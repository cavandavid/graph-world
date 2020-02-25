(ns graph-world.web-server
  (:require [com.stuartsierra.component :as component]
            [yada.yada :refer [listener resource as-resource default-interceptor-chain]]
            [schema.core :as s]
            [taoensso.timbre :as t]
            [clojure.core.async :as async]
            [graph-world.database :as database]
            [graph-world.utils :as utils]))

(defn request-logger
  "logs request made to server"
  []
  (fn [{:keys [request response] :as ctx}]
    (t/info "Request received from remote host "
            (:remote-addr request)
            " for path "
            (:uri request)
            " Status "
            (:status response))))

(defn add-unique-id
  "Adds unique id to HTTP requests that alter state to handle concurrency"
  [ctx]
  (if (not= (-> ctx :request :request-method) :get)
    (assoc-in ctx [:request :unique-id] (utils/random-string 8))
    ctx))

(def custom-interceptor-chain
  "Modified Yada interceptor chain"
  (cons add-unique-id
        default-interceptor-chain))

(defn custom-resource
  "Create a yada resource with sensible default values"
  [model]
  (-> model
      (assoc :produces "application/json")
      (assoc :consumes "application/json")
      (assoc :logger (request-logger))
      (assoc :interceptor-chain custom-interceptor-chain)
      (assoc :responses
             {500 (let [msg "Internal error."]
                    {:description msg
                     :produces    "application/json"
                     :response
                     (fn [ctx]
                       (t/error (:error ctx))
                       {:error msg})})})
      (resource)))

(def node-crud-handler
  (custom-resource
   {:methods
    {:get    {:parameters   {:path {:node-name s/Str}}
              :response     (fn [ctx]
                              (let [node      (-> ctx
                                                  :parameters
                                                  :path
                                                  :node-name)
                                    result    (if-let [node-data (first
                                                                  (database/query
                                                                   [(str "select * from nodes where name = '" node "'")]))]
                                                (assoc
                                                 node-data
                                                 :connections
                                                 (map :destination
                                                      (database/query
                                                       [(str "select destination from edges where source = '" node "'")]))))]
                                (if result
                                  result
                                  (assoc (:response ctx)
                                         :status 204))))}
     :post   {:parameters   {:path {:node-name s/Str}
                             :body {:description s/Str}}
              :response (fn [ctx]
                          (let [name 							(-> ctx
                                                :parameters
                                                :path
                                                :node-name)
                                description (-> ctx
                                                :parameters
                                                :body
                                                :description)
                                write-channel-data {:operation :insert
                                                    :table     :nodes
                                                    :data      {:name        name
                                                                :description description}
                                                    :result    (promise)}]
                            (async/>!! database/write-channel write-channel-data)
                            (when-let [[success result] @(:result write-channel-data)]
                              (if success
                                result
                                (assoc (:response ctx)
                                       :status 403
                                       :body   result)))))}
     :put    {:parameters   {:path {:node-name s/Str}
                             :body {:description s/Str}}
              :response (fn [ctx]
                          (let [name 							(-> ctx
                                                :parameters
                                                :path
                                                :node-name)
                                description (-> ctx
                                                :parameters
                                                :body
                                                :description)
                                write-channel-data {:operation :update
                                                    :table     :nodes
                                                    :data      {:description description}
                                                    :condition [(str "name = '" name "'")]
                                                    :result    (promise)}]
                            (async/>!! database/write-channel write-channel-data)
                            (when-let [[success result] @(:result write-channel-data)]
                              (if (and success (= result [1]))
                                result
                                (assoc (:response ctx)
                                       :status 403)))))}
     :delete {:parameters   {:path {:node-name s/Str}}
              :response     (fn [ctx]
                              (let [node      (-> ctx
                                                  :parameters
                                                  :path
                                                  :node-name)
                                    write-channel-data {:operation :delete
                                                        :table     :nodes
                                                        :condition [(str "name = '" node "'")]
                                                        :result    (promise)}]
                                (async/>!! database/write-channel write-channel-data)
                                (assoc (:response ctx)
                                       :status 204)))}}}))
(def edge-crud-handler
  (custom-resource
   {:methods
    {:post   {:parameters {:body {:source      s/Str
                                  :destination s/Str
                                  :description s/Str}}
              :response (fn [ctx]
                          (let [write-channel-data {:operation :insert
                                                    :table     :edges
                                                    :data      (-> ctx :parameters :body)
                                                    :result    (promise)}]
                            (async/>!! database/write-channel write-channel-data)
                            (when-let [[success result] @(:result write-channel-data)]
                              (if success
                                result
                                (assoc (:response ctx)
                                       :status 403
                                       :body   result)))))}
     :put    {:parameters {:body {:source      s/Str
                                  :destination s/Str
                                  :description s/Str}}
              :response   (fn [ctx]
                            (let [{:keys [source
                                          destination
                                          description]} (-> ctx
                                                            :parameters
                                                            :body)
                                  write-channel-data  {:operation :update
                                                       :table     :edges
                                                       :data      {:description description}
                                                       :condition [(str "source = '"
                                                                        source
                                                                        "' and destination = '"
                                                                        destination
                                                                        "'")]
                                                       :result    (promise)}]
                              (async/>!! database/write-channel write-channel-data)
                              (when-let [[success result] @(:result write-channel-data)]
                                (if (and success (not= result [0]))
                                  result
                                  (assoc (:response ctx)
                                         :status 403)))))}
     :delete {:parameters {:body {:source      s/Str
                                  :destination s/Str}}
              :response (fn [ctx]
                          (let [{:keys [source
                                        destination]} (-> ctx
                                                          :parameters
                                                          :body)
                                write-channel-data  {:operation :delete
                                                     :table     :edges
                                                     :condition [(str "source = '"
                                                                      source
                                                                      "' and destination = '"
                                                                      destination
                                                                      "'")]
                                                     :result    (promise)}]
                            (async/>!! database/write-channel write-channel-data)
                            (assoc (:response ctx)
                                   :status 204)))}}}))

(def routes
  "Routes specifying different endpoints of our web server"
  [""
   [["/node"
     [[["/" :node-name]
       [["" node-crud-handler]]]]]
    ["/edge" edge-crud-handler]
    [true (as-resource nil)]]])

(defn start-web-app
  "Initializes webserver on specified port and returns and instance of running server"
  [port]
  (listener
   routes
   {:port port}))

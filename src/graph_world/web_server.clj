(ns graph-world.web-server
  (:require [com.stuartsierra.component :as component]
            [yada.yada :refer [listener resource as-resource]]
            [graph-world.database :as database]
            [schema.core :as s]
            [taoensso.timbre :as t]))

(defn request-logger
  []
  (fn [{:keys [request response] :as ctx}]
    (println (keys request))
    (t/info "Request received from remote host " 
            (:remote-addr request)  
            " for path " 
            (:uri request)
            " Status "
            (:status response))))

(defn custom-resource
  "Create a yada resource with sensible default values"
  [model]
  (-> model
      (assoc :produces "application/json")
      (assoc :consumes "application/json")
      (assoc :logger (request-logger))
      (assoc :responses
        {500 (let [msg "Internal error."]
					          {:description msg
					           :produces    "application/json"
					           :response
					           (fn [ctx]
					             (t/error (:error ctx))
					             {:error msg})})
         })
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
                                    result    (database/query 
                                                [(str "select * from nodes where name = '" node "'")])]
                                (if (seq result)
                                  (first result)
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
                                                :description)]
                          (database/insert! :nodes {:name name
                                                    :description description})))}
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
                                                :description)]
                          (database/update! :nodes 
                                            {:description description}
                                            [(str "name = '" name "'")])))}
     :delete {:parameters   {:path {:node-name s/Str}}
              :response     (fn [ctx]
                              (let [node      (-> ctx 
                                                :parameters 
                                                :path 
                                                :node-name)]
                                    (database/delete! :nodes 
                                      [(str "name = '" node "'")])
                                    (assoc (:response ctx) 
                                    :status 204)))}}}))
(def edge-crud-handler
  (custom-resource
   {:methods
    {:post   {:parameters {:body {:source      s/Str
                                  :destination s/Str
                                  :description s/Str}}
              :response (fn [ctx]
                          (database/insert! :edges
                                            (-> ctx :parameters :body)
                                            ))}
     :put    {:parameters {:body {:source      s/Str
                                  :destination s/Str
                                  :description s/Str}}
              :response (fn [ctx]
                          (let [{:keys [source 
                                        destination 
                                        description]} (-> ctx 
												                                           :parameters 
												                                           :body)]
                            (database/update! :edges 
                                            {:description description}
                                            [(str "source = '" source 
                                                  "' and destination = '" 
                                                  destination
                                                  "'")])))}
     :delete {:parameters {:body {:source      s/Str
                                  :destination s/Str}}
              :response (fn [ctx]
                          (let [{:keys [source 
                                        destination]} (-> ctx 
												                                           :parameters 
												                                           :body)]
                            (database/delete! :edges 
                                      [(str "source = '" source 
                                                  "' and destination = '" 
                                                  destination
                                                  "'")])
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

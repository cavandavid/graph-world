(ns graph-world.client
  (:require [clj-http.client :as client]))

(def base-url "http://localhost:11131/")

(defn get-fake-names
  "Queries http://names.drycodes.com to get fake names"
  [count]
  (let [url      (str "http://names.drycodes.com/" count "?nameOptions=boy_names")]
      (-> (client/get
                  url
                  {:insecure?        true
                   :throw-exceptions true
                   :as               :json})
          :body)))

(defn create-node
  "Queries graph api to create node"
  [node-name description]
  (let [url      (str base-url "node/" node-name)
        response (client/post
                  url
                  {:insecure?        true
                   :throw-exceptions false
                   :content-type     :json
                   :form-params      {:description description}})]))

(defn create-edge
  "Queries graph api to create edges"
  [data]
  (let [url      (str base-url "edge")
        response (client/post
                  url
                  {:insecure?        true
                   :throw-exceptions false
                   :content-type     :json
                   :form-params      data})]))

(defn calculate-shortest-path
  "Returns shortest path betwee two nodes"
  [source destination]
  (let [node-queue       (atom '())
        distance-table   (atom {})
        get-connections  #(-> (client/get (str "http://localhost:11131/node/" %) {:as :json})
                              :body
                              :connections)
        back-track-table #(loop [result '()
                                 {:keys [value parent]
                                  :as current-node} (get @distance-table destination)]
                            (if (= parent nil)
                              (cons value result)
                              (recur (cons value result)
                                     (get @distance-table parent))))]
    ;; Populate the source in the queue data structure
    (swap! node-queue conj {:value    source
                            :parent   nil})
    (while (not-empty @node-queue)
      (let [{:keys [value parent] :as current-node} (first @node-queue)]
        ;; Enqueue first item from queue
        (swap! node-queue pop)
        ;; Add to distance table
        (swap! distance-table assoc value current-node)
        (if (= value destination)
          ;; break out of loop
          (reset! node-queue '())
          ;; Continue searching in children
          (doall
           (map
            (fn [fetched-child]
              (if-not (get @distance-table fetched-child)
                  ;; Add the children of the node into the queue
                (swap! node-queue conj {:value  fetched-child
                                        :parent value}))) (get-connections value))))))
    (clojure.string/join " --> " (back-track-table))))

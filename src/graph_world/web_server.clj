(ns graph-world.web-server
  (:require [com.stuartsierra.component :as component]
            [yada.yada :refer [listener resource as-resource]]))

(defn custom-resource
  "Create a yada resource with sensible default values"
  [model]
  (-> model
      (assoc :produces "text/plain")
      (resource)))

(def node-crud-handler
  (custom-resource
   {:methods
    {:get {:response (fn [ctx]
                       {:message "NODE WORKS!!"})}}}))
(def edge-crud-handler
  (custom-resource
   {:methods
    {:get {:response (fn [ctx]
                      {:message "EDGE WORKS!!"})}}}))

(def routes
  "Routes specifying different endpoints of our web server"
  ["/"
   [["node" node-crud-handler]
    ["edge" edge-crud-handler]
    [true (as-resource nil)]]])

(defn start-web-app
  "Initializes webserver on specified port and returns and instance of running server"
  [port]
		(listener
		   routes
		   {:port port}))

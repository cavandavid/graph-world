(ns graph-world.system
  (:require [com.stuartsierra.component :as component]
            [aero.core :refer [read-config]]
            [graph-world.web-server :refer [start-web-app]]
            [graph-world.database :refer [create-connection-pool
                                          close-connection-pool
                                          ensure-integrity-of-database-writes]]))

(defrecord Database [port-number server-name database-name username password]
  component/Lifecycle
  (start [component]
    (println "Ensure integrity of database writes")
    (ensure-integrity-of-database-writes)
    (println "Initialize database connection pool running on " server-name " and " port-number)
    (assoc component :database-connection (create-connection-pool {:port-number port-number
                                                                   :server-name server-name
                                                                   :database-name database-name
                                                                   :username username
                                                                   :password password})))

  (stop [component]
    (println "Close database connection pool running on " server-name " and " port-number)
    (close-connection-pool)
    (assoc component :database-connection nil)))

(defn database-component-wrapper
  "Launches database connection pool component"
  [database]
  (map->Database database))

(defrecord Webserver [port]
  component/Lifecycle
  (start [component]
    (println "Starting web server on port" port)
    (assoc component :web-server (start-web-app port)))

  (stop [component]
    (when-let [close (:close (:web-server component))]
      (println "Closing down web server" close)
      (close)
      (assoc component :web-server nil))))

(defn webserver-component-wrapper
  "Launches web-server service component"
  [web-server]
  (map->Webserver web-server))

(defn component-system
  "The main system which holds all components together"
  []
  (let [config-options (read-config
                        (clojure.java.io/resource "config.edn"))
        {:keys [database web-server]} config-options]
    (component/system-map
     :db 		  (database-component-wrapper database)
     :server (webserver-component-wrapper web-server))))

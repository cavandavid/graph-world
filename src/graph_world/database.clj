(ns graph-world.database
  (:require [clojure.java.jdbc :as j]
            [hikari-cp.core :refer [make-datasource close-datasource]]))


(def datasource (atom nil))
(def pool (atom nil))

(def pool
  "Database connection pool."
  (atom nil))

(def predefined-database-settings
  {:auto-commit       true
   :read-only         false
   :minimum-idle      5
   :maximum-pool-size 5
   :max-lifetime      0
   :adapter           "postgresql"})

(defn create-connection-pool
  "Creates connection pool with database"
  [input-settings]
  (let [merged-settings (merge input-settings predefined-database-settings)]
    (do
      (reset! datasource (make-datasource merged-settings))
      (reset! pool (.getConnection @datasource))
      @datasource)))

(defn close-connection-pool
  "Closes database connection pool"
  []
  (close-datasource @datasource))

(defn query
  "Run an SQL query on the database."
  [condition]
  (clojure.java.jdbc/with-db-connection [conn {:datasource @datasource}]
      (clojure.java.jdbc/query conn 
                               condition)))
(defn insert!
  "Inserts record in the database table."
  [table data]
  (clojure.java.jdbc/with-db-connection [conn {:datasource @datasource}]
      (clojure.java.jdbc/insert! conn 
                                 table 
                                 data
                                 {:result-set-fn first})))

(defn update!
  "Updates record in the database table"
  [table data condition]
  (clojure.java.jdbc/with-db-connection [conn {:datasource @datasource}]
    (clojure.java.jdbc/update! conn 
                               table 
                               data 
                               condition)))

(defn delete!
  "Updates record in the database table"
  [table condition]
  (println "****" table condition)
  (clojure.java.jdbc/with-db-connection [conn {:datasource @datasource}]
    (clojure.java.jdbc/delete! conn
                               table  
                               condition)))
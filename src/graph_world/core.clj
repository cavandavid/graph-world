(ns graph-world.core
  (:require
   [com.stuartsierra.component.repl :refer [reset set-init start stop]]
   [graph-world.system :refer [component-system]]
   [graph-world.client])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(set-init (fn [_] (component-system)))


(ns graph-world.utils)

(defn random-string
  [length]
  (let [chars "0123456789abcdefghijklmnopqrstuvwxyz"]
    (clojure.string/join ""
                         (repeatedly length #(rand-nth chars)))))
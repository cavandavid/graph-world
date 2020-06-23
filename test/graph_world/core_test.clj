(ns graph-world.core-test
  (:require [clojure.test :refer :all]
            [graph-world.core :refer :all]
            [graph-world.client :refer [calculate-shortest-path]]
            [graph-world.populate-nodes :refer [populate-test-nodes]]
            [graph-world.populate-edges :refer [populate-edges]]))

(deftest a-test
  (testing "Test if calculate-shortest-path works as it should"
    (populate-test-nodes)
    (populate-edges)
    (is (= (calculate-shortest-path "linkedin" "linkedin.com_profile_jacob_baker")
           "linkedin --> linkedin.com --> linkedin.com_search_j --> linkedin.com_profile_jacob_baker")
        "Shortest path calcuation incorrect, please verify!!")
    (is (= (calculate-shortest-path "linkedin.com" "tinder.com")
           "")
        "Linkedin.com should not have any links to tinder.com")))

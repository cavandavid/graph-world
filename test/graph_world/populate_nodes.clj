(ns graph-world.populate-nodes
  (:require [clojure.test :refer :all]
            [graph-world.client :refer [create-node]]
            [graph-world.test-data :refer [linkedin-urls-search-users-by-characters
                                           linkedin-profiles]]))
(defn populate-test-nodes
  []
  (do
				; Populate nodes :
				; _____ Google ____
    (create-node "mumbaiuniversity"  "google search mumbai University")
    (create-node "linkedin"          "google search linkedin")

				; ; _____ Mumbai University ____
    (create-node "mumbaiuniversity.com"                "Mumbai University landing page")
    (create-node "mumbaiuniversity.com_alumni"         "Mumbai University alumni page");;_____Mumbai University alumni sort by character page____
    (doseq [identifier "abcdefghijklmnopqrstuvwxyz"]
      (create-node (str "mumbaiuniversity.com_alumni_name_" identifier)
                   (str "Alumni names starting with " identifier)))

    ; ; ___ Linkedin ___
    (create-node "linkedin.com" "Linkedin landing page")

				; ; ____ LinkedinProfile/search_by_character _____
    (doseq [{:keys [node description]} linkedin-urls-search-users-by-characters]
      (create-node node description))

    ;;___LinkedinProfile
    (doseq [{:keys [node description]} linkedin-profiles]
      (create-node node description))))
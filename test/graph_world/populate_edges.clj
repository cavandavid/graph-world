(ns graph-world.populate-edges
  (:require [clojure.test :refer :all]
            [graph-world.client :refer [create-edge]]
            [graph-world.test-data :refer [linkedin-urls-search-users-by-characters
                                           linkedin-profiles]]))

(defn populate-edges
  []
  (do
    ;; Google search Mumbai University -> mumbaiuniversity.com -> mumbaiuniversity.com/alumni -> mumbaiuniversity.com/alumni/search_by_{char} -> linkedin.com_profile_{user}
    (create-edge {:source       "mumbaiuniversity"
                  :destination  "mumbaiuniversity.com"
                  :description  "google search to official site"})
    (create-edge {:source       "mumbaiuniversity.com"
                  :destination  "mumbaiuniversity.com_alumni"
                  :description  "Mumbai University alumni page transfer"})

    (doseq [identifier "abcdefghijklmnopqrstuvwxyz"]
      (create-edge {:source      "mumbaiuniversity.com_alumni"
                    :destination (str "mumbaiuniversity.com_alumni_name_" identifier)
                    :description "Alumni page sorted by character"}))

    ; Google search Linkedin -> linkedin.com -> linkedin.com_search_{by_char} -> linkedin.com_profile_{user}
    (create-edge {:source       "linkedin"
                  :destination  "linkedin.com"
                  :description  "google search to official site"})
    ;; Linkedin homepage to linkedsearch by character
    (doseq [{:keys [node
                    description]} linkedin-urls-search-users-by-characters]
      (create-edge {:source       "linkedin.com"
                    :destination  node
                    :description  description}))
    ;; Linkedin search by character to individual linkedin page
    (doseq [{:keys [node
                    description]} linkedin-profiles]
      (create-edge {:source       (str "linkedin.com_search_"
                                       (clojure.string/lower-case
                                        (first description)))
                    :destination  node
                    :description  description})
      (create-edge {:source       (str "mumbaiuniversity.com_alumni_name_"
                                       (clojure.string/lower-case
                                        (first description)))
                    :destination  node
                    :description  description}))))

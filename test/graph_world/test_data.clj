(ns graph-world.test-data
  (:require [graph-world.client :refer [get-fake-names]]))

(def linkedin-urls-search-users-by-characters
  [{:node "linkedin.com_search_c"  :description "Linkedin search users with c"}
   {:node "linkedin.com_search_j"  :description "Linkedin search users with j"}
   {:node "linkedin.com_search_b"  :description "Linkedin search users with b"}
   {:node "linkedin.com_search_a"  :description "Linkedin search users with a"}
   {:node "linkedin.com_search_d"  :description "Linkedin search users with d"}
   {:node "linkedin.com_search_e"  :description "Linkedin search users with e"}
   {:node "linkedin.com_search_f"  :description "Linkedin search users with f"}
   {:node "linkedin.com_search_g"  :description "Linkedin search users with g"}
   {:node "linkedin.com_search_h"  :description "Linkedin search users with h"}
   {:node "linkedin.com_search_i"  :description "Linkedin search users with i"}
   {:node "linkedin.com_search_k"  :description "Linkedin search users with k"}
   {:node "linkedin.com_search_l"  :description "Linkedin search users with l"}
   {:node "linkedin.com_search_m"  :description "Linkedin search users with m"}
   {:node "linkedin.com_search_n"  :description "Linkedin search users with n"}
   {:node "linkedin.com_search_o"  :description "Linkedin search users with o"}
   {:node "linkedin.com_search_p"  :description "Linkedin search users with p"}
   {:node "linkedin.com_search_q"  :description "Linkedin search users with q"}
   {:node "linkedin.com_search_r"  :description "Linkedin search users with r"}
   {:node "linkedin.com_search_s"  :description "Linkedin search users with s"}
   {:node "linkedin.com_search_t"  :description "Linkedin search users with t"}
   {:node "linkedin.com_search_u"  :description "Linkedin search users with u"}
   {:node "linkedin.com_search_v"  :description "Linkedin search users with v"}
   {:node "linkedin.com_search_w"  :description "Linkedin search users with w"}
   {:node "linkedin.com_search_x"  :description "Linkedin search users with x"}
   {:node "linkedin.com_search_y"  :description "Linkedin search users with y"}
   {:node "linkedin.com_search_z"  :description "Linkedin search users with z"}])
;; http://names.drycodes.com/100?nameOptions=boy_names
(def linkedin-profiles-self-populated
  [{:node  "linkedin.com_profile_alex_jose"       :description  "alex_jose's linkedin page"}
   {:node  "linkedin.com_profile_alex_ryan"       :description  "alex_ryan's linkedin page"}
   {:node  "linkedin.com_profile_alexandra_baker" :description  "alexandra_baker's linkedin page"}
   {:node  "linkedin.com_profile_alexandra_smith" :description  "alexandra_smith's linkedin page"}
   {:node  "linkedin.com_profile_aman_shah"       :description  "aman_shah's linkedin page"}
   {:node  "linkedin.com_profile_arman_kohli"     :description  "arman_kohli's linkedin page"}
   {:node  "linkedin.com_profile_arial_winter"    :description  "arial_winter's linkedin page"}
   {:node  "linkedin.com_profile_arial_summer"    :description  "arial_summer's linkedin page"}
   {:node  "linkedin.com_profile_anish_matthew"   :description  "anish_matthew's linkedin page"}
   {:node  "linkedin.com_profile_arjun_gopal"     :description  "arjun_gopal's linkedin page"}
   {:node  "linkedin.com_profile_amar_gopal"      :description  "amar_gopal's linkedin page"}

   {:node  "linkedin.com_profile_john_doe"      :description "John's linkedin page"}
   {:node  "linkedin.com_profile_jon_snow"      :description "Jon's linkedin page"}
   {:node  "linkedin.com_profile_james_dean"    :description "James's linkedin page"}
   {:node  "linkedin.com_profile_jenny_smith"   :description "Jenny's linkedin page"}
   {:node  "linkedin.com_profile_john_abraham"  :description "John Abraham's linkedin page"}
   {:node  "linkedin.com_profile_jacob_baker"   :description "Jacob Baker's linkedin page"}
   {:node  "linkedin.com_profile_jared_baker"   :description "Jared Baker's linkedin page"}
   {:node  "linkedin.com_profile_jason_baker"   :description "Jason Baker's linkedin page"}
   {:node  "linkedin.com_profile_jeremy_baker"  :description "Jeremy Baker's linkedin page"}
   {:node  "linkedin.com_profile_jeff_jackson"  :description "Jeffs's linkedin page"}
   {:node  "linkedin.com_profile_jenny_jane"    :description "Jenny's linkedin page"}
   {:node  "linkedin.com_profile_julie_jess"    :description "Julie's linkedin page"}
   {:node  "linkedin.com_profile_jerry_smith"   :description "Jerry's linkedin page"}
   {:node  "linkedin.com_profile_jake_ryans"    :description "Jake's linkedin page"}

   {:node  "linkedin.com_profile_barry_smith"     :description "Barry's linkedin page"}
   {:node  "linkedin.com_profile_berry_mark"      :description "Berry's linkedin page"}
   {:node  "linkedin.com_profile_benjamin_baker"  :description "Benjamin's linkedin page"}
   {:node  "linkedin.com_profile_benjamin_button" :description "Benjamin button's linkedin page"}
   {:node  "linkedin.com_profile_boris_badguy"    :description "Boris's linkedin page"}
   {:node  "linkedin.com_profile_boris_goodguy"   :description "Boris goodguy's linkedin page"}
   {:node  "linkedin.com_profile_benny_hill"      :description "Benny's linkedin page"}
   {:node  "linkedin.com_profile_benn_williams"   :description "Benn Williams's linkedin page"}
   {:node  "linkedin.com_profile_burke_smith"     :description "Burke's linkedin page"}
   {:node  "linkedin.com_profile_brigsby"         :description "Brigsby's linkedin page"}
   {:node  "linkedin.com_profile_billy_jean"      :description "Billy's linkedin page"}
   {:node  "linkedin.com_profile_billy_smith"     :description "Billy Smith's linkedin page"}
   {:node  "linkedin.com_profile_bart_simpson"    :description "Bart's linkedin page"}
   {:node  "linkedin.com_profile_bruce_wayne"     :description "Bruce's linkedin page"}
   {:node  "linkedin.com_profile_bryan_smith"     :description "Bryan's linkedin page"}
   {:node  "linkedin.com_profile_brian_smith"     :description "Brian's linkedin page"}
   {:node  "linkedin.com_profile_brendon"         :description "Brendon's linkedin page"}
   {:node  "linkedin.com_profile_brandon"         :description "Brandon's linkedin page"}
   {:node  "linkedin.com_profile_brian_smit"      :description "Brian's linkedin page"}
   {:node  "linkedin.com_profile_bruce_smith"     :description "Brian's linkedin page"}
   {:node  "linkedin.com_profile_blake"           :description "Blake's linkedin page"}
   {:node  "linkedin.com_profile_blaise"          :description "Blaise's linkedin page"}
   {:node  "linkedin.com_profile_bob"             :description "Bob's linkedin page"}
   {:node  "linkedin.com_profile_bradley"         :description "Bradley's linkedin page"}

   {:node  "linkedin.com_profile_clark_kent"    :description "Clark's linkedin page"}
   {:node  "linkedin.com_profile_cavan_david"   :description "Cavan's linkedin page"}
   {:node  "linkedin.com_profile_calvin_smith"  :description "Calvin's linkedin page"}
   {:node  "linkedin.com_profile_cyrene_joe"    :description "Cyrene's linkedin page"}
   {:node  "linkedin.com_profile_cyril"         :description "Cyril's linkedin page"}
   {:node  "linkedin.com_profile_conrad_smith"  :description "Conrad Smith's linkedin page"}
   {:node  "linkedin.com_profile_conrad_joe"    :description "Conrad Joe's linkedin page"}
   {:node  "linkedin.com_profile_chris_smith"   :description "Chris Smith's linkedin page"}
   {:node  "linkedin.com_profile_conrad_neil"   :description "Chris Neil's linkedin page"}
   {:node  "linkedin.com_profile_cody_jr"       :description "Cody's linkedin page"}
   {:node  "linkedin.com_profile_cody_banks"    :description "Cody Bank's linkedin page"}
   {:node  "linkedin.com_profile_claire"        :description "Clair's linkedin page"}
   {:node  "linkedin.com_profile_celeste"       :description "Celeste's linkedin page"}
   {:node  "linkedin.com_profile_connor"        :description "Connor's linkedin page"}
   {:node  "linkedin.com_profile_carol"         :description "Carol's linkedin page"}
   
   {:node  "linkedin.com_profile_daniel"        :description "daniel's linkedin page"}
   {:node  "linkedin.com_profile_declan"        :description "declan's linkedin page"}
   {:node  "linkedin.com_profile_dawson"        :description "dawson's linkedin page"}
   {:node  "linkedin.com_profile_daxton"        :description "daxton's linkedin page"}
   {:node  "linkedin.com_profile_desmond"       :description "desmond's linkedin page"}
   {:node  "linkedin.com_profile_damon"         :description "damon's linkedin page"}
   {:node  "linkedin.com_profile_denver"        :description "denver's linkedin page"}
   {:node  "linkedin.com_profile_deacon"        :description "deacon's linkedin page"}
   {:node  "linkedin.com_profile_donald"        :description "donald's linkedin page"}
   {:node  "linkedin.com_profile_dariel"        :description "dariel's linkedin page"}
   {:node  "linkedin.com_profile_david"         :description "david's linkedin page"}
   {:node  "linkedin.com_profile_diego"         :description "diego's linkedin page"}
   {:node  "linkedin.com_profile_derek"         :description "derek's linkedin page"}
   {:node  "linkedin.com_profile_dante"         :description "dante's linkedin page"}
   {:node  "linkedin.com_profile_dalton"        :description "dalton's linkedin page"}
   {:node  "linkedin.com_profile_drake"         :description "drake's linkedin page"}
   {:node  "linkedin.com_profile_dexter"        :description "dexter's linkedin page"}
   {:node  "linkedin.com_profile_davis"         :description "davis's linkedin page"}
   {:node  "linkedin.com_profile_duke"          :description "duke's linkedin page"}
   {:node  "linkedin.com_profile_deandre"       :description "deandre's linkedin page"}
   {:node  "linkedin.com_profile_dominic"       :description "dominic's linkedin page"}
   {:node  "linkedin.com_profile_dean"          :description "dean's linkedin page"}
   {:node  "linkedin.com_profile_damien"        :description "damien's linkedin page"}
   {:node  "linkedin.com_profile_donovan"       :description "donovan's linkedin page"}
   {:node  "linkedin.com_profile_dax"           :description "dax's linkedin page"}
   {:node  "linkedin.com_profile_derrick"       :description "derrick's linkedin page"}
   {:node  "linkedin.com_profile_darius"        :description "darius's linkedin page"}
   {:node  "linkedin.com_profile_dennis"        :description "dennis's linkedin page"}
   {:node  "linkedin.com_profile_darren"        :description "darren's linkedin page"}])

(def linkedin-profiles
  (concat 
    linkedin-profiles-self-populated
    (mapv
      (fn [name]
        {:node        (str "linkedin.com_profile_" name)
         :description (str name "'s linkedin page")})
      (get-fake-names 1000))))

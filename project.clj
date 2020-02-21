(defproject graph-world "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.stuartsierra/component "0.4.0"]
                 [aero "1.1.3"]
                 [com.stuartsierra/component.repl "0.2.0"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [hikari-cp "2.6.0"]
                 [org.postgresql/postgresql "42.2.5"]
                 [danlentz/clj-uuid "0.1.7"]
                 [yada "1.2.15"]
; [aleph "0.4.1"]
; [bidi "2.0.12"]
]
  :main ^:skip-aot graph-world.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

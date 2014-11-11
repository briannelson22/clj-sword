(defproject clj-sword "0.1.0-SNAPSHOT"
  :description "Bible study API"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.1"]]
  :main ^:skip-aot clj-sword.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :global-vars {*print-length* 100})

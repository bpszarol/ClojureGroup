(defproject calculator "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [seesaw "1.4.2" :exclusions [org.clojure/clojure]]
                 [instaparse "1.4.1"]
                 [org.clojure/math.numeric-tower "0.0.2"]]
  :main calculator.core
  :aot [calculator.core])

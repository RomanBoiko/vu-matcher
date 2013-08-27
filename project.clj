(defproject matcher "0.1.0-SNAPSHOT"
  :description "Reconciliation and matching engine"
  :dependencies [
    [org.clojure/clojure "1.5.1"]
    [org.clojure/data.csv "0.1.2"]
    [clj-orient "0.5.0"]]
  :main matcher.bootstrap
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}}
  :aliases {"boot" ["run" "-m" "matcher.bootstrap"]})


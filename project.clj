(defproject matcher "0.1.0-SNAPSHOT"
  :description "Reconciliation and matching engine"
  :dependencies [
    [org.clojure/clojure "1.5.1"]
    [org.clojure/data.csv "0.1.2"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}})

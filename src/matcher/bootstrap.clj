(ns matcher.bootstrap
  (:require
    [matcher.loader :as loader]
    [matcher.db :as db])
  (:gen-class :main true))

(defn -main [& args]
  (let [input-dir (java.io.File. "waiting")
        processed-dir (java.io.File. "processed")]
    (matcher.db/create-dbs)
    (.mkdir input-dir)
    (.mkdir processed-dir)
    (loop []
      (Thread/sleep 2000)
      (println "Looking up to load dir...")
      (loader/perform-load input-dir processed-dir)
      (recur))))

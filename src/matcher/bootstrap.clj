(ns matcher.bootstrap
  (:require
    [matcher.loader :as loader]
    [matcher.db :as db]
    [matcher.web :as web]
    [taoensso.timbre :as log])
  (:gen-class :main true))

(defn -main [& args]
  (let [input-dir (java.io.File. "waiting")
        processed-dir (java.io.File. "processed")]
    (db/start-db)
    (web/start-web-server)
    (.mkdir input-dir)
    (.mkdir processed-dir)
    (loop []
      (Thread/sleep 2000)
      (log/info "Looking up to load dir...")
      (loader/perform-load input-dir processed-dir)
      (recur))))

(ns matcher.bootstrap
  (:require
    [matcher.loader :as loader]
    [matcher.db :as db]
    [matcher.web :as web]
    [matcher.matching :as matching]
    [taoensso.timbre :as log])
  (:gen-class :main true))

(defn -main [& args]
  (do
    (db/start-db)
    (web/start-web-server)
    (matching/start-matching)
    (loader/start-loading)
    (loop []
      (Thread/sleep 5000)
      (recur))))

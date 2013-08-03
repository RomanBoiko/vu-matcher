(ns matcher.dbinstall
  (:require [com.ashafa.clutch :as couchdb]))

(def dburl "http://localhost:5984/matcher")

(defn -main [& args]
  (let [db-creation-response (couchdb/get-database dburl)]
    (assert (= (:db_name db-creation-response) "matcher"))
    (println "==>DB creation response: " db-creation-response)))

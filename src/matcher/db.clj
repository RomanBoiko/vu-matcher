(ns matcher.db
  (:require [com.ashafa.clutch :as couchdb])
  (:gen-class))

(def dburl "http://localhost:5984/")

(def db-source (str dburl "source"))
(def db-active-records (str dburl "active_records"))
(def all-dbs [db-source db-active-records])

(defn create-dbs []
  (doseq [response (map couchdb/get-database all-dbs)]
    (println "==>DB creation response: " response)))

(defn drop-dbs []
  (doseq [response (map couchdb/delete-database all-dbs)]
    (println "==>DB delete response: " response)))

(defn add-active-records [active-records]
  (couchdb/bulk-update db-active-records active-records))

(defn add-source [source-name]
  (:_id (couchdb/put-document db-source {:name source-name})))

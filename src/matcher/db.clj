(ns matcher.db
  (:require [clj-orient.core :as oc]
            [clj-orient.query :as oq]
            [taoensso.timbre :as log]))

(defn start-db []
  (do
    (log/info "starting db...")
    (oc/set-db! (oc/open-document-db! "local:/tmp/matcherdb" "writer" "writer"))
    (log/info "db started")))

(defn stop-db []
  (do
    (log/info "stopping db...")
    (oc/close-db!)
    (log/info "db stopped")))

(defn add-active-records [active-records]
  (doseq [record active-records]
    (oc/save! (oc/document :active-record record))))

(defn all-active-records []
  (oq/native-query :active-record {}))

(defn add-source [source-name]
  (oc/save! (oc/document :source {:name source-name})))

(defn all-sources []
  (oq/native-query :source {}))

(defn clean-db []
  (do
    (doseq [record (all-active-records)]
      (oc/delete! record))
    (doseq [source (all-sources)]
      (oc/delete! source))))

(defn create-db []
  (do
    (oc/create-db! "local:/tmp/matcherdb")
    (start-db)
    (add-active-records [{}])
    (add-source "")
    (clean-db)
    (stop-db)))

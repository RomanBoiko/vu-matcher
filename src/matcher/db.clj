(ns matcher.db
  (:require [clj-orient.core :as oc]
            [clj-orient.query :as oq]
            [taoensso.timbre :as log]))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(def db-url "local:/tmp/matcherdb")

(defn start-db []
  (do
    (log/info "starting db...")
    (oc/set-db! (oc/open-document-db! db-url "writer" "writer"))
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


(defn all-matched-records []
  (oq/native-query :matched-record {}))

(defn add-group [match-name]
  (let [id (uuid)]
    (oc/save! (oc/document :group {:match-name match-name :id id}))
    id))

(defn all-groups []
  (oq/native-query :group {}))

(defn add-source [source-name]
  (oc/save! (oc/document :source {:name source-name})))

(defn all-sources []
  (oq/native-query :source {}))

(defn clean-db []
  (do
    (doseq [document (concat (all-active-records) (all-matched-records) (all-sources) (all-groups))]
      (oc/delete! document))
    (log/info "db wiped")))

(defn create-db []
  (do
    (oc/create-db! db-url)
    (start-db)
    (add-active-records [{}])
    (add-source "")
    (add-group "some-match")
    (oc/save! (oc/document :matched-record {}))
    (clean-db)
    (stop-db)))

(defn convert-active-records-to-matched-group [match-name records]
  (let [match-id (add-group match-name)]
    (doseq [record records]
      (oc/delete! record)
      (oc/save! (oc/document :matched-record (assoc record :match-id match-id))))))

(ns matcher.db-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [matcher.db :as db]
            [com.ashafa.clutch :as couchdb]))

(deftest should-create-and-drop-dbs
  (db/drop-dbs)
  (db/create-dbs)
  (is (= 4 (count (couchdb/all-databases))))
  (db/drop-dbs)
  (is (= 2 (count (couchdb/all-databases)))))

(deftest should-add-active-records
  (db/drop-dbs)
  (db/create-dbs)
  (let [add-result (db/add-active-records [{"a" 1 "b" 2} {:c 3}])]
    (is (= 2 (count add-result)))
    (is (every? #(:ok %) add-result))
    (println "====>Put records result: " add-result))
  (db/drop-dbs))


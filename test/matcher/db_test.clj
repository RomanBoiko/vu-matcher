(ns matcher.db-test
  (:require [clojure.test :refer :all]
            [matcher.db :as db]))

(deftest should-add-sources
  (db/add-source "some.csv")
  (is (= 1 (count (db/all-sources))))
  (is (= "some.csv" (:name (first (db/all-sources))))))

(deftest should-add-active-records
  (db/add-active-records [{"idd" 1} {"idd" 2}])
  (is (= 2 (count (db/all-active-records)))))

(deftest should-convert-few-active-records-to-matched-group
  (db/add-active-records [{"idd" 1} {"idd" 2} {"idd" 3}])
  (let [active-records (db/all-active-records)]
    (is (= 3 (count active-records)))
    (db/convert-active-records-to-matched-group
      "perfect one-to-one match"
      (take 2 active-records)))
  (is (= 1 (count (db/all-active-records))))
  (is (= 2 (count (db/all-matched-records))))
  (is (= 1 (count (db/all-groups))))
  (let [match-id (:id (first (db/all-groups)))]
    (doseq [matched-record (db/all-matched-records)]
      (is (= match-id (:match-id matched-record))))))

(defn ns-fixture [f]
  (db/start-db)
  (f)
  (db/stop-db))

(defn each-fixture [f]
  (db/clean-db)
  (f))

(use-fixtures :once ns-fixture)
(use-fixtures :each each-fixture)


(ns matcher.db-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [matcher.db :as db]))

(deftest should-add-sources
  (db/add-source "some.csv")
  (is (= 1 (count (db/all-sources))))
  (is (= "some.csv" (:name (first (db/all-sources))))))

(deftest should-add-active-records
  (db/add-active-records [{"idd" 1} {"idd" 2}])
  (is (= 2 (count (db/all-active-records)))))

(defn each-fixture [f]
  (db/start-db)
  (db/clean-db)
  (f)
  (db/stop-db))

(use-fixtures :each each-fixture)


(ns matcher.db-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [matcher.db :as db]))

(deftest should-start-db
  (is (= true (db/start-db))))


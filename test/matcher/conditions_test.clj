(ns matcher.conditions-test
  (:require [clojure.test :refer :all]
            [matcher.conditions :refer :all]))

(deftest should-cluster-by-attribute-value-equal
  (let [test-map {"k1" "v1"}]
    (is 
      (= [[test-map]] (condition-values-equal "k1" [[test-map]])))
    (is 
      (= [[test-map test-map]] (condition-values-equal "k1" [[test-map test-map]])))
    (is 
      (= [[test-map] [test-map]] (condition-values-equal "k1" [[test-map] [test-map]])))
    (is 
      (= [[test-map] [{"k1" "v2"}]] (condition-values-equal "k1" [[test-map {"k1" "v2"}]])))
    (is 
      (= [[test-map]] (condition-values-equal "k1" [[test-map {}]])))
    (is 
      (= [] (condition-values-equal "k2" [[test-map]])))
    (is 
      (= [[{"k2" "v2"}]] (condition-values-equal "k2" [[test-map] [{"k2" "v2"}]])))))

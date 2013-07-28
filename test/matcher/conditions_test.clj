(ns matcher.conditions-test
  (:require [clojure.test :refer :all]
            [matcher.conditions :refer :all]))

(deftest should_cluster_by_attribute_value_equal
  (let [testMap {"k1" "v1"}]
    (is 
      (= [[testMap]] (condition-values-equal "k1" [[testMap]])))
    (is 
      (= [[testMap testMap]] (condition-values-equal "k1" [[testMap testMap]])))
    (is 
      (= [[testMap] [testMap]] (condition-values-equal "k1" [[testMap] [testMap]])))
    (is 
      (= [[testMap] [{"k1" "v2"}]] (condition-values-equal "k1" [[testMap {"k1" "v2"}]])))
    (is 
      (= [[testMap]] (condition-values-equal "k1" [[testMap {}]])))
    (is 
      (= [] (condition-values-equal "k2" [[testMap]])))
    (is 
      (= [[{"k2" "v2"}]] (condition-values-equal "k2" [[testMap] [{"k2" "v2"}]])))))

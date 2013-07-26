(ns matcher.matchrules-test
  (:require [clojure.test :refer :all]
            [matcher.matchrules :refer :all]))

(deftest should_cluster_list_of_groups_of_records_by_attribute_value_equal
  (let [testMap {"key1" "val1" "key2" "val2"}]
    (is 
      (= [[testMap {"key1" "val1"}]] (values-equal "key1" [[testMap {"key1" "val1"}]])))
    (is 
      (= [[testMap] [testMap]] (values-equal "key2" [[testMap] [testMap {}]])))
    (is 
      (= [[testMap]] (values-equal "key2" [[testMap {"key1" "val1"}]])))
    (is 
      (= [] (values-equal "key3" [[testMap]])))))

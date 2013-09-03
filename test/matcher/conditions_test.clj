(ns matcher.conditions-test
  (:require 
    [midje.sweet :refer :all]
    [matcher.conditions :refer :all]))

(facts "about `cluster-using-group-by`"
  (fact "will group maps within same vector that have matching value for given key"
    (cluster-using-group-by "k1" [{"k1" "v1"}]) => [[{"k1" "v1"}]]
    (cluster-using-group-by "k1" [{"k1" "v1"} {"k1" "v1"}]) => [[{"k1" "v1"} {"k1" "v1"}]])

  (fact "will group maps in different vectors that do not have matching value for given key"
    (cluster-using-group-by "k1" [{"k1" "v1"} {"k1" "v2"}]) => [[{"k1" "v1"}] [{"k1" "v2"}]]
    (cluster-using-group-by "k1" [{"k1" "v1"} {"k1" "v2"} {"k1" "v3"}]) => [[{"k1" "v1"}] [{"k1" "v2"}] [{"k1" "v3"}]])

  (fact "will return nil when none of the maps contain given key"
    (cluster-using-group-by "k1" [{}]) => nil
    (cluster-using-group-by "k1" [{"k2" "v2"}]) => nil
    (cluster-using-group-by "k1" [{"k2" "v2"} {}]) => nil)

  (fact "will group maps when there is at least one matching value for given key"
    (cluster-using-group-by "k1" [{"k1" "v1"} {}]) => [[{"k1" "v1"}]]))

(facts "about `values-equal`"
  (fact "will only group matches that are part of the same vector"
    (values-equal "k1" [[{"k1" "v1"} {"k1" "v1"}]]) => [[{"k1" "v1"} {"k1" "v1"}]]
    (provided
      (cluster-using-group-by "k1" [{"k1" "v1"} {"k1" "v1"}]) => [[{"k1" "v1"} {"k1" "v1"}]])

    (values-equal "k1" [[{"k1" "v1"}] [{"k1" "v1"}]]) => [[{"k1" "v1"}] [{"k1" "v1"}]]
    (provided
      (cluster-using-group-by "k1" [{"k1" "v1"}]) => [[{"k1" "v1"}]] :times 2)))

(facts "about `number-of-records-in-group-is`"
  (fact "will create matches only if number of records in group is as given"
    (number-of-records-in-group-is 2 [[{} {}]]) => [[{} {}]]
    (number-of-records-in-group-is 2 [[{}]]) => []
    (number-of-records-in-group-is 2 [[{} {}] [{}]]) => [[{} {}]]
    (number-of-records-in-group-is 3 [[{} {} {}][{} {}]]) => [[{} {} {}]]
    (number-of-records-in-group-is 1 [[{} {}] [{}]]) => [[{}]]))

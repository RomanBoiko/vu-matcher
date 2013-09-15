(ns matcher.conditions-test
  (:require
    [clojure.test :refer :all]
    [echo.test.mock :refer :all]
    [matcher.conditions :refer :all]))

(deftest about-cluster-using-group-by
  (testing "will group maps within same vector that have matching value for given key"
    (is (= (cluster-using-group-by "k1" [{"k1" "v1"}]) [[{"k1" "v1"}]]))
    (is (= (cluster-using-group-by "k1" [{"k1" "v1"} {"k1" "v1"}]) [[{"k1" "v1"} {"k1" "v1"}]])))

  (testing "will group maps in different vectors that do not have matching value for given key"
    (is (= (cluster-using-group-by "k1" [{"k1" "v1"} {"k1" "v2"}]) [[{"k1" "v1"}] [{"k1" "v2"}]]))
    (is (= (cluster-using-group-by "k1" [{"k1" "v1"} {"k1" "v2"} {"k1" "v3"}]) [[{"k1" "v1"}] [{"k1" "v2"}] [{"k1" "v3"}]])))

  (testing "will return nil when none of the maps contain given key"
    (is (= (cluster-using-group-by "k1" [{}]) nil))
    (is (= (cluster-using-group-by "k1" [{"k2" "v2"}]) nil))
    (is (= (cluster-using-group-by "k1" [{"k2" "v2"} {}]) nil)))

  (testing "will group maps when there is at least one matching value for given key"
    (is (= (cluster-using-group-by "k1" [{"k1" "v1"} {}]) [[{"k1" "v1"}]]))))

(deftest about-values-equal
  (testing "will only group matches that are part of the same vector"
    (expect [cluster-using-group-by (->>
        (has-args ["k1" [{"k1" "v1"} {"k1" "v1"}]])
        (returns [[{"k1" "v1"} {"k1" "v1"}]]))]
      (is (= (values-equal "k1" [[{"k1" "v1"} {"k1" "v1"}]]) [[{"k1" "v1"} {"k1" "v1"}]])))
    (expect [cluster-using-group-by (->>
        (has-args ["k1" [{"k1" "v1"}]])
        (times 2)
        (returns [[{"k1" "v1"}]]))]
      (is (= (values-equal "k1" [[{"k1" "v1"}] [{"k1" "v1"}]]) [[{"k1" "v1"}] [{"k1" "v1"}]])))))


(deftest about-number-of-records-in-group-is
  (testing "will create matches only if number of records in group is as given"
    (is (= (number-of-records-in-group-is 2 [[{} {}]]) [[{} {}]]))
    (is (= (number-of-records-in-group-is 2 [[{}]]) []))
    (is (= (number-of-records-in-group-is 2 [[{} {}] [{}]]) [[{} {}]]))
    (is (= (number-of-records-in-group-is 3 [[{} {} {}][{} {}]]) [[{} {} {}]]))
    (is (= (number-of-records-in-group-is 1 [[{} {}] [{}]]) [[{}]]))))

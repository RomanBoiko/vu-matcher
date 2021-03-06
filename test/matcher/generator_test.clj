(ns matcher.generator-test
  (:require 
    [clojure.test :refer :all]
    [matcher.generator :refer :all]))

(deftest about-generate-map-of-size
  (testing "will generate map with given number of entries"
    (is (= (generate-map-of-size 0) {}))
    (is (= (generate-map-of-size 4) {"k1" "v1", "k2" "v2", "k3" "v3", "k4" "v4"}))))

(deftest about-generate-sequence-of-maps
  (testing "will generate sequence of given size with maps of given size"
    (is (= (generate-sequence-of-maps 1 4) [{"k1" "v1", "k2" "v2", "k3" "v3", "k4" "v4"}]))
    (is (= (generate-sequence-of-maps 2 2) [{"k1" "v1", "k2" "v2"} {"k1" "v1", "k2" "v2"}]))))

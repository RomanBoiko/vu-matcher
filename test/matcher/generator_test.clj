(ns matcher.generator-test
  (:require 
    [midje.sweet :refer :all]
    [matcher.generator :refer :all]))

(facts "about `generate-map-of-size`"
  (fact "will generate map with given number of entries"
    (generate-map-of-size 0) => {}
    (generate-map-of-size 4) => {"k1" "v1", "k2" "v2", "k3" "v3", "k4" "v4"}))

(facts "about `generate-sequence-of-maps`"
  (fact "will generate sequence of given size with maps of given size"
    (generate-sequence-of-maps 1 4) => [{"k1" "v1", "k2" "v2", "k3" "v3", "k4" "v4"}]
    (generate-sequence-of-maps 2 2) => [{"k1" "v1", "k2" "v2"} {"k1" "v1", "k2" "v2"}]))
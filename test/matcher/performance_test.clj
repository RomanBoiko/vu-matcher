(ns matcher.performance-test
  (:require
    [matcher.conditions :refer :all]
    [matcher.generator :refer :all]
    [midje.sweet :refer :all]))

(defn performance-wrapper [key list-of-clusters]
  (println "Number of maps in sequence:" (count list-of-clusters)". Size of maps:" (count (first list-of-clusters)))
  (time (condition-values-equal key list-of-clusters)))

(fact "performance test measuring elapsed time of processing"
  (performance-wrapper "k1" (generate-sequence-of-maps 50 5)) => ()
  (performance-wrapper "k1" (generate-sequence-of-maps 500 5)) => ()
  (performance-wrapper "k1" (generate-sequence-of-maps 1000 5)) => ()
  (performance-wrapper "k1" (generate-sequence-of-maps 10000 10)) => ()
  (performance-wrapper "k1" (generate-sequence-of-maps 100000 10)) => ())
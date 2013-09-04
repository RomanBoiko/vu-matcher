(ns matcher.rules
  (:use [matcher.conditions]))

(defn perfect-match-demo-rule [list-of-clusters]
  {"one-to-one amount-price match"
    (->> list-of-clusters
      (values-equal "amount")
      (values-equal "price")
      (number-of-records-in-group-is 2))})

(defn suggested-match-demo-rule [list-of-clusters]
  {"suggested price match"
    (->> list-of-clusters
      (values-equal "price")
      (number-of-records-in-group-is 2))})

(defn ruleset [rules active-records]
  ;not implemented yet
  )

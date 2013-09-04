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
  (loop [records-to-match [active-records]
         matched-groups []
         rules-to-run rules]
    (if (or (empty? rules-to-run) (empty? records-to-match))
        matched-groups
        (let [rule-result ((first rules-to-run) records-to-match)
              non-matched-records (remove (set (flatten (vals rule-result))) (flatten records-to-match))]
          (recur [non-matched-records] (conj matched-groups rule-result) (rest rules-to-run))))))


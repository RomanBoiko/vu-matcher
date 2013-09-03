(ns matcher.conditions)

(defn cluster-using-group-by [key records]
  (vals (dissoc (group-by #(get % key) records) nil)))

(defn values-equal [key list-of-clusters]
  (mapcat (partial cluster-using-group-by key) list-of-clusters))

(defn number-of-records-in-group-is [records-number list-of-clusters]
  (filter #(= (count %) records-number) list-of-clusters))

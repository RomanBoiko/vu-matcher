(ns matcher.conditions (:gen-class))

(defn cluster-using-group-by [key records]
  (vals (dissoc (group-by #(get % key) records) nil)))

(defn condition-values-equal [key list-of-clusters]
  (apply concat (map (partial cluster-using-group-by key) list-of-clusters)))
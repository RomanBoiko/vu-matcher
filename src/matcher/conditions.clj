(ns matcher.conditions (:gen-class))

(defn cluster_using_group_by [condition records]
  (vals (dissoc (group-by condition records) nil)))

(defn apply-cluster [cluster-function condition list-of-clusters]
  (apply concat (map (partial cluster-function condition) list-of-clusters)))


(defn condition-values-equal [key list-of-clusters]
  (apply-cluster cluster_using_group_by #(get % key) list-of-clusters))



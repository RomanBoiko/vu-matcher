(ns matcher.conditions
  (:gen-class))

(defn cluster_using_group_by [condition records]
  (vals (dissoc (group-by condition records) nil)))

(defn apply-cluster [clusterFunction condition listOfClusters]
  (apply concat (map (partial clusterFunction condition) listOfClusters)))


(defn condition-values-equal [key listOfClusters]
  (apply-cluster cluster_using_group_by #(get % key) listOfClusters))



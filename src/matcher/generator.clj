(ns matcher.generator)

(defn generate-map-of-size [x]
  (zipmap 
    (for [k (range 1 (inc x))]
      (str "k" k))
    (for [v (range 1 (inc x))]
      (str "v" v))))

(defn generate-sequence-of-maps [x y]
  (for [s (range 1 (inc x))]
    (generate-map-of-size y)))
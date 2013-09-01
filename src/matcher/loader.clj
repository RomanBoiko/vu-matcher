(ns matcher.loader
  (:require [clojure.data.csv :as csv]
            [matcher.db :as db]
            [clojure.java.io :as io]))

(defn read-csv [file-path]
  (with-open [in-file (io/reader file-path)]
    (doall
      (csv/read-csv in-file))))

(defn csv-to-maps [csv-col]
  (map (partial zipmap (first csv-col)) (rest csv-col)))

(defn move-file [input-file output-file]
  (do
    (.renameTo input-file output-file)
    output-file))

(defn process-files [input-dir processed-dir fn-process-file]
  (doseq [input-file (file-seq input-dir)]
     (if (not= (.getName input-file) (.getName input-dir))
       (fn-process-file (move-file input-file (io/file processed-dir (.getName input-file)))))))

(defn add-records-from-csvfile-to-db [csv-file]
  (let [source-id (db/add-source (.getName csv-file))]
    (db/add-active-records 
      (map #(assoc % :source source-id)
        (csv-to-maps (read-csv csv-file))))))

(defn perform-load [input-dir processed-dir]
  (process-files input-dir processed-dir add-records-from-csvfile-to-db))

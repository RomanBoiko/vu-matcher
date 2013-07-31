(ns matcher.loader (:gen-class)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn read-csv [file-path]
  (with-open [in-file (io/reader file-path)]
    (doall
      (csv/read-csv in-file))))

(defn process-file [input-file output-file fn-process-file]
  (do
    (.renameTo input-file output-file)
    (fn-process-file output-file)))

(defn process-files [input-dir processed-dir failed-dir fn-process-file]
  (doseq [input-file (file-seq input-dir)]
     (if (not= (.getName input-file) (.getName input-dir))
       (process-file
         input-file (io/file processed-dir (.getName input-file)) fn-process-file))))

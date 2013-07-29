(ns matcher.loader (:gen-class)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(defn read-csv [file-path]
  (with-open [in-file (io/reader file-path)]
    (doall
      (csv/read-csv in-file))))

;DOESN'T WORK
(defn process-files [input-dir processed-dir failed-dir fn-process-file]
  (for [file (file-seq input-dir)]
    (let [output-file (java.io.File. processed-dir (.getName file))]
      (.renameTo file output-file)
      (fn-process-file output-file))))

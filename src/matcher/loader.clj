(ns matcher.loader
  (:require [clojure.data.csv :as csv]
            [matcher.db :as db]
            [taoensso.timbre :as log]
            [clojure.java.io :as io]))

(def input-dir (java.io.File. "waiting"))
(def processed-dir (java.io.File. "processed"))

(defn read-csv [file-path]
  (with-open [in-file (io/reader file-path)]
    (doall
      (csv/read-csv in-file))))

(defn csv-to-maps [csv-col]
  (map (partial zipmap (first csv-col)) (rest csv-col)))

(defn move-file [input-file output-file]
  (do
    (log/info (str "moving incoming " input-file " into " output-file))
    (.renameTo input-file output-file)
    output-file))

(defn process-files [input-dir processed-dir fn-process-file]
  (doseq [input-file (file-seq input-dir)]
     (if (not= (.getName input-file) (.getName input-dir))
       (fn-process-file (move-file input-file (io/file processed-dir (.getName input-file)))))))

(defn add-records-from-csvfile-to-db [csv-file]
  (let [source-id (db/add-source (.getName csv-file))
        records (csv-to-maps (read-csv csv-file))]
    (log/info (str "source " (.getName csv-file) " created in db,"
                   "loading " (count records) " records from it"))
    (db/add-active-records
      (map #(assoc % :source source-id) records))))

(defn perform-load [input-dir processed-dir]
  (process-files input-dir processed-dir add-records-from-csvfile-to-db))

(defn loading-process [agent-state]
  (loop []
    (Thread/sleep 2000)
    (log/debug "Looking up to load dir...")
    (perform-load input-dir processed-dir)
    (recur)))

(defn err-handler-fn [ag ex]
    (log/error (str "error occured in loading agent " ag": " ex)))

(defn start-loading []
  (do
    (.mkdir input-dir)
    (.mkdir processed-dir)
    (send (agent 0 :error-handler err-handler-fn) loading-process)))

(ns matcher.loader-test
  (:require [clojure.test :refer :all]
            [matcher.db :as db]
            [matcher.loader :as loader]))

(deftest should-read-csv-file
    (is
      (= 4 (count (loader/read-csv "test/resources/loader-records.csv"))))
    (is
      (= 3 (count (first (loader/read-csv "test/resources/loader-records.csv"))))))
    (is
      (= ["trade_id" "amount" "buy_sell"] (first (loader/read-csv "test/resources/loader-records.csv"))))

(deftest should-convert-csv-contents-to-list-of-maps
  (is (= [{"a" "11" "b" "12"} {"a" "21" "b" "22"}] (loader/csv-to-maps [["a" "b"] ["11" "12"] ["21" "22"]])))
  (is (= [] (loader/csv-to-maps [["a" "b"]]))))

(defn clean-dir [dir]
  (doseq [file-to-delete (file-seq dir)]
    (if (not= (.getName file-to-delete) (.getName dir))
      (.delete file-to-delete))))

(def root-dir (java.io.File. "target/load"))
(def input-dir (java.io.File. root-dir "/waiting"))
(def processed-dir (java.io.File. root-dir "/processed"))
(def test-input-file (java.io.File. input-dir "/test.csv"))
(def test-output-file (java.io.File. processed-dir "/test.csv"))

(defn setup-file-processing-test []
  (do
    (.mkdir root-dir)
    (.mkdir input-dir)
    (.mkdir processed-dir)
    (clean-dir input-dir)
    (clean-dir processed-dir)
    (clojure.java.io/copy (java.io.File. "test/resources/loader-records.csv") test-input-file)))

(deftest should-process-files-itest
  (setup-file-processing-test)
  (is (.exists test-input-file))
  (is (not (.exists test-output-file)))
  (loader/process-files input-dir processed-dir #(println "File-callback: " %))
  (is (.exists test-output-file)))

(deftest should-load-records-from-file-to-db-itest
  (setup-file-processing-test)
  (db/start-db)
  (db/clean-db)
  (loader/perform-load input-dir processed-dir)
  (is (= 1 (count (db/all-sources))))
  (is (= 3 (count (db/all-active-records))))
  (db/stop-db))


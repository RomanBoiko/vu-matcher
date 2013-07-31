(ns matcher.loader-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [matcher.loader :refer :all]))

(deftest should-read-csv-file
    (is
      (= 4 (count (read-csv "test/resources/loader-records.csv"))))
    (is
      (= 6 (count (first (read-csv "test/resources/loader-records.csv"))))))
    (is
      (= ["trade_id" "trade_version" "trade_domain" "amount" "buy_sell" "book"] (first (read-csv "test/resources/loader-records.csv"))))

(defn clean-dir [dir]
  (doseq [file-to-delete (file-seq dir)]
    (if (not= (.getName file-to-delete) (.getName dir))
      (.delete file-to-delete))))

(def root-dir (java.io.File. "target/load"))
(def input-dir (java.io.File. root-dir "/waiting"))
(def processed-dir (java.io.File. root-dir "/processed"))
(def failed-dir (java.io.File. root-dir "/failed"))
(def test-input-file (java.io.File. input-dir "/test.csv"))
(def test-output-file (java.io.File. processed-dir "/test.csv"))

(defn setup-file-processing-test []
  (do
    (.mkdir root-dir)
    (.mkdir input-dir)
    (.mkdir processed-dir)
    (.mkdir failed-dir)
    (clean-dir input-dir)
    (clean-dir processed-dir)
    (clean-dir failed-dir)

    (spit (.getPath test-input-file) "some\ntext")))

(defn test-callback [input-file]
  (println "got in callback: " input-file))

(deftest should-process-files-itest
  (setup-file-processing-test)
  (is (.exists test-input-file))
  (is (not (.exists test-output-file)))
  (process-files input-dir processed-dir failed-dir test-callback)
  (is (.exists test-output-file)))

(facts "about `process-files`"
  (fact "will process only input file from input dir using nested process-file call"
    (process-files input-dir processed-dir failed-dir test-callback) => nil
    (provided (matcher.loader/process-file test-input-file test-output-file test-callback) => nil :times 1)
    (against-background (before :facts (setup-file-processing-test)))))

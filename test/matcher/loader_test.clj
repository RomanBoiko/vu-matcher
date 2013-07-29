(ns matcher.loader-test
  (:require [clojure.test :refer :all]
            [matcher.loader :refer :all]))

(deftest should-read-csv-file
    (is 
      (= 4 (count (read-csv "test/resources/loader-records.csv"))))
    (is 
      (= 6 (count (first (read-csv "test/resources/loader-records.csv"))))))
    (is 
      (= ["trade_id" "trade_version" "trade_domain" "amount" "buy_sell" "book"] (first (read-csv "test/resources/loader-records.csv"))))

(defn clean-dir [dir]
  (map #(.delete (java.io.File. %)) (file-seq dir)))

(deftest should-process-files 
  (let [root (java.io.File. "target/load")
        input (java.io.File. root "/waiting")
        processed (java.io.File. root "/processed")
        failed (java.io.File. root "/failed")
        test-input-file (java.io.File. input "/test.csv")
        test-output-file (java.io.File. processed "/test.csv")]
    (.mkdir root)
    (.mkdir input)
    (.mkdir processed)
    (.mkdir failed)
    (clean-dir input)
    (clean-dir processed)
    (clean-dir failed)
    
    (spit (.getPath test-input-file) "some\ntext")
    
    (is (.exists test-input-file))
    (is (not (.exists test-output-file)))
    (process-files input processed failed #(println %))
    (is (.exists test-output-file))))

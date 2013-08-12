(ns matcher.fleetdb-test
  (:require [clojure.test :refer :all]
            [fleetdb.client :as fdb]))

(def client (fdb/connect {:host "127.0.0.1", :port 3400}))


(deftest should-use-fleetdb
  (client ["delete" "source"])
  (is (= "pong" (fdb/query client ["ping"])))
  (is (= 1 (client ["insert" "source" {"id" 1 "name" "test1.csv"}])))
  (is (= [{"id" 1 "name" "test1.csv"}] (client ["select" "source" {"where" ["=" "id" 1]}]))))

(defn uuid []
  (str (java.util.UUID/randomUUID)))

(deftest should-create-unique-id
  (is (not= (uuid) (uuid))))

(deftest should-use-uuid-as-record-id
  (client ["delete" "source"])
  (is (= 1 (client ["insert" "source" {"id" (uuid) "name" "test1.csv"}]))))

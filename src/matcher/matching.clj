(ns matcher.matching
  (:require [taoensso.timbre :as log]
            [matcher.db :as db]
            [matcher.rules :refer :all]))

(defn err-handler-fn [ag ex]
    (log/error (str "error occured in matching agent " ag": " ex)))

(def matching-agent (agent 0 :error-handler err-handler-fn))

(defn match-active-records []
  (let [active-records (db/all-active-records)
        match-results (ruleset [perfect-match-demo-rule suggested-match-demo-rule] active-records)]
    (doseq [match-type match-results]
      (let [match-name (first (keys match-type))]
        (doseq [group-records (first (vals match-type))]
          (log/info (str "Storing match in db: " match-name "(" group-records ")"))
          (db/convert-active-records-to-matched-group match-name group-records))))))

(defn matching-process [agent-state]
  (loop []
    (log/debug "matching started")
    (match-active-records)
    (log/debug "matching finished")
    (Thread/sleep 5000)
    (recur)))

(defn start-matching []
  (send matching-agent matching-process))

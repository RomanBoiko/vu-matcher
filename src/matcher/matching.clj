(ns matcher.matching
  (:require [taoensso.timbre :as log]
            [matcher.db :as db]))

(defn err-handler-fn [ag ex]
    (log/error (str "error occured in matching agent " ag": " ex)))

(def matching-agent (agent 0 :error-handler err-handler-fn))

(defn match-active-records []
  (let [active-records (db/all-active-records)]
    1))

(defn matching-process [agent-state]
  (loop []
    (log/info "matching started")
    (log/info "matching finished, " (with-out-str (time (match-active-records))))
    (Thread/sleep 5000)
    (recur)))

(defn start-matching []
  (send matching-agent matching-process))

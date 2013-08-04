(ns matcher.dbinstall
  (:require [matcher.db]))

(defn -main [& args]
  (matcher.db/create-dbs))

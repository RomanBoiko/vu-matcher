Matcher
=======

Matching and reconciliations, first attempt

Install
--------

* Leiningen: http://leiningen.org

Commands and links
-------------------

* REPL: lein repl
* Deploy dev db: lein dbdeploy
* Run unit tests in REPL: (do (use 'midje.repl) (autotest))
* Run unit tests in REPL with core test api: (do (use 'matcher.db-test :reload-all) (clojure.test/run-tests 'matcher.db-test))

Matcher
=======

Matching and reconciliations, first attempt

Install
-------

* Leiningen: http://leiningen.org

Dev commands and links
----------------------

* REPL: lein repl
* Deploy dev db: lein dbdeploy
* Start matcher: lein boot
* Run unit tests in REPL: (do (use 'midje.repl) (autotest))
* Run unit tests in REPL with core test api: (do (use 'matcher.db-test :reload-all) (clojure.test/run-tests 'matcher.db-test))

Startup
-------

* Deploy db: ./scripts/db.sh
* Start matcher: ./scripts/matcher.sh

Matcher
=======

Matching and reconciliations, first attempt

Install
--------

* Leiningen: http://leiningen.org

Commands and links
-------------------

* REPL: lein repl
* Run unit tests in REPL: (do (use 'midje.repl) (autotest))
* Run unit tests in REPL with core test api: (do (use 'matcher.fleetdb-test :reload-all) (clojure.test/run-tests 'matcher.fleetdb-test))

Matcher
=======

Matching and reconciliations, first attempt

Install
--------

* Leiningen: http://leiningen.org
* CouchDB: http://wiki.apache.org/couchdb/Installing_on_Ubuntu
* FleetDB: http://fleetdb.org/docs/introduction.html
* mkdir fleetdb; cd fleetdb; wget http://fleetdb.s3.amazonaws.com/fleetdb-standalone.jar; cd ..


Commands and links
-------------------

* REPL: lein repl
* Run unit tests in REPL: (do (use 'midje.repl) (autotest))
* Run unit tests in REPL with core test api: (do (use 'matcher.fleetdb-test :reload-all) (clojure.test/run-tests 'matcher.fleetdb-test))
* Start CouchDB server: ./scripts/couchdb.start.bash
* Start FleetDB server: ./scripts/fleetdb.start.bash
* DB web-console: http://localhost:5984/\_utils/
* Deploy CouchDB: lein dbinstall

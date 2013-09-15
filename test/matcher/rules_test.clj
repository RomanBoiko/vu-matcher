(ns matcher.rules-test
  (:require
    [clojure.test :refer :all]
    [matcher.rules :refer :all]))

(deftest about-perfect-match-demo-rule
  (is (=
    (perfect-match-demo-rule
      [[{"id" 1 "price" 12.3 "amount" 3}
        {"id" 2 "price" 12.3 "amount" 3}
        {"id" 3 "price" 12.3 "amount" 4}
        {"id" 4 "price" 12.4 "amount" 5}
        {"id" 5 "price" 12.4 "amount" 5}
        {"id" 6 "price" 12.4 "amount" 5}
        {"id" 7 "price" 1.1 "amount" 6}
        {"id" 8 "price" 1.1 "amount" 6}
        ]]) {"one-to-one amount-price match" [
                    [{"id" 1 "price" 12.3 "amount" 3}
                     {"id" 2 "price" 12.3 "amount" 3}]
                    [{"id" 7 "price" 1.1 "amount" 6}
                     {"id" 8 "price" 1.1 "amount" 6}]
                  ]
               })))

(deftest about-suggested-match-demo-rule
  (is (=
    (suggested-match-demo-rule
      [[{"id" 1 "price" 12.3 "amount" 3}
        {"id" 2 "price" 12.3 "amount" 4}
        {"id" 3 "price" 12.4 "amount" 4}
        ]])  {"suggested price match" [
                  [{"id" 1 "price" 12.3 "amount" 3}
                   {"id" 2 "price" 12.3 "amount" 4}]
                ]
             })))

(deftest about-ruleset
  (is (=
    (ruleset [perfect-match-demo-rule suggested-match-demo-rule]
      [{"id"  1 "price" 12.3 "amount" 3}
       {"id"  2 "price" 12.3 "amount" 3}
       {"id"  3 "price" 12.3 "amount" 4}
       {"id"  4 "price" 12.4 "amount" 5}
       {"id"  5 "price" 12.4 "amount" 5}
       {"id"  6 "price" 12.4 "amount" 5}
       {"id"  7 "price" 1.2 "amount" 8}
       {"id"  8 "price" 1.2 "amount" 9}
       {"id"  9 "price" 1.1 "amount" 6}
       {"id" 10 "price" 1.1 "amount" 6}
      ])  [
           {"one-to-one amount-price match" [
                  [{"id"  1 "price" 12.3 "amount" 3}
                   {"id"  2 "price" 12.3 "amount" 3}]
                  [{"id"  9 "price" 1.1 "amount" 6}
                   {"id" 10 "price" 1.1 "amount" 6}]
                ]
           }
           {"suggested price match" [
                  [{"id" 7 "price" 1.2 "amount" 8}
                   {"id" 8 "price" 1.2 "amount" 9}]
                ]
           }
          ])))

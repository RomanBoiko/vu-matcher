(ns matcher.rules-test
  (:require
    [midje.sweet :refer :all]
    [matcher.rules :refer :all]))

(facts "about `perfect-match-demo-rule`"
  (fact "will show combination of two or more conditions"
    (perfect-match-demo-rule
      [[{"id" 1 "price" 12.3 "amount" 3}
        {"id" 2 "price" 12.3 "amount" 3}
        {"id" 3 "price" 12.3 "amount" 4}
        {"id" 4 "price" 12.4 "amount" 5}
        {"id" 5 "price" 12.4 "amount" 5}
        {"id" 6 "price" 12.4 "amount" 5}
        {"id" 7 "price" 1.1 "amount" 6}
        {"id" 8 "price" 1.1 "amount" 6}
        ]]) => {"one-to-one amount-price match" [
                    [{"id" 1 "price" 12.3 "amount" 3}
                     {"id" 2 "price" 12.3 "amount" 3}]
                    [{"id" 7 "price" 1.1 "amount" 6}
                     {"id" 8 "price" 1.1 "amount" 6}]
                  ]
               }))

(facts "about `suggested-match-demo-rule`"
  (fact "suggested match in case only price is the same - sides to adjust amounts"
    (suggested-match-demo-rule
      [[{"id" 1 "price" 12.3 "amount" 3}
        {"id" 2 "price" 12.3 "amount" 4}
        {"id" 3 "price" 12.4 "amount" 4}
        ]]) => {"suggested price match" [
                    [{"id" 1 "price" 12.3 "amount" 3}
                     {"id" 2 "price" 12.3 "amount" 4}]
                  ]
               }))

(facts "about `ruleset`"
  (fact "ruleset should produce collection of matches, rules applied in sequence"
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
      ]) => [
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
            ]))

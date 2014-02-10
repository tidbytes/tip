(ns tip.parser-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [tip.parser :refer [grammar]]))

(facts "About whitespace"
  (fact "Source code functions differing only by their whitespace should
         be considered equal after parsing."
    (let [ast (grammar (slurp "test/tip/code/insignificant-whitespace.tip"))
          fns (rest ast)]
      (apply = fns) => true)))
(ns tip.core-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [tip.core :as core]))

(defn printer
  [s]
  s)

(deftest test-main
  (facts "About the main function."
    (fact "The main function should print the 'proper use' wall of text when
           called without any arguments."
      (core/-main))
    (fact "The main function should compile and print a source file if given
           one."
      (core/-main "resources/simple.tip"))))
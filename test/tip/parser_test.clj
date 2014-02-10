(ns tip.parser-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [tip.parser :refer [grammar]]))

(defn parse-tip
  [filename]
  (grammar (slurp (str "test/tip/code/" filename ".tip"))))

(facts "About whitespace"
  (fact "Source code functions differing only by their whitespace should
         be considered equal after parsing."
    (let [parse-tree (parse-tip "insignificant-whitespace")]
      (apply = (rest parse-tree)) => true)))

(facts "About expressions"
  (let [constants (parse-tip "expression-constant")
        identifiers (parse-tip "expression-identifier")
        comparison1 (parse-tip "expression-comparison-less")
        comparison2 (parse-tip "expression-comparison-equal")
        comparison3 (parse-tip "expression-comparison-greater")
        arithmetics1 (parse-tip "expression-arithmetics-addition")
        arithmetics2 (parse-tip "expression-arithmetics-subtraction")
        arithmetics3 (parse-tip "expression-arithmetics-multiplication")
        arithmetics4 (parse-tip "expression-arithmetics-division")
        parentheses (parse-tip "expression-parentheses")
        input (parse-tip "expression-input")
        null (parse-tip "expression-null")
        dynamic-call (parse-tip "expression-dynamic-call")
        function-call (parse-tip "expression-function-call")
        malloc (parse-tip "expression-malloc")]
    (fact "Constants should be expressions."
      constants => (contains :program))
    (fact "Identifiers should be expressions."
      identifiers => (contains :program))
    (fact "Comparisons should be expressions."
      comparison1 => (contains :program)
      comparison2 => (contains :program)
      comparison3 => (contains :program))
    (fact "Arithmetic calculations should be expressions."
      arithmetics1 => (contains :program)
      arithmetics2 => (contains :program)
      arithmetics3 => (contains :program)
      arithmetics4 => (contains :program))
    (fact "Parenthesized expressions should be expressions."
      parentheses => (contains :program))
    (fact "Reading user input should be an expression."
      input => (contains :program))
    (fact "Null references should be expressions."
      null => (contains :program))
    (fact "Dynamic function calls should be expressions."
      dynamic-call => (contains :program))
    (fact "Function calls should be expressions."
      function-call => (contains :program))
    (fact "Allocating memory should be an expression."
      malloc => (contains :program))))
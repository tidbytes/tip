(ns tip.parser-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [tip.parser :refer [grammar]]
            [instaparse.core :refer [failure?]]))

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
      (failure? constants) => false)
    (fact "Identifiers should be expressions."
      (failure? identifiers) => false)
    (fact "Comparisons should be expressions."
      (failure? comparison1) => false
      (failure? comparison2) => false
      (failure? comparison3) => false)
    (fact "Arithmetic calculations should be expressions."
      (failure? arithmetics1) => false
      (failure? arithmetics2) => false
      (failure? arithmetics3) => false
      (failure? arithmetics4) => false)
    (fact "Parenthesized expressions should be expressions."
      (failure? parentheses) => false)
    (fact "Reading user input should be an expression."
      (failure? input) => false)
    (fact "Null references should be expressions."
      (failure? null) => false)
    (fact "Dynamic function calls should be expressions."
      (failure? dynamic-call) => false)
    (fact "Function calls should be expressions."
      (failure? function-call) => false)
    (fact "Allocating memory should be an expression."
      (failure? malloc) => false)))

(facts "About statements"
  (let [assignment (parse-tip "statement-assignment")
        if-short (parse-tip "statement-if")
        if-block (parse-tip "statement-if-block")
        if-else-short (parse-tip "statement-if-else")
        if-else-block (parse-tip "statement-if-else-block")
        output (parse-tip "statement-output")
        var-decl (parse-tip "statement-var-decl")
        while-short (parse-tip "statement-while")
        while-block (parse-tip "statement-while-block")]
    (fact "Assignments of values should be statements."
      (failure? assignment) => false)
    (fact "If statements should be statements."
      (failure? if-short) => false
      (failure? if-block) => false)
    (fact "If-then-else statements should be statements."
      (failure? if-else-short) => false
      (failure? if-else-block) => false)
    (fact "Writing an expression to output should be a statement"
      (failure? output) => false)
    (fact "Variable declarations should be statements."
      (failure? var-decl) => false)
    (fact "While loops should be statements."
      (failure? while-short) => false
      (failure? while-block) => false)))

(facts "About pointers"
  (let [pointer-assignment (parse-tip "pointer-assignment")
        pointer-expression-address (parse-tip "pointer-expression-address")
        pointer-expression-value (parse-tip "pointer-expression-value")
        pointer-parameter (parse-tip "pointer-parameter")]
    (fact "Pointer assignments should be accepted."
      (failure? pointer-assignment) => false)
    (fact "Pointer address operation should be accepted."
      (failure? pointer-expression-address) => false)
    (fact "Pointer value operation should be accepted."
      (failure? pointer-expression-value) => false)
    (fact "Pointers in function parameters should be rejected."
      (failure? pointer-parameter) => true)))
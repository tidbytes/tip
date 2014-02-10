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
      malloc => (contains :program))
    ))

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
      assignment => (contains :program))
    (fact "If statements should be statements."
      if-short => (contains :program)
      if-block => (contains :program))
    (fact "If-then-else statements should be statements."
      if-else-short => (contains :program)
      if-else-block => (contains :program))
    (fact "Writing an expression to output should be a statement"
      output => (contains :program))
    (fact "Variable declarations should be statements."
      var-decl => (contains :program))
    (fact "While loops should be statements."
      while-short => (contains :program)
      while-block => (contains :program))))

(facts "About pointers"
  (let [pointer-assignment (parse-tip "pointer-assignment")
        pointer-expression-address (parse-tip "pointer-expression-address")
        pointer-expression-value (parse-tip "pointer-expression-value")
        pointer-parameter (parse-tip "pointer-parameter")]
    (fact "Pointer assignments should be accepted."
      pointer-assignment => (contains :program))
    (fact "Pointer address operation should be accepted."
      pointer-expression-address => (contains :program))
    (fact "Pointer value operation should be accepted."
      pointer-expression-value => (contains :program))
    (fact "Pointers in function parameters should be rejected."
      (failure? pointer-parameter) => true)))
(ns tip.parser-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [tip.parser :refer [grammar]]
            [instaparse.core :refer [failure?]]))

(defn- parse-tip
  [filename]
  "Internal function used to avoid writing out full paths and parse calls for
   TIP source files."
  (grammar (slurp (str "test/tip/code/" filename ".tip"))))

(defn- parse-status
  [succeeds]
  "Internal function used to avoid duplication in `succeeds` and `fails`."
  (fn [string filenames]
    (fact string
      (doseq [f filenames]
        (failure? (parse-tip f)) => (not succeeds)))))

(defn succeeds
  [string & filenames]
  "Shorter notation for expressing 'all given filenames must be parsable TIP
   programs' using the fact string `string`.

   Unfolds to:
   (fact string
     (failure? (parse-tip filename1)) => false
     (failure? (parse-tip filename2)) => false
     ...)"
  ((parse-status true) string filenames))

(defn fails
  [string & filenames]
  "Shorter notation for expressing 'all given filenames must NOT be parsable
   TIP programs' using the fact string `string`.
   
   Unfolds to:
   (fact string
     (failure? (parse-tip filename1)) => true
     (failure? (parse-tip filename2)) => true
     ...)"
  ((parse-status false) string filenames))

(facts "About whitespace"
  (fact "Source code functions differing only by their whitespace should
         be considered equal after parsing."
    (let [parse-tree (parse-tip "insignificant-whitespace")]
      (apply = (rest parse-tree)) => true)))

(facts "About expressions"
  (succeeds"Constants should be expressions."
    "expression-constant")
  (succeeds "Identifiers should be expressions."
    "expression-identifier")
  (succeeds "Comparisons should be expressions."
    "expression-comparison-less"
    "expression-comparison-equal"
    "expression-comparison-greater")
  (succeeds "Arithmetic calculations should be expressions."
    "expression-arithmetics-addition"
    "expression-arithmetics-subtraction"
    "expression-arithmetics-multiplication"
    "expression-arithmetics-division")
  (succeeds "Parenthesized expressions should be expressions."
    "expression-parentheses")
  (succeeds "Reading user input should be an expression."
    "expression-input")
  (succeeds "Null references should be expressions."
    "expression-null")
  (succeeds "Dynamic function calls should be expressions."
    "expression-dynamic-call")
  (succeeds "Function calls should be expressions."
    "expression-function-call")
  (succeeds "Allocating memory should be an expression."
    "expression-malloc"))

(facts "About statements"
  (succeeds "Assignments of values should be statements."
    "statement-assignment")
  (succeeds "If statements should be statements."
    "statement-if"
    "statement-if-block")
  (succeeds "If-then-else statements should be statements."
    "statement-if-else"
    "statement-if-else-block")
  (succeeds "Writing an expression to output should be a statement"
    "statement-output")
  (succeeds "Variable declarations should be statements."
    "statement-var-decl")
  (fact "While loops should be statements."
    "statement-while"
    "statement-while-block"))

(facts "About pointers"
  (succeeds "Pointer assignments should be accepted."
    "pointer-assignment")
  (succeeds "Pointer address operation should be accepted."
    "pointer-expression-address")
  (succeeds "Pointer value operation should be accepted."
    "pointer-expression-value")
  (succeeds "Pointers in function parameters should be rejected."
    "pointer-parameter"))
(ns tip.parser
  (:require [instaparse.core :refer [parser]]))

(def ebnf (slurp "src/tip/ebnf.txt"))

(def grammar
  (parser ebnf))
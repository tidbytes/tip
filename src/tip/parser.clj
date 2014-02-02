(ns tip.parser
  (:require [instaparse.core :refer [parser]]))

(def ebnf (slurp "src/tip/tip.ebnf"))

(def grammar
  (parser ebnf))
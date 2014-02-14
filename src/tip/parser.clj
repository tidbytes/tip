(ns tip.parser
  (:require [instaparse.core :refer []]))

(def
  parser
  "The main function of `tip.parser`. This is the actual parser, and it is
   called with a string containing a TIP source code program.

   Example: (parser \"inc(n) { return n+1; }\""
  (instaparse.core/parser (slurp "src/tip/tip.ebnf")))
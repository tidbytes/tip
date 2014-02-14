(ns tip.parser
  (:require [instaparse.core :refer []]))

(def
  ^{:private true}
  ebnf
  (slurp "src/tip/tip.ebnf"))

(def
  ^{:doc
  	"The main function of `tip.parser`. This is the actual parser, and it is
  	 called with a string containing a TIP source code program.

  	 Example: (parser \"inc(n) { return n+1; }\""}
  parser
  (instaparse.core/parser ebnf))
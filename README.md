# TIP

A barebone compiler for the academic static analysis playground language Tiny Imperative Programming.

## Status

**Master branch**

![Build status](https://www.codeship.io/projects/bd9e0000-7597-0131-79fd-32f3c34aacd4/status?branch=master)

[![Coverage Status](https://coveralls.io/repos/tidbytes/tip/badge.png)](https://coveralls.io/r/tidbytes/tip)

**Latest commit**

![Build status](https://www.codeship.io/projects/bd9e0000-7597-0131-79fd-32f3c34aacd4/status)

## Purpose

TIP is meant as a playground for various static analysis tools. It is meant to continually expand its capabilities as I progress through the Static Analysis course.

### Current features

* Lexer/parser creating a nice abstract syntax tree of the source code in [hiccup style](https://github.com/weavejester/hiccup).

### Upcoming features

* Control flow graph.

## Use

All use of TIP is currently intended to happen through  [Leiningen](https://github.com/technomancy/leiningen/), which is all but a necessity for using the project at all at this stage.

### Compilation

Having installed Leiningen, the easiest way to fire up the compiler is to run
```bash
lein run myfile.tip
```
which will pass `myfile.tip` through the stages currently implemented by the compiler project and present the result.

### Testing

If you want to run the test suite, simply run
```bash
lein midje
```
to run the full suite of behavioral tests.

### Exploration

If you want to explore the functions provided by the TIP compiler, you should run
```bash
lein repl
```
and `use` the namespaces you desire.

An example of an exploratory REPL session could be:

```clojure
(use 'tip.parser)
;; => nil

(def program "inc(number) { return number + 1; }")
;; => #'tip.core/program

(def parsed-program
  (grammar program))
;; => #'tip.core/parsed-program

(clojure.pprint/pprint parsed-program)
;; => [:program
;;     [:function
;;      [:identifier "inc"]
;;      [:arg-list [:identifier "number"]]
;;      [:return [:addition [:identifier "number"] [:constant "1"]]]]]
;; => nil
```

## License

Let's say GPLv3. Why not?

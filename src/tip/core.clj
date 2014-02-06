(ns tip.core
  (:require [clojure.pprint :refer [pprint]]
            [tip.parser :refer [grammar]]))

(defn -main
  [& args]
  (-> "resources/simple.tip"
      slurp
      grammar
      pprint))
(ns tip.core
  (:require [clojure.pprint :refer [pprint]]
            [tip.parser :refer [grammar]]))

(defn -main
  [& args]
  (if args
    (doseq [file args]
      (println "Parsing" file)
      (-> file
        slurp
        grammar
        pprint))
    (println "You did not supply any TIP source file.\n"
             "- Proper use:\n"
             "  lein run mysource.tip")))
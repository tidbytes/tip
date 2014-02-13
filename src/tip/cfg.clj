(ns tip.cfg
  (require [loom.graph :refer [digraph]]
           [loom.attr :refer [add-attr]]
           [clojure.string :refer [join]]))

(declare program-cfg function->cfg)

(def counter (atom 0))

(defn get-id
  []
  (swap! counter inc))

(defn program->cfg
  [program]
  (map function->cfg (rest program)))

(defn function->cfg
  [[_
    [_ id]
    [_ & args]
    & body]]
  (let [graph-id (get-id)
        g (digraph graph-id)
        params (map second args)]
    (-> g
      (add-attr id :label (str "{ " id "(" (join "," params) ") }")))))
    
(defn cfg
  [ast]
  (program->cfg ast))
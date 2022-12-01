(ns day05
  (:require [clojure.string :as str]))

(def ids
  (as-> "resources/day05.txt" $
    (slurp $)
    (str/replace $ #"F|L" "0")
    (str/replace $ #"B|R" "1")
    (str/split-lines $)
    (map #(Integer/parseInt % 2) $)
    (sort $)))

(def answer1 (last ids))

(def answer2 (- (/ (* (+ (first ids) (last ids)) (inc (count ids))) 2) (reduce + ids)))

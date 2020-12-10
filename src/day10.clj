(ns day10
  (:require [clojure.string :as str]))

(def input
  (as-> "resources/day10.txt" $
    (slurp $)
    (str/split-lines $)
    (map read-string $)
    (sort $)
    (concat [0] $ [(+ (last $) 3)])
    (vec $)))

(def answer1
  (let [freq (frequencies (map (fn [[i j]] (- j i)) (partition 2 1 input)))]
    (* (freq 1) (freq 3))))

(def max-index (dec (count input)))

(def solve
  (memoize
   (fn [base-jolts index]
     (if (< index max-index)
       (let [index-jolts (input index)
             next-index (inc index)
             next-jolts (input next-index)
             rec-solve (solve index-jolts next-index)]
         (if (<= (- next-jolts base-jolts) 3)
           (+ rec-solve (solve base-jolts next-index))
           rec-solve))
       1))))

(def answer2 (solve 0 1))

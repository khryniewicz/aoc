(ns day09
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))

(def input (mapv read-string (str/split-lines (slurp "resources/day09.txt"))))

(def answer1
  (loop [i 25]
    (if (some #{(nth input i)} (map #(reduce + %) (combo/combinations (subvec input (- i 25) i) 2)))
      (recur (inc i))
      (nth input i))))

(def answer2
  (loop [start 0
         end 1]
  (let [slice (subvec input start end)
        sum (apply + slice)]
    (cond
      (< sum answer1) (recur start (inc end))
      (> sum answer1) (recur (inc start) end)
      :else (+ (apply min slice) (apply max slice))))))

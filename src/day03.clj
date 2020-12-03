(ns day03
  (:require [clojure.string :as str]))

(def input (str/split-lines (slurp "resources/day03.txt")))

(defn count-trees [[right down]]
  (->> (take-nth down input)
       (map-indexed #(get %2 (mod (* %1 right) (count %2))))
       (filter #{\#})
       (count)))

(def answer1 (count-trees [3 1]))

(def answer2 (reduce * (map count-trees [[1 1] [3 1] [5 1] [7 1] [1 2]])))

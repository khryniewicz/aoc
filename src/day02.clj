(ns day02
  (:require [clojure.string :as str]))

(defn parse [line]
  (let [[i j c w] (str/split line #"-| |: ")]
    [(Integer/parseInt i) (Integer/parseInt j) (first c) w]))

(def input (map parse (str/split-lines (slurp "resources/day02.txt"))))

(defn valid-occurance? [[i j c w]]
  (<= i (count (filter #{c} w)) j))

(def answer1 (count (filter valid-occurance? input)))

(defn valid-positions? [[i j c w]]
  (= (count (filter #{c} (map #(nth w (dec %)) [i j]))) 1))

(def answer2 (count (filter valid-positions? input)))

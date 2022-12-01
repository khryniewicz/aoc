(ns day06
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(def input (str/split (slurp "resources/day06.txt") #"\n\n"))

(def answer1 (apply + (map #(count (remove #{\newline} (set %))) input)))

(def answer2 (apply + (map #(count (apply set/intersection (map set (str/split-lines %)))) input)))

(ns day01
  (:require [clojure.java.io :as io]))

(def input (-> "day01.txt" io/resource io/reader line-seq))

(def entries (sort (map read-string input)))

(defn match-sum? [coll sum elm]
  (some #(= (- sum elm) %) coll))

(defn find-sum [sum]
  (loop [i 0]
    (when (< i (count entries))
      (let [a (nth entries i)]
        (if (match-sum? sum a)
          [a (- sum a)]
          (recur (inc i)))))))

; answer 1
(println (reduce * (find-sum 2020)))

(def find-3
  (loop [i 0]
    (let [a (nth entries i)
          r (find-sum (- 2020 a))]
      (if r
        (into [a] r)
        (recur (inc i))))))

; answer 2
(println (reduce * find-3))

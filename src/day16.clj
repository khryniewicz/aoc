(ns day16
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(def input
  (let [[p1 p2 p3] (map str/split-lines (str/split (slurp "resources/day16.txt") #"\n\n"))]
    {:rules (reduce #(let [[field ranges] (str/split %2 #": ")
                           [l1 h1 l2 h2] (map read-string (re-seq #"\d+" ranges))]
                       (assoc %1 field (set (concat (range l1 (inc h1)) (range l2 (inc h2))))))
                    {} p1)
     :ticket (map read-string (str/split (p2 1) #","))
     :scanned (map #(map read-string (str/split % #",")) (rest p3))}))

(def answer1
  (let [valid-v (apply set/union (vals (input :rules)))]
    (apply + (remove (partial contains? valid-v) (flatten (input :scanned))))))

(def answer2
  (let [targets (set (filter #(str/includes? % "departure") (keys (input :rules))))
        valid-v (apply set/union (vals (input :rules)))
        tickets (filter #(every? (partial contains? valid-v) %) (input :scanned))
        v->fields (fn [v] (set (keys (filter #(contains? (val %) v) (input :rules)))))
        i->fields (fn [i] (apply set/intersection (map v->fields (map #(nth % i) tickets))))]
    (->> (range (count (input :rules)))
         (reduce #(assoc %1 %2 (i->fields %2)) {})
         (sort-by #(count (val %)))
         (reduce #(assoc %1 (first (set/difference (val %2) (keys %1))) (key %2)) {})
         (filter #(contains? targets (key %)))
         (vals)
         (map #(nth (input :ticket) %))
         (apply *))))

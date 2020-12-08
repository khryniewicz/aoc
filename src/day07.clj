(ns day07
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn assoc-content [coll s]
  (assoc coll (subs s 2) (Integer/parseInt (subs s 0 1))))

(defn assoc-line [coll line]
  (let [[k c] (str/split line #" bags contain ")]
    (assoc coll k (reduce assoc-content {} (str/split c #",")))))

(def input
  (as-> "resources/day07.txt" $
    (slurp $)
    (str/replace $ #"(no other)? bags?(,|\.) ?" ",")
    (str/split-lines $)
    (reduce assoc-line {} $)))

(defn assoc-invert [coll [k vs]]
  (reduce #(assoc %1 %2 (conj (coll %2 #{}) k)) coll (keys vs)))

(def to-outer (reduce assoc-invert {} input))

(defn count-outer [root]
  (loop [to-visit #{root}
         visited #{}]
    (if (empty? to-visit)
      (dec (count visited))
      (let [v (first to-visit)
            visited (conj visited v)
            to-add (set/difference (to-outer v) visited)
            to-visit (set/union (disj to-visit v) to-add)]
        (recur to-visit visited)))))

(def answer1 (count-outer "shiny gold"))

(defn count-inside [root]
  (let [content (input root)]
    (if (empty? content)
      0
      (apply + (map #(* (inc (count-inside %)) (content %)) (keys content))))))

(def answer2 (count-inside "shiny gold"))

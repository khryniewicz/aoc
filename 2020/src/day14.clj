(ns day14
  (:require [clojure.math.numeric-tower :as math]
            [clojure.string :as str]))

(def input
  (map str/split-lines (rest (str/split (slurp "resources/day14.txt") #"mask = "))))

(defn process [block]
  (let [apply-m (Long/parseLong (str/replace (first block) #"X" "0") 2)
        select-x (Long/parseLong (str/replace (str/replace (first block) #"1" "0") #"X" "1") 2)]
    (map #(let [[address value] (re-seq #"\d+" %)]
            [(Integer/parseInt address) (bit-or apply-m (bit-and select-x (Long/parseLong value)))])
         (rest block))))

(def answer1
  (apply + (vals (reduce #(assoc %1 (%2 0) (%2 1)) {} (apply concat (map process input))))))

(defn format-mask [mask value length]
  (reduce #(str/replace-first %1 #"X" (str %2)) mask
          (seq (str/replace (format (format "%%%ds" length) (Integer/toString value 2)) #" " "0"))))

(defn process2 [block]
  (let [mask (first block)
        ignore-x (Long/parseLong (str/replace (str/replace mask #"0" "1") #"X" "0") 2)
        freq-x ((frequencies mask) \X)
        comb-m (map #(Long/parseLong (format-mask mask % freq-x) 2) (range (math/expt 2 freq-x)))]
    (map #(let [[address value] (re-seq #"\d+" %)
                intermediate (bit-and ignore-x (Long/parseLong address))]
            [(map (partial bit-or intermediate) comb-m) (Integer/parseInt value)])
         (rest block))))

(def answer2
  (apply + (vals (reduce (fn [coll [addresses value]] (reduce #(assoc %1 %2 value) coll addresses))
                         {} (apply concat (map process2 input))))))

(ns day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input (-> "day02.txt" io/resource io/reader line-seq))

(defn valid-row? [row]
  (let [[f1 f2 c pw] (str/split (str/replace row #"-|: " " ") #" ")]
    (<= (read-string f1) (get (frequencies pw) (first c) 0) (read-string f2))))

; answer 1
(frequencies (map valid-row? input))

(defn xor [a b]
  (and (not (and a b)) (or a b)))

(defn valid-char? [w p c]
  (= (nth w (- (read-string p) 1)) (first c)))

(defn valid-row2? [row]
 (let [[p1 p2 c pw] (str/split (str/replace row #"-|: " " ") #" ")]
   (xor (valid-char? pw p1 c) (valid-char? pw p2 c))))

; answer 2
(frequencies (map valid-row2? input))

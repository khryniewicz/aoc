(ns day08
  (:require [clojure.string :as str]))

(def input (mapv #(str/split % #" ") (str/split-lines (slurp "resources/day08.txt"))))

(defn resolve-instruction [s v]
  ((resolve (symbol (subs s 0 1))) v (Integer/parseInt (subs s 1))))

(defn execute [instructions acc index visited]
  (cond
    (contains? visited index) {:acc acc :status :loop}
    (= index (count instructions)) {:acc acc :status :ok}
    :else
    (let [visited (conj visited index)
          [k s] (nth instructions index)]
      (cond
        (= k "acc") (execute instructions (resolve-instruction s acc) (inc index) visited)
        (= k "jmp") (execute instructions acc (resolve-instruction s index) visited)
        (= k "nop") (execute instructions acc (inc index) visited)))))

(def answer1 (:acc (execute input 0 0 #{})))

(defn switch [index]
  (let [[k s] (nth input index)]
    (cond
      (= k "nop") [(assoc input index ["jmp" s]) (inc index)]
      (= k "jmp") [(assoc input index ["nop" s]) (inc index)]
      :else (switch (inc index)))))

(def answer2
  (loop [switch-index 0]
    (let [[instructions switch-index] (switch switch-index)
          {:keys [acc status]} (execute instructions 0 0 #{})]
      (if (= status :ok) acc (recur switch-index)))))

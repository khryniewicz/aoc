(ns day11)

(def input (mapv vec (clojure.string/split-lines (slurp "resources/day11.txt"))))

(def maxx (count input))

(def maxy (count (input 0)))

(defn count-adjecent-occupied [layout x y]
  ((frequencies (drop 1 (for [i (filter #(< -1 % maxx) [x (dec x) (inc x)])
                              j (filter #(< -1 % maxy) [y (dec y) (inc y)])]
                          ((layout i) j)))) \# 0))

(defn count-occupied-at-equilibrium [count-occupied-fn tolerate]
  (loop [p-layout input]
    (let [layout (mapv vec (partition maxy (for [x (range 0 maxx)
                                                 y (range 0 maxy)]
                                             (let [seat ((get p-layout x) y)
                                                   o (count-occupied-fn p-layout x y)]
                                               (cond
                                                 (and (#{\L} seat) (= o 0)) \#
                                                 (and (#{\#} seat) (> o tolerate)) \L
                                                 :else seat)))))]
      (if (= p-layout layout)
        ((frequencies (flatten layout)) \#)
        (recur layout)))))

(def answer1 (count-occupied-at-equilibrium count-adjecent-occupied 3))

(def directions [[-1 -1] [-1 0] [-1 1] [0 -1] [0 1] [1 -1] [1 0] [1 1]])

(defn see-occupied? [layout x-p y-p x-d y-d]
  (let [x (+ x-p x-d)
        y (+ y-p y-d)]
    (if (and (< -1 x maxx) (< -1 y maxy))
      (let [seat ((layout x) y)]
        (cond
          (= seat \#) 1
          (= seat \L) 0
          :else (see-occupied? layout x y x-d y-d)))
      0)))

(defn count-visible-occupied [layout x y]
  (apply + (for [[x-d y-d] directions] (see-occupied? layout x y x-d y-d))))

(def answer2 (count-occupied-at-equilibrium count-visible-occupied 4))

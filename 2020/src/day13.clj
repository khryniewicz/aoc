(ns day13)

(def input (str/split-lines (slurp "resources/day13.txt")))

(def answer1
  (let [timestamp (read-string (first input))
        ids (map read-string (clojure.string/split (second input) #",(x,)*"))]
    (apply * (first (sort-by second (reduce #(assoc %1 %2 (- %2 (mod timestamp %2))) {} ids))))))

(defn parse [s]
  (->> (clojure.string/split s #",")
       (map-indexed #(vector (read-string %2) %1))
       (filter (fn [[k v]] (not= k 'x)))))

(defn calc-pair [[b1 o1] [b2 o2]]
  (let [b (clojure.math.numeric-tower/lcm b1 b2)]
    (loop [ts (- b1 o1)]
      (if (= (mod (+ ts o2) b2) 0)
        [b (- b ts)]
        (recur (+ ts b1))))))

(def answer2
  (let [[p t] (reduce calc-pair (parse (second input)))]
    (- p t)))

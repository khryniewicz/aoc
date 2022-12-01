(ns day12)

(defn parse [line]
  (let [[d v] (split-at 1 line)]
    [(first d) (Integer/parseInt (apply str v))]))

(def input (map parse (clojure.string/split-lines (slurp "resources/day12.txt"))))

(def val2dir [\N \E \S \W])

(defn move [[r x y] [d v]]
  (case d
    \F (move [r x y] [(val2dir r) v])
    \R [(mod (+ r (/ v 90)) 4) x y]
    \L [(mod (- r (/ v 90)) 4) x y]
    \N [r x (+ y v)]
    \S [r x (- y v)]
    \E [r (+ x v) y]
    \W [r (- x v) y]))

(def answer1 (let [[d x y] (reduce move [1 0 0] input)] (+ (Math/abs x) (Math/abs y))))

(defn turn [[sx sy wx wy]]
  [sx sy wy (- wx)])

(defn move2 [[sx sy wx wy] [d v]]
  (case d
    \F [(+ sx (* wx v)) (+ sy (* wy v)) wx wy]
    \R (nth (iterate turn [sx sy wx wy]) (/ v 90))
    \L (nth (iterate turn [sx sy wx wy]) (- 4 (/ v 90)))
    \N [sx sy wx (+ wy v)]
    \S [sx sy wx (- wy v)]
    \E [sx sy (+ wx v) wy]
    \W [sx sy (- wx v) wy]))

(def answer2 (let [[x y wx wy] (reduce move2 [0 0 10 1] input)] (+ (Math/abs x) (Math/abs y))))

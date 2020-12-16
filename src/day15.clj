(ns day15)

(def input [6 13 1 15 2 0])

(defn memory-game [starting end]
  (loop [turn (count starting)
         recent (last starting)
         memory (transient (zipmap (butlast starting) (range 1 (count starting))))]
    (if (= turn end)
      recent
      (recur (inc turn) (- turn (memory recent turn)) (assoc! memory recent turn)))))

(def answer1 (memory-game input 2020))

(def answer2 (memory-game input 30000000))

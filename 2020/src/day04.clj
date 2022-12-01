(ns day04
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]))

(def req-keys '(::byr ::iyr ::eyr ::hgt ::hcl ::ecl ::pid))

(defn parse [block]
  (->> (str/split block #" |\n")
       (map #(str/split % #":"))
       (reduce #(assoc %1 (keyword (str *ns*) (nth %2 0)) (nth %2 1)) {})))

(def input (map parse (str/split (slurp "resources/day04.txt") #"\n\n")))

(def answer1 (count (filter true? (map #(every? % req-keys) input))))

(s/def ::passport (s/keys :req (req-keys)))
(s/def ::byr (s/and (s/conformer read-string) integer? #(<= 1920 % 2002)))
(s/def ::iyr (s/and (s/conformer read-string) integer? #(<= 2010 % 2020)))
(s/def ::eyr (s/and (s/conformer read-string) integer? #(<= 2020 % 2030)))
(s/def ::hgt (s/or :cm (s/and #(= (count %) 5)
                              #(= "cm" (subs % 3 5))
                              (s/conformer #(read-string (subs % 0 3)))
                              #(<= 150 % 193))
                   :in (s/and #(= (count %) 4)
                              #(= "in" (subs % 2 4))
                              (s/conformer #(read-string (subs % 0 2)))
                              #(<= 59 % 76))))
(s/def ::hcl (s/and #(= (count %) 7) #(= (first %) \#)
                    (fn [v] (every? #(Character/isLetterOrDigit %) (subs v 1 7)))))
(s/def ::ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(s/def ::pid (s/and #(= (count %) 9) (fn [v] (every? #(Character/isDigit %) v))))

(def answer2 (count (filter true? (map #(s/valid? ::passport %) input))))

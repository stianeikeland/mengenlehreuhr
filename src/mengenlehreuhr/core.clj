(ns mengenlehreuhr.core
  (:require [clj-time.core :as clj-time]))

(def lamp-minute-pattern
  (take 11 (cycle [:yellow :yellow :red])))

(defn get-seconds [time]
  (rem (+ (clj-time/second time) 1) 2))

(defn get-hours-5 [time]
  (quot (clj-time/hour time) 5))

(defn get-hours-1 [time]
  (rem (clj-time/hour time) 5))

(defn get-minutes-5 [time]
  (quot (clj-time/minute time) 5))

(defn get-minutes-1 [time]
  (rem (clj-time/minute time) 5))

(defn lampcolor-second [count]
  (if (zero? count) [:yellow/off] [:yellow/on]))

(defn make-lamp [color state]
  (keyword (name color) (name state)))

(defn lampcolor-4 [count color]
  (loop [x count acc []]
    (if (> x 0)
    	(recur (dec x) (conj acc (make-lamp color :on)))
     	(concat acc (take (- 4 count) (repeat (make-lamp color :off)))))))

(defn lampcolor-hours [count]
  (lampcolor-4 count :red))

(defn lampcolor-minutes-1 [count]
  (lampcolor-4 count :yellow))

(defn lampcolor-minutes-5-state [count]
  (loop [x count acc []]
    (if (> x 0)
    	(recur (dec x) (conj acc :on))
     	(concat acc (take (- 11 count) (repeat :off))))))

(defn lampcolor-minutes-5 [count]
  (map #(make-lamp %1 %2)
       lamp-minute-pattern
       (lampcolor-minutes-5-state count)))

(defn lamp [time]
  [(lampcolor-second (get-seconds time))
   (lampcolor-hours (get-hours-5 time))
   (lampcolor-hours (get-hours-1 time))
   (lampcolor-minutes-5 (get-minutes-5 time))
   (lampcolor-minutes-1 (get-minutes-1 time))])

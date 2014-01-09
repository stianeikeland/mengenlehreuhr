(ns mengenlehreuhr.core
  (:require [clj-time.core :as clj-time]))

(def lamp-minute-pattern
  (take 11 (cycle [:yellow :yellow :red])))

(defn get-seconds [time]
  (rem (+ (clj-time/second time) 1) 2))

; Every program needs a macro...
(defmacro def-timepart-extractors [partname partfunction]
  (let [make-name #(symbol (str "get-" (str partname) "-" (str %1)))]
    `(do
       (defn ~(make-name 5) [time#] 
         (quot (~partfunction time#) 5))
       (defn ~(make-name 1) [time#] 
         (rem  (~partfunction time#) 5)))))

(def-timepart-extractors hours clj-time/hour)
(def-timepart-extractors minutes clj-time/minute)

(defn lampcolor-second [count]
  (if (zero? count) [:yellow/off] [:yellow/on]))

(defn make-lamp [color state]
  (keyword (name color) (name state)))

(defn lampcolor-4 [count color]
  (->
    (repeat count (make-lamp color :on))
    (concat (take (- 4 count) (repeat (make-lamp color :off))))))

(defn lampcolor-hours [count]
  (lampcolor-4 count :red))

(defn lampcolor-minutes-1 [count]
  (lampcolor-4 count :yellow))

(defn lampcolor-minutes-5-state [count]
  (-> 
    (repeat count :on)
    (concat (take (- 11 count) (repeat :off)))))

(defn lampcolor-minutes-5 [count]
  (map make-lamp
       lamp-minute-pattern
       (lampcolor-minutes-5-state count)))

(defn lamp [time]
  [(lampcolor-second (get-seconds time))
   (lampcolor-hours (get-hours-5 time))
   (lampcolor-hours (get-hours-1 time))
   (lampcolor-minutes-5 (get-minutes-5 time))
   (lampcolor-minutes-1 (get-minutes-1 time))])

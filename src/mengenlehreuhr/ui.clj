(ns mengenlehreuhr.ui
  (:use [quil.core]
        [mengenlehreuhr.core])
  (:require [clj-time.local :as clj-time]))

(def colors {:red/on [255 5 5]
             :red/off [97 29 29]
             :yellow/on [252 252 3]
             :yellow/off [97 90 13]})

(defn setup []
  (smooth)
  (frame-rate 10)
  (background 160))

(defn draw-seconds [time]
  (apply fill (colors (first (first time))))
  (ellipse 322 70 100 100))

(defn draw-square-lamp [color x y width]
  (apply fill (colors color))
  (rect x y width 60))

(defn draw-lamp-sequence [lamps y-offset lamp-offset width]
  (let [x-offsets (iterate (partial + lamp-offset) 100)]
    (doseq [[lamp x-offset] (map list lamps x-offsets)]
      (draw-square-lamp lamp x-offset y-offset width))))

(defn draw []
  (stroke 0)
  (stroke-weight 1)

  (let [time (lamp (clj-time/local-now))]
    (draw-seconds time)
    (draw-lamp-sequence (nth time 1) 135 115 100)
    (draw-lamp-sequence (nth time 2) 210 115 100)
    (draw-lamp-sequence (nth time 3) 285 41 36)
    (draw-lamp-sequence (nth time 4) 360 115 100)))

(defn -main [& arg-list]
  (sketch
  	:title "Mengenlehreuhr"
  	:setup setup
  	:draw draw
  	:size [640 480]))

(-main)
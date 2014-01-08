(ns mengenlehreuhr.core-test
  (:require [clojure.test :refer :all]
            [clj-time.core :as time]
            [mengenlehreuhr.core :refer :all]))

(deftest get-second-test
  (testing "Seconds count"
    (is (= 1 (get-seconds (time/today-at 00 00 00))))
    (is (= 0 (get-seconds (time/today-at 00 00 01))))))

(deftest get-hours-5-test
  (testing "5 hours count"
    (is (= 0 (get-hours-5 (time/today-at 00 00 00))))
    (is (= 1 (get-hours-5 (time/today-at 05 00 00))))
    (is (= 1 (get-hours-5 (time/today-at 06 00 00))))))

(deftest get-hours-1-test
  (testing "1 hours count"
    (is (= 0 (get-hours-1 (time/today-at 00 00 00))))
    (is (= 1 (get-hours-1 (time/today-at 01 00 00))))
    (is (= 1 (get-hours-1 (time/today-at 06 00 00))))))

(deftest get-minutes-5-test
  (testing "5 minutes count"
    (is (= 3 (get-minutes-5 (time/today-at 13 17 01))))))

(deftest lampcolor-second-test
  (testing "Lamp color second"
    (is (= [:yellow/off] (lampcolor-second 0)))
    (is (= [:yellow/on] (lampcolor-second 1)))))

(deftest lampcolor-hours-test
  (testing "Lamp color hours"
    (is (= [:red/off :red/off :red/off :red/off] (lampcolor-hours 0)))
    (is (= [:red/on :red/on :red/on :red/off] (lampcolor-hours 3)))))

(deftest lampcolor-minutes-1-test
  (testing "Lamp color minutes"
    (is (= [:yellow/off :yellow/off :yellow/off :yellow/off] (lampcolor-minutes-1 0)))
    (is (= [:yellow/on :yellow/on :yellow/on :yellow/off] (lampcolor-minutes-1 3)))))

(deftest lampcolor-minutes-5-test
  (testing "Lamp color minutes 5"
    (is (= (take 11 (cycle [:yellow/off :yellow/off :red/off]))
           (lampcolor-minutes-5 0)))
    (is (= [:yellow/on :yellow/on :red/on
            :yellow/off :yellow/off :red/off
            :yellow/off :yellow/off :red/off
            :yellow/off :yellow/off]
           (lampcolor-minutes-5 3)))))

(deftest lamp-test
  (testing "Whole lamp"
    (is (=
          [[:yellow/off]
           [:red/on :red/on :red/off :red/off]
           [:red/on :red/on :red/on :red/off]
           [:yellow/on :yellow/on :red/on :yellow/off :yellow/off :red/off :yellow/off :yellow/off :red/off :yellow/off :yellow/off]
           [:yellow/on :yellow/on :yellow/off :yellow/off]]
          (lamp (time/today-at 13 17 01))))))

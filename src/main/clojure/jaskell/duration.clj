(ns jaskell.duration
  (:import (java.time Duration)))

(defn of-days [d]
  (Duration/ofDays d))

(defn of-hours [h]
  (Duration/ofHours h))

(defn of-minutes [m]
  (Duration/ofMinutes m))

(defn of-seconds
  ([s]
   (Duration/ofSeconds s))
  ([s n]
    (Duration/ofSeconds s n)))

(defn of-millis [m]
  (Duration/ofMillis m))

(defn of-nano [n]
  (Duration/ofNanos n))

(defn of [a u]
  (Duration/of a u))

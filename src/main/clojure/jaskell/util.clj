(ns jaskell.util
  (:import (java.util Map Collection List Set)))

(defn keywordize-it
  "Recursively transforms collection in java.util to clojure dictionary/vector/set with all keys
  from strings to keywords."
  [data]
  (let [keywordize (fn [k] (if (string? k) (keyword k) k))]
    (cond
      (instance? Map data) (into {} (map #(vector (keywordize (key %)) (keywordize-it (val %)))) data)
      (instance? Set data) (into #{} (map keywordize-it data))
      (instance? List data) (into [] (map keywordize-it data))
      :else data)))
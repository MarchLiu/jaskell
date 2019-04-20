(ns jaskell.spec
  (:require [clojure.spec.alpha :as s]))

;; code from: https://groups.google.com/d/msg/clojure/fti0eJdPQJ8/CUOETpSJAQAJ
;; code author: Alistair Roche
(defmacro limit-keys
  [& {:keys [req req-un opt opt-un limit] :as args}]
  (if limit
    `(s/merge (s/keys ~@(apply concat (vec args)))
              (s/map-of ~(set (concat req
                                      (map (comp keyword name) req-un)
                                      opt
                                      (map (comp keyword name) opt-un)))
                        any?))
    `(s/keys ~@(apply concat (vec args)))))
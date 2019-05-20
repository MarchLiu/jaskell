(ns jaskell.distance)

(defn levenshtein [x y]
  (letfn [(d [a b] d (cond
                       (= (count a) 0) (count b)
                       (= (count b) 0) (count a)
                       :else #(min (inc (trampoline d a (butlast b)))
                                   (inc (trampoline d (butlast a) b))
                                   (if (= (last a) (last b))
                                     (trampoline d (butlast a)
                                                 (butlast b))
                                     (inc (trampoline
                                            d (butlast a)
                                            (butlast b)))))))]
    (trampoline d x y)))

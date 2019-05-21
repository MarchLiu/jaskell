(ns jaskell.distance)

(defn levenshtein [src tgt]
  (let [src-size (count src)
        tgt-size (count tgt)
        frame (reduce (fn [f i] (conj f [i]))
                      [(vec (for [i (range (inc src-size))] i))]
                      (range 1 (inc tgt-size)))]
    (-> (reduce (fn [result src-idx]
                  (let [src-char (nth src src-idx)]
                    (reduce (fn [dataset tgt-idx]
                              (let [tgt-char (nth tgt tgt-idx)
                                    cost (if (= src-char tgt-char) 0 1)
                                    above (inc (get-in dataset [tgt-idx (inc src-idx)]))
                                    left (inc (get-in dataset [(inc tgt-idx) src-idx]))
                                    diag (+ (get-in dataset [tgt-idx src-idx]) cost)]
                                (assoc-in dataset [(inc tgt-idx) (inc src-idx)] (min above left diag))))
                            result
                            (range tgt-size))))
                frame
                (range src-size))
        (get-in [tgt-size src-size]))))
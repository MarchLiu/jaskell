(ns jaskell.distance-test
  (:require [clojure.test :refer :all]
            [jaskell.distance :refer :all]))

(deftest levenshtein-test
  (are [expect actual] (= expect actual)
                       1 (levenshtein "126.com" "127.com")
                       1 (levenshtein "127.com" "126.com")
                       0 (levenshtein "hotmail.com" "hotmail.com")
                       0 (levenshtein "" "")
                       8 (levenshtein "sina.com" "")
                       9 (levenshtein "gmail.com" "")
                       2 (levenshtein "sina.com" "sina.cn")
                       1 (levenshtein "139.com" ".139.com")
                       3 (levenshtein "qq." "qq.com")
                       1 (levenshtein "gmail.com" "gmail.com.")
                       1 (levenshtein ".gmail.com" "gmail.com")
                       10 (levenshtein "ttttattttctg" "tcaaccctaccat")))

(ns jaskell.handle-test
  (:require [clojure.test :refer :all])
  (:require [jaskell.handle :as h])
  (:import [java.util.function Function Supplier]
           [jaskell Handler Handler2]))

(deftest supplier-test
  (let [anchor (atom 0)
        supplier (h/supplier [_]
                   @anchor)]
    (doseq [i (range 10)]
      (is (= (swap! anchor inc) (.get supplier))))))

(deftest function-test
  (let [anchor (atom 0)
        functor (h/function [_ c]
                  (+ c 1))]
    (doseq [i (range 10)]
      (is (= (swap! anchor inc) (.apply functor i))))))

(deftest runnable-test
  (let [anchor (atom 0)
        runner (h/runnable [_]
                   (swap! anchor inc))]
    (doseq [i (range 1 11)]
      (.run runner)
      (is (= i @anchor)))))

(h/def-generator-0 fun-0 [Supplier get])
(deftest generator-0-test
  (let [anchor (atom 0)
        supplier (fun-0 [_]
                   @anchor)]
    (doseq [i (range 10)]
      (is (= (swap! anchor inc) (.get supplier))))))

(h/def-generator-1 fun-1 [Handler handle])
(deftest generator-1-test
  (let [anchor (atom 0)
        functor (fun-1 [_ c]
                  (+ c 1))]
    (doseq [i (range 10)]
      (is (= (swap! anchor inc) (.handle functor i))))))

(h/def-generator-2 fun-2 [Handler2 handle])
(deftest generator-1-test
  (let [anchor (atom 0)
        functor (fun-2 [_ a b]
                       (+ a b (swap! anchor inc)))]
    (doseq [i (range 3 13)]
      (is (= i (.handle functor 1 1))))))
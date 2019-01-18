(ns jaskell.handle
  (:import (java.util.function Function Supplier)))

(defmacro def-generator-0 [name [clz handler]]
  `(defmacro ~name [[this#] & body#]
     `(reify ~'~clz
        (~'~handler [~this#]
          ~@body#))))

(defmacro def-generator-1 [name [clz handler]]
  `(defmacro ~name [[this# arg#] & body#]
     `(reify ~'~clz
        (~'~handler [~this# ~arg#]
           ~@body#))))

(defmacro def-generator-2 [name [clz handler]]
  `(defmacro ~name [[this# a# b#] & body#]
     `(reify ~'~clz
        (~'~handler [~this# ~a# ~b#]
          ~@body#))))

(defmacro supplier [[this] & body]
  `(reify Supplier
     (get [~this]
       ~@body)))

;(defmacro function [[this arg] & body]
;  `(reify Function
;     (apply [~this ~arg]
;       ~@body)))

(def-generator-1 function [Function apply])

(defmacro runnable [[this] & body]
  `(reify Runnable
     (run [~this]
       ~@body)))


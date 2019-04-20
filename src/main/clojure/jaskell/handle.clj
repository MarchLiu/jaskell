(ns jaskell.handle
  (:import (java.util.function Function Supplier)))

(defmacro def-generator-0 [name [clz handler]]
  `(defmacro ~name [[]  & body#]
     `(reify ~'~clz
        (~'~handler [_]
          ~@body#))))

(defmacro def-generator-1 [name [clz handler]]
  `(defmacro ~name [[arg#] & body#]
     `(reify ~'~clz
        (~'~handler [_ ~arg#]
           ~@body#))))

(defmacro def-generator-2 [name [clz handler]]
  `(defmacro ~name [[a# b#] & body#]
     `(reify ~'~clz
        (~'~handler [_ ~a# ~b#]
          ~@body#))))

(defmacro supplier [_ & body]
  `(reify Supplier
     (get [_]
       ~@body)))

(defmacro function [[arg] & body]
  `(reify Function
     (apply [_ ~arg]
       ~@body)))

;(def-generator-1 function [Function apply])

(defmacro runnable [[_] & body]
  `(reify Runnable
     (run [_]
       ~@body)))


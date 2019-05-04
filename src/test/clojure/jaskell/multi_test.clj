(ns jaskell.multi-test
  (:require [clojure.test :refer :all])
  (:require [jaskell.multi :refer :all]))

(deftest basic-multi
  (let [m (multi (fn [message] (:category message)))]
    (testing "default method just return message"
      (defmethod m :default [message]
        message)
      (is (= (m {}) {})))
    (testing "add a dispatch method"
      (defmethod m :message [message]
        {:content (:message message) :category :reply})
      (is (= (m {}) {}))
      (is (= (m {:category :message}) {:content nil :category :reply}))
      (is (= (m {:category :message :message "a question"})
             {:content "a question" :category :reply}))))
  (testing "define chain"
    (let [m (doto (multi (fn [message] (:category message)))
              (defmethod :message [message]
                {:content (:message message) :category :reply})
              (defmethod :default [message]
                message))]
      (is (= (m {}) {}))
      (is (= (m {:category :message}) {:content nil :category :reply}))
      (is (= (m {:category :message :message "a question"})
             {:content "a question" :category :reply})))))
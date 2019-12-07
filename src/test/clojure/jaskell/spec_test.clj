(ns jaskell.spec-test
  (:require [clojure.test :refer :all])
  (:require [clojure.spec.alpha :as s])
  (:require [jaskell.spec :refer [limit-keys]]))

(def email-regex #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,63}$")
(s/def ::email-type (s/and string? #(re-matches email-regex %)))

(def phone-regex #"^[0-9]{7,11}$")
(s/def ::phone (s/and string? #(re-matches phone-regex %)))

(s/def ::acctid int?)
(s/def ::first-name string?)
(s/def ::last-name string?)
(s/def ::email ::email-type)

(s/def ::person (limit-keys :req [::first-name ::last-name ::email]
                                         :opt [::phone]))

(s/def ::x-person (limit-keys :req [::first-name ::last-name ::email]
                                         :opt [::phone]
                                         :limit true))
(deftest smart-test
  (is (= true (s/valid? ::person
                        {::first-name "Bugs"
                         ::last-name  "Bunny"
                         ::email      "bugs@example.com"})))
  (is (= true (s/valid? ::person
                        {::first-name "Bugs"
                         ::last-name  "Bunny"
                         ::email      "bugs@example.com"
                         ::phone      "1000000"})))
  (is (= false (s/valid? ::x-person
                         {::first-name "Bugs"
                          ::last-name  "Bunny"
                          ::email      "bugs@example.com"
                          ::x-phone    "1000000"})))
  (is (= true (s/valid? ::person
                         {::first-name "Bugs"
                          ::last-name  "Bunny"
                          ::email      "bugs@example.com"
                          ::x-phone    "1000000"}))))

(let [])
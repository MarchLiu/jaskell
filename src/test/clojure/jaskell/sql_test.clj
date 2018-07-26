(ns jaskell.sql-test
  (:require [clojure.test :refer :all]
            [jaskell.sql :refer [select from where p insert into values limit]]
            [clojure.java.jdbc :refer :all])
  (:import (jaskell.sql Statement Query)))

(def db {:classname "org.sqlite.JDBC", :subprotocol "sqlite", :subname ":memory:"})
(deftest basic0
  (testing "basic tests about SQL generate"
    (is (= "select 1") (.script (select 1)))
    (is (= "select * from test where id = ?" (.script (select :* from :test where :id := (p "id")))))))

(deftest basic1
  (testing "basic tests about SQL parameters"
    (is (= "id"
           (-> (select :* from :test where :id := (p "id"))
               (.parameters)
               (first)
               .key)))))

(deftest with-memory-sqlite
  (testing "init database and test parameter"
    (with-open [conn (get-connection db)]
      (-> conn
          (.prepareStatement (create-table-ddl :test
                                               [[:id :integer "primary key" :autoincrement]
                                                [:pid :int] [:content :text]]))
          (.execute))
      (-> ^Statement (insert (into :test [:pid :content]) (values [(p :pid Long) (p :content String)]))
          ((fn [statement]
             (println (.script statement))
             statement))
          (.setParameter :pid 1)
          (.setParameter :content "one line")
          (.execute conn))
      (is (= 1 (-> ^Query
                   (select :id from :test limit 1)
                   (.scalar conn Integer)
                   (.get)))))))

(run-tests)
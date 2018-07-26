(ns jaskell.sql-test
  (:require [clojure.test :refer :all]
            [jaskell.sql :refer [select from where p insert into values limit returning f null] :as sql]
            [clojure.java.jdbc :refer :all])
  (:import (jaskell.sql Statement Query)
           (java.util Optional)))

(defn spy [statement]
  (println (.script statement))
  statement)

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

(deftest insert-memory-sqlite
  (testing "init database and test insert"
    (with-open [conn (get-connection db)]
      (let [^Statement ins (insert (into :test [:pid :content]) (values (p :pid Long) (p :content String)))]
        (-> conn
            (.prepareStatement (create-table-ddl :test
                                                 [[:id :integer "primary key" :autoincrement]
                                                  [:pid :int] [:content :text]]))
            (.execute))
        (-> ins
            (.setParameter :pid 1)
            (.setParameter :content "one line")
            (.execute conn))
        (is (= 1 (-> ^Query
                     (select :id from :test limit 1)
                     (.scalar conn Integer)
                     (.get))))
        (-> ins
            (.setParameter :pid 1)
            (.setParameter :content "another lne")
            (.execute conn))
        (is (= 2 (-> ^Query
                     (select (f :last_insert_rowid))
                     (.scalar conn)
                     (.get))))))))

(deftest update-memory-sqlite
  (testing "init database and test update"
    (with-open [conn (get-connection db)]
      (-> conn
          (.prepareStatement (create-table-ddl :test
                                               [[:id :integer "primary key" :autoincrement]
                                                [:pid :int] [:content :text]]))
          (.execute))
      (-> ^Statement (insert (into :test [:content]) (values (p :content String)))
          (.setParameter :content "one line")
          (.execute conn))
      (-> ^Statement (sql/update :test (sql/set :pid := 1) where :pid sql/is null)
          (.execute conn))
      (is (.equals (Optional/of (int 1))
                   (-> ^Query
                       (select :id from :test where :pid := 1)
                       (.scalar conn Integer)))))))

(run-tests)
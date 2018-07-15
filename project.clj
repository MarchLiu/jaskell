(defproject org.clojars.marsliu/jaskell "0.1.0-SNAPSHOT"
  :name "jaskell"
  :description "Rock your Java!"
  :url "https://github.com/MarchLiu/jaskell"
  :license {:name "THE MIT LICENSE"
            :url "https://opensource.org/licenses/MIT"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :profiles {:dev {:dependencies [[junit/junit "4.11"]
                                  [org.xerial/sqlite-jdbc "3.23.1"]]
                   :java-source-paths ["src/main/java" "src/test/java"]
                   :junit ["src/test/java"]}}
  :plugins [[lein-junit "1.1.8"]]
  :java-source-paths ["src/main/java"]
  :source-paths ["src/main/clojure" "src/main/java"]
  :test-paths ["src/test/clojure" "src/test/java"]
  :javac-options ["-target" "1.10" "-source" "1.10" "-Xlint:unchecked" "-Xlint:varargs"]
  :junit ["src/test/java"])

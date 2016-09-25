(defproject org.clojars.marsliu/jaskell "0.0.1-SNAPSHOT"
  :name "jaskell"
  :description "Rock your Java!"
  :url "https://github.com/MarchLiu/jaskell"
  :license {:name "THE MIT LICENSE"
            :url "https://opensource.org/licenses/MIT"}
  :profiles {:dev {:dependencies [[junit/junit "4.11"]]
                   :java-source-paths ["src/main/java" "src/test/java"]
                   :junit ["src/test/java"]}}
  :plugins [[lein-junit "1.1.8"]]
  :java-source-paths ["src/java"]
  ;; :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:-options"]
  :javac-options ["-target" "1.8" "-source" "1.8" "-Xlint:unchecked" "-Xlint:varargs"]
  :junit ["src/test/java"])

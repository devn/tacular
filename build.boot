(def project 'io.clojure/tacular)
(def version "0.1.0-SNAPSHOT")

(set-env!
  :resource-paths #{"src"}
  :dependencies '[[org.clojure/clojure "RELEASE" :scope "provided"]
                  [org.clojure/clojurescript "RELEASE" :scope "provided"]
                  [com.gfredericks/test.chuck "RELEASE" :scope "provided"]

                  [org.clojure/test.check "RELEASE" :scope "test"]

                  [adzerk/boot-test "RELEASE" :scope "test"]
                  [crisptrutski/boot-cljs-test "RELEASE" :scope "test"]]
  :exclusions '#{org.clojure/clojure})

(require '[adzerk.boot-test :refer [test]])
(require '[crisptrutski.boot-cljs-test :refer [test-cljs]])

(def test-namespaces
  '#{io.clojure.tacular.clojure-test-test})

(task-options!
  pom {:project     project
       :version     version
       :description "tacular; what’s missing from spec"
       :url         "https://github.com/sattvik/tacular"
       :scm         {:url "https://github.com/sattvik/tacular"}
       :license     {"Eclipse Public License"
                     "http://www.eclipse.org/legal/epl-v10.html"}}
  test {:namespaces test-namespaces}
  test-cljs {:namespaces test-namespaces})

(deftask with-tests
  "Adds test settings."
  []
  (set-env! :resource-paths #(conj % "test"))
  identity)

(deftask dev
  "Runs in development mode."
  []
  (comp (with-tests)
        (repl)
        (watch)
        (test)
        (test-cljs)
        ))

(deftask build
  "Build and install the project locally."
  []
  (comp (pom)
        (jar)
        (install)))

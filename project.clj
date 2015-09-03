(defproject gameengine "0.1.0-SNAPSHOT"
  :description "A fairly generic engine for implementing board games."
  :url "http://orbistertius.xyz"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
		 [clojure-lanterna "0.9.4"]]
  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :creds :gpg}}
  :main ^:skip-aot gameengine.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

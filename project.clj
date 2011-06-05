(defproject plaid-penguin-example "1.0.0-SNAPSHOT"
  :description "Example for plaid penguin."
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [log4j "1.2.15" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
		 [thrift/thrift "0.6.1"]
		 [org.clojars.kjw/slf4j "1.5.5"]
		 [org.clojars.kjw/slf4j-simple "1.5.5"]]
  :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"]
		     [lein-javac "1.2.1-SNAPSHOT"]]
  :java-source-path [["gen-java/"]]
  :main plaid-penguin-example.core)

;; mvn install:install-file -Dfile=thrift-0.6.1/lib/java/build/libthrift-0.6.1-snapshot.jar -DgroupId=thrift -DartifactId=thrift -Dversion=0.6.1 -Dpackaging=jar 

;; thrift --gen java simple.thrift
;; lein javac
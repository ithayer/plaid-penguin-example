(ns plaid-penguin-example.core
  (:require [clojure.contrib.logging :as lg])
  (:require [plaid-penguin-example.server :as pp-server])
  (:require [plaid-penguin-example.client :as pp-client])
  (:import [plaid_penguin_example OpService$Iface OpService$Processor OpService$Client Operation OpType Result])
  (:gen-class))

(def *processor* 
     (OpService$Processor. 
      (proxy [OpService$Iface] []
	(do_op [op]
	       (lg/info (str "server got: " op))
	       Result/OK))))

(defn start-example-server [port]
  (let [server (pp-server/create-multi-threaded port *processor*)]
    (.start (Thread. (fn [] 
		       (lg/info "listening...") 
		       (.serve server)
		       (lg/info "done"))))
    server))

(defn -main [& args]
  (let [example-server (start-example-server (Integer. (first args)))
	connection     (pp-client/create-socket "localhost" (Integer. (first args)))
	client         (pp-client/create OpService$Client connection)
	test-op        (doto (Operation.)
			 (.setMessage "test")
			 (.setOp (OpType/SLEEP)))]
    (let [result (.do_op client test-op)]
      (lg/info (str "client got: " result))
      (.stop example-server))))
  

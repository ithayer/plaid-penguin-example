;; This is a simple example of how to setup a thrift interface using
;;   the 'plaid-penguin' library (which is also very simple). This
;;   file initializes a server, which was specified in thrift.proto,
;;   and calls it with a client. Once the client returns, the server is stopped.

(ns plaid-penguin-example.core
  (:require [clojure.contrib.logging :as lg])
  (:require [plaid-penguin.server :as pp-server])
  (:require [plaid-penguin.client :as pp-client])
  ;; Server and handler interfaces for the 'OpService' service, defined in 'simple.thrift'.
  (:import [plaid_penguin_example OpService$Iface OpService$Processor OpService$Client])
  ;; Datatypes, also defined in 'simple.thrift'.
  (:import [plaid_penguin_example Operation OpType Result])
  (:gen-class))

;; Subclass the handler.
(def *processor* 
     (OpService$Processor. 
      (proxy [OpService$Iface] []
	(do_op [op] ;; This is the only interface function for the 'OpService' service.
	       (lg/info (str "server got: " op))
	       Result/OK)))) ;; It just returns OK.

;; Start the server.
(defn start-example-server [port]
  "Given a port, starts a multi-threaded server, and returns it."
  (let [server (pp-server/create-multi-threaded port *processor*)]
    (.start (Thread. (fn [] 
		       (lg/info "listening...") 
		       (.serve server)
		       (lg/info "done"))))
    server))

;; Initialize both server and client, and make an RPC call.
(defn -main [& args]
  (let [example-server (start-example-server (Integer. (first args)))
	connection     (pp-client/create-socket "localhost" (Integer. (first args)))
	client         (pp-client/create OpService$Client connection)
	test-op        (doto (Operation.)
			 (.setMessage "test")
			 (.setOp (OpType/SLEEP)))]
    (let [result (.do_op client test-op)] ;; Call 'do_op'.
      (lg/info (str "client got: " result))
      (.stop example-server))))

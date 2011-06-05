(ns plaid-penguin-example.core
  (:require [clojure.contrib.logging :as lg])
  (:import [plaid_penguin_example OpService$Iface OpService$Processor OpService$Client Operation OpType Result])
  (:import [org.apache.thrift.server TServer TSimpleServer TServer$Args TThreadPoolServer])
  (:import [org.apache.thrift.transport TServerSocket TServerTransport TTransport TSocket])
  (:import [org.apache.thrift.protocol TBinaryProtocol])
  (:use [clojure.contrib.import-static])
  (:gen-class))

(def *handler* (proxy [OpService$Iface] [] 
		 (do_op [op] 
			(lg/info "got it") 
			Result/OK)))

(def *processor* (OpService$Processor. *handler*))

(defn create-simple-server [port]
  (let [transport (TServerSocket. port)]
    (TSimpleServer. (.processor (TServer$Args. transport) *processor*))))

(defn create-threaded-server [port]
  (let [transport (TServerSocket. port)]
    (TThreadPoolServer. (.processor (TServer$Args. transport) *processor*))))

(defn create-socket [hostname port]
  (let [transport (TSocket. hostname port)]
    (.open transport)
    transport))

(defn create-client [transport]
  (let [protocol (TBinaryProtocol. transport)]
    (OpService$Client. protocol)))

(defn repl-start-server []
  (let [server (create-simple-server 8981)]
    (.start (Thread. (fn [] (lg/info "listening...") (.serve server) (lg/info "done"))))
    server))

(defn create-test-op []
  (doto (Operation.)
    (.setMessage "test")
    (.setOp (OpType/SLEEP))))

(defn -main [& args]
  (print (str "Called with " (apply str (or args [])))))

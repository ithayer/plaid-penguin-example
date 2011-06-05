(ns plaid-penguin-example.server
  (:require [clojure.contrib.logging :as lg])
  (:import [org.apache.thrift.server TServer TSimpleServer TServer$Args TThreadPoolServer])
  (:import [org.apache.thrift.transport TServerSocket TServerTransport TTransport TSocket])
  (:import [org.apache.thrift.protocol TBinaryProtocol])

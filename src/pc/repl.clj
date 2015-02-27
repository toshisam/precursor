(ns pc.repl
  "Utility functions to make repl access more convenient.
   Also serves as a guide for how nses should be aliased"
  (:require [cheshire.core :as json]
            [cemerick.url :as url]
            [clj-http.client :as http]
            [clj-time.core :as time]
            [clojure.core.async :as async]
            [clojure.java.javadoc :refer (javadoc)]
            [clojure.repl :refer :all]
            [datomic.api :as d]
            [pc.datomic :as pcd]
            [pc.datomic.web-peer :as web-peer]
            [pc.email :as email]
            [pc.http.sente :as sente]
            [pc.models.chat :as chat-model]
            [pc.models.cust :as cust-model]
            [pc.models.doc :as doc-model]
            [pc.models.flag :as flag-model]
            [pc.models.layer :as layer-model]
            [pc.models.permission :as permission-model]
            [slingshot.slingshot :refer (try+ throw+)]))

(defmacro pomegranate-load [artifact]
  `(do
     (require 'cemerick.pomegranate)
     (cemerick.pomegranate/add-dependencies
      :coordinates '[~artifact]
      :repositories (merge cemerick.pomegranate.aether/maven-central {"clojars" "http://clojars.org/repo"}))))

(defmacro browser-repl []
  `(do
     (require 'weasel.repl.websocket)
     (cemerick.piggieback/cljs-repl :repl-env (weasel.repl.websocket/repl-env :ip "0.0.0.0" :port 9001))))

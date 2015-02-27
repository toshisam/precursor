(ns pc.http.urls
  (:require [cemerick.url :as url]
            [pc.profile :as profile]))

(defn make-url [path & {:keys [query]}]
  (str (url/map->URL (merge {:host (profile/hostname)
                             :protocol (if (profile/force-ssl?)
                                         "https"
                                         "http")
                             :port (if (profile/force-ssl?)
                                     (profile/https-port)
                                     (profile/http-port))
                             :path path}
                            (when query
                              {:query query})))))

(defn root []
  (make-url "/"))

(defn doc [doc-id & {:keys [query]}]
  (make-url (str "/document/" doc-id) :query query))

(defn doc-svg [doc-id & {:keys [query]}]
  (make-url (str "/document/" doc-id ".svg") :query query))

(defn doc-png [doc-id & {:keys [query]}]
  (make-url (str "/document/" doc-id ".png") :query query))

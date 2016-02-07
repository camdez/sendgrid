(ns sendgrid.core
  (:require [sendgrid.request :as request]))

(defn profile [config]
  (-> (request/request config :get "profile.get" {})
      first))

(defn send-email [config params]
  (let [attachments (:attachments params)
        params      (dissoc params :attachments)]
    (request/request config :post "mail.send" params attachments)))

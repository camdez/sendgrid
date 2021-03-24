(ns sendgrid.core
  (:require [sendgrid.request :as request]))

(defn send-email [config params]
  (let [attachments (:attachments params)
        params      (dissoc params :attachments)]
    (request/request config :post "mail.send" params attachments)))

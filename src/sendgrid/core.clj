(ns sendgrid.core
  (:require [sendgrid.request :as request]))

(defn profile [config]
  (-> (request/request config :get "profile.get" {})
      first))

(defn send-email [config params]
  (request/request config :get "mail.send" params))

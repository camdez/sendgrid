(ns sendgrid.request
  (:require [cheshire.core :as json]
            [clj-http.client :as http]
            [sendgrid.utils :as utils]))

(def base-url
  "https://api.sendgrid.com/api/")

(defn build-url [endpoint]
  (format "%s%s.json" base-url endpoint))

(defn merged-params [config params]
  (->> (merge config params)
       (utils/map-keys utils/underscore-kw)))

(defn handle-api-errors [resp]
  (if-let [error (get-in resp [:body :error])]
    (throw (ex-info (:message error) (dissoc error :message)))
    resp))

(defn request [config method endpoint params]
  (when (= method :get)
    (-> (http/get (build-url endpoint)
                  {:query-params (merged-params config params)})
        (update :body #(json/parse-string % utils/dasherize-kw))
        (handle-api-errors)
        :body)))

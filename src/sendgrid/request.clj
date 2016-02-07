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

(defn multipart-params [params attachments]
  (concat (map (fn [[k v]] {:name (name k), :content v}) params)
          (map (fn [a] (update a :name #(format "files[%s]" %))) attachments)))

(defn build-request [method endpoint params attachments]
  (merge
   {:method method
    :url    (build-url endpoint)}
   (cond
     (= method :get)      {:query-params params}
     (empty? attachments) {:form-params  params}
     :else                {:multipart    (multipart-params params attachments)})))

(defn handle-api-errors [resp]
  (if-let [error (get-in resp [:body :error])]
    (throw (ex-info (:message error) (dissoc error :message)))
    resp))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(defn request
  ([config method endpoint params]
   (request config method endpoint params nil))
  ([config method endpoint params attachments]
   (-> (build-request method endpoint (merged-params config params) attachments)
       (http/request)
       (update :body #(json/parse-string % utils/dasherize-kw))
       (handle-api-errors)
       :body)))

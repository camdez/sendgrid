(ns sendgrid.request
  (:require [cheshire.core :as json]
            [clj-http.client :as http]
            [sendgrid.utils :as utils]))

(def base-url
  "https://api.sendgrid.com/api/")

(defn- build-url [endpoint]
  (str base-url endpoint ".json"))

(defn- multipart-params [params]
  (mapcat (fn [[k vs]]
            (let [vs    (cond-> vs (not (coll? vs)) vector)
                  mult? (some? (second vs))]
              (for [v vs]
                (if (map? v)
                  (update v :name #(str (name k) "[" % "]"))
                  {:name    (str (name k) (when mult? "[]"))
                   :content v}))))
          params))

(defn- build-request [method endpoint config params attachments]
  (let [mp (-> (merge config params)
               (update-keys utils/underscore-kw)
               (dissoc :api_user :api_key))]
    (merge
     {:method method
      :url    (build-url endpoint)
      :oauth-token (:api-key config)}
     (cond
       (= method :get)      {:query-params mp}
       (empty? attachments) {:form-params  mp}
       :else                {:multipart    (-> mp
                                               (assoc :files attachments)
                                               multipart-params)}))))

(defn- handle-api-errors [resp]
  (if-let [error (get-in resp [:body :error])]
    (throw (ex-info (:message error) (dissoc error :message)))
    resp))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Public

(defn request
  ([config method endpoint params]
   (request config method endpoint params nil))
  ([config method endpoint params attachments]
   (-> (build-request method endpoint config params attachments)
       (http/request)
       (update :body #(json/parse-string % utils/dasherize-kw))
       (handle-api-errors)
       :body)))

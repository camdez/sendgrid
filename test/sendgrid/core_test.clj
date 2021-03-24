(ns sendgrid.core-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [environ.core :as environ]
            [sendgrid.core :refer :all]))

(def config
  {:api-key (environ/env :sendgrid-api-key)})

(def from (environ/env :from-email))
(def to   (environ/env :to-email))

(def basic-params
  {:to to, :from from, :subject "Clojure SendGrid Test"})

(def text-params
  (assoc basic-params :text "Hello World"))

(def html-params
  (assoc basic-params :html "<h1>Hello World</h1>"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest test-send-email
  (testing "Sending a text-only email"
    (is (= (-> (send-email config text-params) :message)
           "success")))
  (testing "Sending an html-only email"
    (is (= (-> (send-email config html-params) :message)
           "success")))
  (testing "Sending an email with an attachment"
    (is (= (->> (assoc text-params
                       :attachments
                       [{:name "plain.txt", :content "Simple."}
                        {:name "yay.jpg",   :content (-> "yay.jpg" io/resource io/file)}])
                (send-email config)
                :message)
           "success"))))

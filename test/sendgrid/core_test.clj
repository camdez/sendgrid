(ns sendgrid.core-test
  (:require [clojure.test :refer :all]
            [environ.core :as environ]
            [sendgrid.core :refer :all]))

(def config
  {:api-user (environ/env :sendgrid-api-user)
   :api-key  (environ/env :sendgrid-api-key)})

(def from (environ/env :from-email))
(def to   (environ/env :to-email))

(def basic-params
  {:to to, :from from, :subject "Clojure SendGrid Test"})

(def text-params
  (assoc basic-params :text "Hello World"))

(def html-params
  (assoc basic-params :html "<h1>Hello World</h1>"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest test-profile
  (testing "Fetching user profile"
    (is (= (-> (profile config) :username)
           (:api-user config)))))

(deftest test-send
  (testing "Sending a text-only email"
    (is (= (-> (send-email config text-params) :message)
           "success")))
  (testing "Sending an html-only email"
    (is (= (-> (send-email config html-params) :message)
           "success"))))

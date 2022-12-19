(ns sendgrid.request-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [sendgrid.request :as sut]))

(deftest build-request
  (testing "uses basic form encoding if no attachments"
    (let [params {:to ["test1@test.com"
                       "test2@test.com"]}
          req    (#'sut/build-request :post "mail.send"
                                      {:api-key "bar"}
                                      params
                                      [])]
      (is (= :post (:method req)))
      (is (= "https://api.sendgrid.com/api/mail.send.json" (:url req)))
      (is (= "bar" (:oauth-token req)))
      (is (= params (:form-params req)))))
  (testing "uses multipart form encoding if attachments"
    (let [req (#'sut/build-request :post "mail.send"
                                   {:api-key "bar"}
                                   {:bcc  ["bcc@test.com"]
                                    :from "me@test.com"
                                    :to   ["test1@test.com"
                                           "test2@test.com"]}
                                   [{:name    "file1.txt"
                                     :content "Contents 1"}
                                    {:name    "file2.txt"
                                     :content "Contents 2"}])]
      (is (= :post (:method req)))
      (is (= "https://api.sendgrid.com/api/mail.send.json" (:url req)))
      (is (= "bar" (:oauth-token req)))
      (is (= [["bcc" "bcc@test.com"]
              ["files[file1.txt]" "Contents 1"]
              ["files[file2.txt]" "Contents 2"]
              ["from" "me@test.com"]
              ["to[]" "test1@test.com"]
              ["to[]" "test2@test.com"]]
             (->> req
                  :multipart
                  (map (juxt :name :content))
                  (sort-by first)))))))

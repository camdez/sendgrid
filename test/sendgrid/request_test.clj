(ns sendgrid.request-test
  (:require [sendgrid.request :refer :all]
            [clojure.test :refer :all]))

(deftest test-build-url
  (testing "Basic URL construction"
    (is (= (build-url "profile.get")
           "https://api.sendgrid.com/api/profile.get.json"))))

(deftest test-merged-params
  (testing "Merging of config and request params"
    (is (= (merged-params {:api-user "foo", :api-key "bar"} {:speed "fast"})
           {:api_user "foo", :api_key "bar", :speed "fast"}))))

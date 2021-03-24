(defproject camdez/sendgrid "0.1.1-SNAPSHOT"
  :description "Clojure library for sending emails with SendGrid"
  :url "https://github.com/camdez/sendgrid"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :dependencies [[cheshire "5.10.0"]
                 [clj-http "3.12.1"]
                 [environ "1.2.0"]
                 [org.clojure/clojure "1.10.3"]]
  :plugins [[lein-environ "1.0.2"]])

(defproject camdez/sendgrid "0.1.0"
  :description "Clojure library for sending emails with SendGrid"
  :url "https://github.com/camdez/sendgrid"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :dependencies [[cheshire "5.5.0"]
                 [clj-http "2.0.1"]
                 [environ "1.0.2"]
                 [org.clojure/clojure "1.7.0"]]
  :plugins [[lein-environ "1.0.2"]])

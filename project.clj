(defproject camdez/sendgrid "0.2.1-SNAPSHOT"
  :description "Clojure library for sending emails with SendGrid"
  :url "https://github.com/camdez/sendgrid"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :dependencies [[cheshire "5.11.0"]
                 [clj-http "3.12.3"]
                 [org.clojure/clojure "1.11.1"]]
  :profiles {:dev {:dependencies [[environ "1.2.0"]]}}
  :plugins [[lein-environ "1.0.2"]])

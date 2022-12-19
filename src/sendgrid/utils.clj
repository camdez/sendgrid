(ns sendgrid.utils
  (:require [clojure.string :as str]))

(def dasherize-kw
  (comp keyword #(str/replace % "_" "-") name))

(def underscore-kw
  (comp keyword #(str/replace % "-" "_") name))

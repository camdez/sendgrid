(ns sendgrid.utils)

(def dasherize-kw
  (comp keyword #(clojure.string/replace % "_" "-") name))

(def underscore-kw
  (comp keyword #(clojure.string/replace % "-" "_") name))

(defn map-keys
  "Apply function F to the keys of map M, returning a new map."
  [f m]
  (reduce-kv #(assoc %1 (f %2) %3) {} m))

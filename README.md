# sendgrid

[![Clojars Project][clojars-badge]][clojars-sendgrid]

A Clojure library for sending emails with [SendGrid][].

## Usage

```clj
(require '[sendgrid.core :as sendgrid])

(def sg-config
  {:api-user "camdez"
   :api-key  "d34db33f"})

;; Fetch your profile
(sendgrid/profile sg-config)
;; => {:username       "camdez"
;;     :first-name     "Cameron"
;;     :last-name      "Desautels"
;;     :website        "https://camdez.com"
;;     :email          "[REDACTED]"
;;     :address        "[REDACTED]"
;;     :address2       "[REDACTED]"
;;     :city           "[REDACTED]"
;;     :state          "[REDACTED]"
;;     :zip            "[REDACTED]"
;;     :country        "[REDACTED]"
;;     :website-access "[REDACTED]"
;;     :active         "[REDACTED]"
;;     :phone          "[REDACTED]"}

;; Send a plain-text email
(sendgrid/send-email sg-config
                     {:to      "email@example.com"
                      :from    "email2@example.com"
                      :subject "SendGrid Test"
                      :text    "Email body here."})
;; => {:message "success"}

;; Send an HTML email
(sendgrid/send-email sg-config
                     {:to      "email@example.com"
                      :from    "email2@example.com"
                      :subject "SendGrid Test"
                      :html    "<h1>Email body here.</h1>"})
;; => {:message "success"}
```

For details on individual request params, please see the
[SendGrid API documentation][sendgrid-api-docs].

Emails can also include attachments via the `:attachments` param.
Attachments must be a sequence of maps where each map contains a
`:name` and `:content` and optionally a `:mime-type` (`String`).
`:content` can be a `File`, `InputStream`, `ByteArray`, or `String`.

```clj
(let [attachments
      [{:name "plain.txt", :mime-type "text/plain", :content "Simple."}
       {:name "yay.jpg",   :content (-> "yay.jpg" io/resource io/file)}]]
  (sendgrid/send-email sg-config
                       {:to          "email@example.com"
                        :from        "email2@example.com"
                        :subject     "SendGrid Test"
                        :html        "<h1>Email body here.</h1>"
                        :attachments attachments}))
;; => {:message "success"}
```

## Testing

To run the tests you'll need the following environment variables set:

- `SENDGRID_API_USER`
- `SENDGRID_API_KEY`
- `FROM_EMAIL`
- `TO_EMAIL`

Alternately you can set these values in a `profiles.clj` file (see
[Environ][] for more information).

Please note that the tests will send real emails to the `TO_EMAIL`
you've set, and will count against your SendGrid email quota.

## Motivation

Features differentiating this library from (some of) the alternatives:

- Idiomatic Clojure.
- No global state.
- Convenient handling of files (whether generated or served from
  the file system).
- No errors on long message contents.
- No assumptions about error handling (error handling is caller's
  responsibility).

## License

Copyright © 2016 Cameron Desautels

Distributed under the MIT License.

[clojars-badge]: http://clojars.org/camdez/sendgrid/latest-version.svg
[clojars-sendgrid]: http://clojars.org/camdez/sendgrid
[sendgrid]: https://sendgrid.com
[environ]: https://github.com/weavejester/environ
[sendgrid-api-docs]: https://sendgrid.com/docs/API_Reference/Web_API/index.html

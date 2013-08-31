(ns matcher.web
  (:require
    [ring.adapter.jetty :as jetty]
    [compojure.route]
    [compojure.core]
    [compojure.handler :as handler]
    [ring.middleware.file]
    [ring.middleware.reload]
    [ring.middleware.stacktrace]
    [hiccup.core]
    [taoensso.timbre :as log]))

(def web-port 8080)

(defn layout [title & body]
  (hiccup.core/html
    [:head [:title title]]
    [:body [:h1.header title] body]))

(defn main-page []
  (layout "Matcher Monitor"
    [:p "Main screen - in development"]
    [:h3 "Matching Breaks (Exceptions)"]))

(compojure.core/defroutes web-routes
     (compojure.core/GET "/main" [] (main-page))
;     (GET "/full-name/:first/:second" [first second] (full-name first second)) ; GET request with variable URL
;     (GET "/full-name" [first second] (full-name first second)) ; GET request with request params
;     (POST "/post-name" [first-name second-name] (post-name first-name second-name)) ; POST request with form data
     (compojure.route/not-found "<h1>Page not found</h1>"))

(def web-config
  (-> #'web-routes
    (handler/api)  ; to access the form data in the parameter style way in the route definitions
    (ring.middleware.file/wrap-file "web")
    (ring.middleware.reload/wrap-reload '(matcher.web))
    (ring.middleware.stacktrace/wrap-stacktrace)))

(defn start-web-server []
  (do
    (defonce web-server (jetty/run-jetty #'web-config {:port web-port :join? false}))
    (log/info (str "web-server started at port " web-port))))

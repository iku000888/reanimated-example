(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/clojurescript "1.9.946"]
                 [reagent "0.8.0-alpha1"]
                 [reanimated "0.5.3"]
                 [figwheel-sidecar "0.5.14"]
                 [hiccup "2.0.0-alpha1"]
                 [cljsjs/react-transition-group "2.2.0-0"]
                 [com.cemerick/piggieback "0.2.2-SNAPSHOT"]])

(require '[clojure.string :as s])
(require '[hiccup.page :as page])
(require '[ring.middleware.resource :as r])
(defn handler [req]
  ((r/wrap-resource
    (constantly
     {:status 200
      :body (page/html5
             [:body
              [:div {:id "app"}]
              (page/include-js "/public/js/reanimated.js")])})

    ".")
   req))

(require '[figwheel-sidecar.repl-api :as f])
(defn fig []
  (f/start-figwheel!))

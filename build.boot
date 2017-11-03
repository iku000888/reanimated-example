(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/clojurescript "1.9.946"]
                 [cljsjs/react-with-addons "15.6.1-0"]
                 [reagent "0.7.0" :exclusions [cljsjs/react]]
                 [reanimated "0.5.3"]
                 [figwheel-sidecar "0.5.14"]
                 [hiccup "2.0.0-alpha1"]
                 [com.cemerick/piggieback "0.2.2-SNAPSHOT"]])

(require '[clojure.string :as s])
(require '[hiccup.page :as page])
(require '[ring.middleware.resource :as r])
(defn page []
  (page/html5
   [:body
    [:div {:id "app"}]
    (page/include-js "/public/js/reanimated.js")]))

(defn handler [req]
  ((r/wrap-resource
    (constantly
     {:status 200
      :body (page)})

    ".")
   req))

(require '[figwheel-sidecar.repl-api :as f])
(defn fig []
  (f/start-figwheel!))

(require '[cljs.build.api :as b])
(deftask build []
  (spit "index.html" (page))
  (b/build "src"
           {:main 'reanimated.demo
            :optimizations :advanced
            :output-to "public/js/reanimated.js"
            :verbose true}))

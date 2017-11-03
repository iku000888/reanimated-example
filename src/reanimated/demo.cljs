(ns reanimated.demo
  (:require [reagent.core :as r]
            [reanimated.core :as rc]))

(defn item [s stuff]
  (let [size (r/atom 40)
        spr (rc/spring size)]
    (fn []
      [:div {:on-click #(swap! stuff
                               (fn [stuff]
                                 (assoc stuff s false)))
             :style {:font-size (str @spr "px")
                     :margin "10px"
                     :cursor "pointer"}}
       s
       [rc/timeline
        #(reset! size 100)
        200
        #(reset! size 40)]
       (when-not (get @stuff s)
         [rc/timeline
          #(reset! size 100)
          400
          #(reset! size 20)
          200
          #(swap! stuff dissoc s)
          ])])))


(defn main []
  (let [size (r/atom 40)
        tl (r/atom false)
        spr (rc/spring size
                       {:from 9
                        :velocity .8
                        :mass 2
                        :stiffness 0.13
                        :damping .17})
        stuff (r/atom {"foo" true
                       "bar" true
                       "baz" true})]
    (fn []
      [:div
       [:span {:on-click #(swap! tl not)
               :style {:font-size (str @spr "px")}}
        "click me!"
        (when @tl
          [rc/timeline
           #(reset! size 100)
           200
           #(reset! size 40)
           #(reset! tl false)])]
       [:hr]
       [:div
        [:input {:type "button" :value  "Add stuff"
                 :on-click #(swap! stuff
                                   (fn [stuff]
                                     (assoc stuff (str (gensym "stuff")) true)))}]
        [:input {:type "button" :value  "Remove stuff"
                 :on-click #(swap! stuff
                                   (fn [stuff]
                                     (assoc stuff (ffirst stuff) false)))}]
        [:div {:style {:display "flex"
                       :flex-wrap "wrap"}}
         (->> @stuff
              sort
              (map (fn [[s]] ^{:key s} [item s stuff]))
              doall)]]])))

(r/render [main]
          (js/document.getElementById "app"))

(ns reanimated.demo
  (:require
   [reagent.core :as r]
   [reanimated.core :as rc]))


(defn main []
  (let [size (r/atom 40)
        tl (r/atom false)
        spr (rc/spring size
                       {:from 9
                        :velocity .8
                        :mass 2
                        :stiffness 0.13
                        :damping .17})]
    (fn []
      [:div
       {:on-click #(swap! tl not)
        :style {:font-size (str @spr "px")}}
       "click me!"
       (when @tl
         [rc/timeline
          #(reset! size 100)
          200
          #(reset! size 40)
          #(reset! tl false)])])))

(r/render [main]
          (js/document.getElementById "app"))

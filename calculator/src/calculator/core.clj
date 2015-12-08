(ns calculator.core (:gen-class))

(use 'seesaw.core)
(use 'calculator.parser)

(def mainframe (frame :title "Clojure Calculator"
                      :size [300 :by 250]
                      :resizable? false
                      :on-close :exit))

(defn display [c]
  (config! mainframe :content c)
  c)

(def t (text :editable? true))

(defn evaluate []
  (config! t :text (parse (text t))))

(defn keyboard-listener [e]
    (cond (= \newline (.getKeyChar e))
          (evaluate)))

(defn make-btns [labels]
  (map (fn[label]
         (let [btn (button :text label)]
           (listen btn
                   :mouse-clicked (fn [e] (config! t :text (str (text t) label))))
           btn))
       labels))

(def cl (button :text "Clear"))
(listen cl
        :mouse-clicked (fn [e] (config! t :text "")))

(def enter (button :text "Enter"))
(listen enter
        :mouse-clicked (fn[e] (evaluate)))

(listen t
        :key-typed keyboard-listener)

(def bp (border-panel
          :center
          (flow-panel
            :items 
               (make-btns ["1" "2" "3"
                           "4" "5" "6"
                           "7" "8" "9"
                           "0" "(" ")"
                           "."]))
          :north t
          :east (grid-panel
								  :items [cl enter])
          :west (grid-panel
								  :items (make-btns ["/" "*" "-" "+" "^"])
                  ;:columns 2
                  )
          :south (grid-panel
                   :items (make-btns ["sin" "cos" "tan"])
                   :columns 3)
          :hgap 10
          :vgap 10))

(defn -main [& args]
  (config! mainframe :content bp)
  (-> mainframe show!))

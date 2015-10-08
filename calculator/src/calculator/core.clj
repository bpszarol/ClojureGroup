(ns calculator.core (:gen-class))

(use 'seesaw.core)

(def mainframe (frame :title "Clojure Calculator"
                      :size [230 :by 250]
                      :resizable? false
                      :on-close :exit))

(defn display [c]
  (config! mainframe :content c)
  c)

(def t (text :editable? false))

(def one (button :text "1"))
(listen one 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "1"))))

(def two (button :text "2"))
(listen two 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "2"))))

(def three (button :text "3"))
(listen three 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "3"))))

(def four (button :text "4"))
(listen four 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "4"))))

(def five (button :text "5"))
(listen five 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "5"))))

(def six (button :text "6"))
(listen six 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "6"))))

(def sev (button :text "7"))
(listen sev 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "7"))))

(def eight (button :text "8"))
(listen eight 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "8"))))

(def nine (button :text "9"))
(listen nine 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "9"))))

(def zero (button :text "0"))
(listen zero 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "0"))))

(def dot (button :text "."))
(listen dot 
        :mouse-clicked (fn [e] (config! t :text (str (text t) "."))))

(def cl (button :text "Clear"))
(listen cl
        :mouse-clicked (fn [e] (config! t :text "")))

(def bp (border-panel
          :center
          (flow-panel
            :items 
               [one
                two
                three
                four
                five
                six
                sev
                eight
                nine 
                zero
                dot])
          :north t
          :south "SOUTH"
          :east cl
          :hgap 10
          :vgap 10))

(defn -main [& args]
  (config! mainframe :content bp)
  (-> mainframe show!)
  )
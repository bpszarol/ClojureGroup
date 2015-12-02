(ns calculator.parser
  (:require [instaparse.core :as insta]))  ;; "instaparse" is a parser that takes a BNF grammar


;; Adapted from https://github.com/Engelberg/instaparse
(def arithmetic
  (insta/parser
    "expr = add-sub
     <add-sub> = mul-div | add | sub
     add = add-sub <'+'> mul-div
     sub = add-sub <'-'> mul-div
     <mul-div> = term | mul | div
     mul = mul-div <'*'> term
     div = mul-div <'/'> term
     <term> = num | <'('> add-sub <')'>
     num = negative? (decimal-num | integer-num)
     <negative> = '-'
     <decimal-num> = digit* '.' digit+
     <digit> = #'[0-9]'
     <integer-num> = digit+"
    :auto-whitespace :standard))

(def precision 32)

(defn parse-num [& tokens]
  (bigdec (apply str tokens)))

(defn addition [& nums]
  (with-precision precision
    (apply + nums)))

(defn subtraction [& nums]
  (with-precision precision
    (apply - nums)))

(defn multiplication [& nums]
  (with-precision precision
    (apply * nums)))

(defn division [& nums]
  (with-precision precision
    (apply / nums)))

;; Defines what Clojure functions to replace parsed tokens with
(def transform-options
  {:num parse-num,
   :add addition,
   :sub subtraction,
   :mul multiplication,
   :div division,
   :expr identity})

;; Parses the input string and transforms it into a Clojure expression
;; Malformed input is handled by returning an empty string
(defn parse [input]
  (let [result (arithmetic input)]
    (if (insta/failure? result)
      (do (println (str "ERR Parse\n" result))
          "ERR")
      (try
        (str (insta/transform transform-options result))
        (catch Exception e
          (do (println (str "ERR Transform\n" (.getMessage e)))
              "ERR"))))))

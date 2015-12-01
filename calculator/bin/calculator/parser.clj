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
     <term> = number | <'('> add-sub <')'>
     number = #'[0-9]+'"
    :auto-whitespace :standard))

;; Defines what Clojure functions to replace parsed tokens with
(def transform-options
  {:number read-string,
   :add +,
   :sub -,
   :mul *,
   :div /,
   :expr identity})

;; Parses the input string and transforms it into a Clojure expression
;; Malformed input is handled by returning an empty string
(defn parse [input]
  (let [result (arithmetic input)]
    (if (insta/failure? result)
      ""
      (insta/transform transform-options result))))

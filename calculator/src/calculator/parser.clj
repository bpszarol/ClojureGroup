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
     number = #'[0-9]+'"))

(def transform-options
  {:number read-string,
   :add +,
   :sub -,
   :mul *,
   :div /,
   :expr identity})

(defn parse [input]
   (->> (arithmetic input)
        (insta/transform transform-options)))
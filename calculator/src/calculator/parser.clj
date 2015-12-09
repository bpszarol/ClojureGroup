(ns calculator.parser
  (:require [instaparse.core :as insta]))  ;; "instaparse" is a parser that takes a BNF grammar
(use 'clojure.math.numeric-tower)


;; Adapted from https://github.com/Engelberg/instaparse
(def arithmetic
  (insta/parser
    "expr = add-sub
     <add-sub> = mul-div | add | sub | exp | bannannas
     add = add-sub <'+'> mul-div
     sub = add-sub <'-'> mul-div
     <mul-div> = (term | mul | div) | exp | bannannas
     mul = mul-div <'*'> term
     div = mul-div <'/'> term
     exp = term <'^'> (term | exp)
     <term> = num | <'('> add-sub <')'>
     num = negative? (decimal-num | integer-num) exponent?
     <exponent> = 'E' sign integer-num
     <bannannas> = sine | cosine | tangent
     sine = <'sin('> expr <')'>
     cosine = <'cos('> expr <')'>
     tangent = <'tan('> expr <')'>
     <sign> = '-' | '+'
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

(defn exponent [num1 num2]
  (with-precision precision
    (bigdec (Math/pow num1 num2))))

(defn sine [num]
  (with-precision precision
    (bigdec (Math/sin num))))

(defn cosine [num]
  (with-precision precision
    (bigdec (Math/cos num))))

(defn tangent [num]
  (with-precision precision
    (bigdec (Math/tan num))))

;; Defines what Clojure functions to replace parsed tokens with
(def transform-options
  {:num parse-num,
   :add addition,
   :sub subtraction,
   :mul multiplication,
   :div division,
   :expr identity,
   :exp exponent,
   :sine sine,
   :cosine cosine,
   :tangent tangent })

;; Parses the input string and transforms it into a Clojure expression
;; Malformed input is handled by returning an empty string
(defn parse [input]
  (let [result (arithmetic input)]
    (if (insta/failure? result)
      (do (println (insta/get-failure result))
          "ERR")
      (try
        (str (insta/transform transform-options result))
        (catch Exception e
<<<<<<< HEAD
          (if (instance? NumberFormatException e)
            (do (println (str "Overflow!\n")) "Overflow!") 
            (do (println (str "ERR Transform\n" (.getMessage e)))
              (.printStackTrace e)
                (.getMessage e))))))))
=======
          (do (println (str "ERR Transform\n" (.getMessage e)))
              (str "ERR: " (.getMessage e))))))))
>>>>>>> 522ca1736c7806355338a2c509f53499130bd54d

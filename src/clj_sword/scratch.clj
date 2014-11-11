(ns clj-sword.scratch
  (:use [clj-sword.core])
  (:require 
   [clojure.zip :as zip]
   [clojure.data.zip.xml :as zipxml]))

; Open the KJV bible and work in John Chapter 1
(def kjv-bible (open-bible "resources/kjv.xml"))
(def kjv-books (map-books kjv-bible))
(def kjv-john (:John kjv-books))
(def john-chapters (map-chapters kjv-john))
(def john1 (:John.1 john-chapters))

(->> kjv-bible
     :content
     first
     :attrs
     :osisIDWork
     )
;(xml-seq john1)

; Retrieve text using zippers
;(nth john1 3)
;(->> john1
;     (filter #(= (:tag %) :w))
;     (map :content))



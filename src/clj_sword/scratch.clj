(ns clj-sword.scratch
  (:use [clj-sword.core])
  (:require 
   [clojure.zip :as zip]
   [clojure.data.zip.xml :as zipxml]
   [clojure.data.xml :as xml]))

; Open the KJV bible and work in John Chapter 1
(def kjv-bible (open-bible "resources/kjv-tiny.xml"))
;(def kjv-books (map-books kjv-bible))
;(def kjv-genesis (:Genesis kjv-books))
;(def kjv-john (:John kjv-books))
;(def john-chapters (map-chapters kjv-john))
;(def john1 (:John.1 john-chapters))

; Use a zipper to retrieve all the words in chapters
(zipxml/xml->
		    kjv-bible
		    :osisText
		    :div
		    :chapter
		    :w
		    zipxml/text)

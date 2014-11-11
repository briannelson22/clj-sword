(ns clj-sword.core
  (:use [clojure.java.io]
        [clojure.pprint])
  (:require [clojure.zip :as zip]
            [clojure.data.zip.xml :as zipxml]
            [clojure.data.xml :as xml]
            ))

;******************************************
; goal: to make a complete search api to make
; bible search research a completely repeatable,
; automatable process.
;******************************************
; todo code reorganization
; todo check into github
;******************************************
; basic reading functionality
;******************************************
; done list bibles
; done load bible
; done list books
; done load books into map
; todo switch to clojurescript friendly parser
; todo list verses
; todo internal data structure
;        every word stands alone 
;        books are sequences of words 
;        all tags/anchors/notes/whatever attach to the word by book & offset

(comment ({
           :book "john"
           :text '("in" "the" "beginning" "...")}))

(comment ({
           :tagname "son of god"
           :tagid :sonofgod
           :locations ( 
                  '(:john 99 105)
                  '(:john 125 136))}))
;
;examples/ideas
;(new-tag tagname symbolname)
;(tag-word tagsymbol wordsymbol)
;(search (and (tag :jesusidentity) (tag :creation)))
;(search (and (phrase "in the begining") (tag :creation)))
;(search (word "god"))
;(search (in-order "in" "beginning"))
;(search (proximity 10 (word "god") (tag :creation)))
;(before (word "god") (word "beginning"))
;(after (word "god") (word "beginning"))
;******************************************
; search functionality
;******************************************
; todo: full text search returning verses
; todo: create context by speaker, author, proximity, etc.
; todo: make search composable
;******************************************
; other 
;******************************************
; todo: reformat into an easier parse tree if it makes sense
; todo: store in datomic, mongo, etc if it makes sense  
; todo: store searches
; todo: save named lists of verses
; todo: upload to ?? think sermon generation, or academic papers.
; todo: are chapters and versus just tags?
; todo: tags are ranges
; todo: alternates are just tag?

;******************************************
;examples
;******************************************
;(let books-of-moses (create-book-context ("gen" "ex" "lev" "num" "deu"))
;(let books-by-john (create-author-context "john"))
;(let books-nt (create-book-context ("matthew" "mark" "luke" "john" "...")))
;(search "son of god" books-of-moses)
;(search "son of god" ("gen"))
;(- (search "son of god" books-new-testament) (search "son of god" books-by-john))
;(search "son of god" (- books-new-testament books-by-john))
;(save "brians's test seearch" (search "son of man" everything))


;***********************************************************
; utilities
;***********************************************************
(defn first-content [element]
  "return the first element within the content of the element passed in"
  (first (:content element)))

(defn no-content [element]
  "dissoc the content within an element"
  (dissoc (into {} element) :content))

;***********************************************************
; bible loading/parsing/listing
;***********************************************************
(defn get-bibles 
  ([] (get-bibles "resources"))
  ([directory]
  (.listFiles (file directory))))

(defn list-bibles
  ([] (list-bibles "resources"))
  ([directory]
  (for [bible-file (get-bibles directory)] (.getName bible-file))))

(defn open-bible [bible-file]
  "open a bible file and parse it into clojure data structure"
  (zip/xml-zip (xml/parse-str (slurp bible-file))))

; given an osis document return the osis work name
(defn get-osis-work-name [osis-doc]
  (->> osis-doc
     :content
     first
     :attrs
     :osisIDWork))

;***********************************************************
; book listing
;***********************************************************
(defn get-osis-divs [osis-doc]
  "return a list of divs within an osis document"
  (filter #(= (:tag %) :div) (:content (first-content osis-doc))))

(defn get-book-name [book]
  "return a book name given the book"
  (:osisID (:attrs (no-content book))))

(defn list-book-names [osis-doc]
  "return a list of book names within an osis document"
  (for [book (get-osis-divs osis-doc)] (get-book-name book)))

(defn map-books [osis-doc]
  "construct a map of books keyed by book name"
  (into {} (for [book (get-osis-divs osis-doc)] 
             [(keyword (get-book-name book)) (:content book)])))


;******************************************
; chapter listing
;******************************************
(defn list-chapters [osis-book]
  "return a list of chapters within an osis book"
  (map :osisID
       (map :attrs
            (->> osis-book
                 (filter #(= (:tag %) :chapter))))))

(defn get-chapters [osis-book]
  "return a list of chapters in a book"
  (->> osis-book
       (filter #(= (:tag %) :chapter))))
                 
(defn get-chapter-name [chapter]
  "return a chapter name given the chapter"
  (:content (first chapter)))

(defn map-chapters [osis-book]
  "return a map containing the chapters within an osis book"
  (into {} (for [chapter (get-chapters osis-book)]
             [(keyword (get-chapter-name chapter)) (:content chapter)])))

(defn write-pretty [data]
  "write the data structure to disk"
  (spit "out.txt" (pr-str data)))

; todo use zippers to extract the verses
; because the verses have tags to delimit start and end
; but the verse isn't the content of the tags
; so we need start -> text -> end  

;************************************************
; repl examples
;************************************************
;parse the second bible file (kjvlite.xml)
;(def parsed (parse  (first (next (get-bibles)))))
;(no-content (get-osis-work parsed))
;(get-osis-work-name parsed)
;(no-content (get-osis-divs parsed))
;(print-books parsed)
;(list-books parsed)

(ns clojure-noob.core
  (:gen-class)
  (:require [instaparse.core :as insta]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def as-and-bs
  (insta/parser
    "S = \\| BOOK_NAME \"\"\\|"))

(def book-names
  (insta/parser
   "BOOK_NAME = #\"[A-Z][a-z]+\"
    CHAPTER_ID = BOOK_NAME #\"\\s+\\d\"
    BOOK_HEADING = \"| \" BOOK_NAME \" Cross References \" #\"\\|\n\" CHAPTER_ID
    REF_LETTER = #\"[a-z]\"
    WHITESPACE = #\"\\s\"
    XREFD_VERSE = #\"\\d+:\\d+\"
    XREF = REF_LETTER WHITESPACE+ XREFD_VERSE WHITESPACE
    BOOK_ABBR = #\"(1|2|3[A-Z])|[A-Z][a-z]+\"
    CHAPTER = #\"\\d+\"
    PHRASEOLOGICAL = BOOK_ABBR WHITESPACE CHAPTER "\:" VERSE_NUM WHITESPACE+ REFS
    REFS = <PHRASEOLOGICAL|CITATION|THEMATIC> "\;""))

(def bible-xrefs (slurp "resources/all-cross-references.txt"))
(book-names bible-xrefs)

(defn get-bible-xrefs [book-name]
  (re-seq
   (re-pattern
    (str
     "("
     book-name
     " \\d+(?s).*?)(?="
     book-name
     " \\d+|$)"))
  bible-xrefs))

(defn add-book-to-map [book map]
  (assoc map book (get-bible-xrefs book)))


(defn map-books-to-references [map books]
  (if (empty? books)
    map
    (map-books-to-references
     (add-book-to-map (first books) map)
     (rest books))))

(def bible-xrefd-book-pattern #"\| ((?s).*?) Cross References \|")

(defn get-bible-xrefd-book-names []
  (let [matches (re-seq bible-xrefd-book-pattern bible-xrefs)]
    (->> matches
         (map second)
         (map str))))

(defn get-references-associated-with-bible-books []
  (map-books-to-references {} (get-bible-xrefd-book-names)))

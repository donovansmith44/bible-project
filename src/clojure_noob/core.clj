(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def bible-xrefs (slurp "resources/all-cross-references.txt"))

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

(ns clojure-noob.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn get-references-associated-with-bible-books []
  (map-books-to-references {} (get-bible-xrefd-book-names)))

(defn get-bible-xrefd-book-names []
  (let [matches (re-seq bible-xrefd-book-pattern bible-xrefs)]
    (->> matches
         (map second)
         (map str))))

(def bible-xrefs (slurp "resources/all-cross-references.txt"))

(def bible-xrefd-book-pattern #"\| ((?s).*?) Cross References \|")


(defn map-books-to-references [map books]
  (if (empty? books)
    map
    (map-books-to-references
     (add-book-to-map (first books) map)
     (rest books))))

(defn add-book-to-map [book map]
  (assoc map book (get-bible-xrefs book)))

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

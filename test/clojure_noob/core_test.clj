(ns clojure-noob.core-test
  (:require [clojure.test :refer :all]
            [clojure-noob.core :refer :all]))

(def xref-map-for-arbitrary-chapter ; Psalm 117
  { :verses
   { :1 {:a #{ {
                :book    "Romans"
                :chapter 15
                :verse   11
                :type    :citation ;; specifically, Romans cites Psalm 117:1
                }}}
    :2  {:b #{ {
                :book    "Psalms"
                :chapter 93
                :verse   11
                :type    :phraseological
                }
              {
               :book    "Psalms"
               :chapter 116
               :verse   5
               :type    :thematic-proximal
               ;; maybe :type :comparative
               }}
         :c #{ {
                :book    "Psalms"
                :chapter 100
                :verse   5
                :type    :thematic-proximal
                ;; maybe :type :comparative
                }}
         :d #{ {
                :book    "Exodus"
                :chapter 34
                :verse   6
                :type    :phraseological
                }}
         :e #{ {
                :book    "Psalm"
                :chapter 116
                :verse   19
                :type    :thematic-distal
                ;; maybe :type :distal-comparative 
                }}} }  })

(def xref-map-for-arbitrary-verse ; Psalm 117:1
  (get xref-map-for-arbitrary-chapter :1))

(deftest transform-raw-refs-into-map
  (testing "Insert raw refs to map call here"
    ;; SHOULD INCLUDE:
    ;; Psalm 117 a 117:1 Cited Rm 15:11
    ;; b 117:2 Ps 103:11; [Ps 116:5]
    ;; c 117:2 [Ps 100:5]
    ;; d 117:2 Ex 34:6
    ;; e 117:2 [See Ps 116:19 above] 
    (is (= xref-map-for-arbitrary-chapter {}))))

(deftest test-get-refs-for-chapter
  (testing "Insert get refs for chapter call here"
  ;; (testing (get-refs-for-chapter Psalm 117)
    (is (= xref-map-for-arbitrary-chapter {}))))

(deftest test-get-refs-for-verse
  (testing "Insert get refs for verse call here"
  ;; (testing (get-refs-for-chapter Psalm 117 1)
    (is (= xref-map-for-arbitrary-verse {}))))

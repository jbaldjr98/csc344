(ns Project2 )
(defn sor [x]
  (if (some true? x)
    true
    (if (some symbol? (rest x))
      (if  (< 2 (count (remove false? x)))
        (remove false? x)
        (last (remove false? (rest x))) )
      false)))

(defn sand [x]
  (if (some false? x)
    false
    (if (some symbol? (rest x))
(if  (< 2 (count (remove true? x)))
  (remove true? x)
  (last (remove true? (rest x))) )
true)))

(defn snot [x]
  (if (some symbol?  (rest x) )
    x
    (if (some true? x)
      false
      true)))

(defn sall [exp]
  (let [exp (map (fn [i]
                   (if (seq? i)
                     (sall i)
                     i))
                 exp)]
    exp
    (cond
      (== 0(compare (first exp) 'or)) (if (seq? exp)
                                        (sor (distinct exp) )
                                        exp)
      (== 0(compare (first exp) 'and)) (if (seq? exp)
                                         (sand (distinct exp) )
                                         exp)
      (== 0(compare (first exp) 'not))(if (seq? exp)
                                        (snot (distinct exp) )
                                        exp))))
(defn deepSub [m l]
  (map (fn [i]
         (if (seq? i)
           (deepSub m i)
           (m i i)))
       l))
(defn evalexp [exp bindings]
  (sall (deepSub bindings exp)))






(ns gallows.core
  (:gen-class))

(def lives-total 6)
(def secret-word "WATERMELON")

(defn lost [] (print "You lost"))

(defn won [] (print "You won!"))

(defn remaining-chars [word rights]
    (remove (fn [char] (contains? rights (str char))) word))

(defn correct-word? [word rights] 
  (empty? (remaining-chars word rights)))


(defn read-guess! [] (read-line))

(defn right? [guess word] (.contains word guess))

(defn print-gallows [lives word rights]
  (println "Lives " lives)
  (doseq [char (seq word)] 
    (if (contains? rights (str char)) 
      (print char " ") (print "_" " ")))
  (println))

(defn game [lives word rights]
  (print-gallows lives word rights)
  (cond
    (= lives 0) (lost)
    (correct-word? word rights) (won)
    :else
    (let [guess (read-guess!)]
      (if (right? guess word)
        (do
          (println "Correct letter!")
          (recur lives word (conj rights guess))
        )
        (do
          (println "Wrong letter!!")
          (recur (dec lives) word rights))))))

(defn start-game [] (game lives-total secret-word #{}))

(defn -main
  [& args]
  (start-game))

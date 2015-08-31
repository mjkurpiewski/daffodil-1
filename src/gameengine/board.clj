(ns gameengine.board
  (:require [io.aviso.ansi :as ansi]))

(def the-empty-space (str ".    "))

(defn buffer-piece
  "This function takes a string, representative of a piece on the game board, and buffers it 
  with spaces until the representation of the piece is 5 characters in length. All whitespace 
  is added to the right side of the piece.
  Input -> A piece representation <string>
  Output -> A buffered piece representation <string>"
  [piece-string]
  (loop [result piece-string]
    (if (= (.length result) 5)
      result
      (recur (str result " ")))))

(defmulti format-piece
  "Multimethod to format a GamePiece. object to a game-specific representation.
  Dispatches based on the game & color of the object passed to format-piece.
  Input: A playing piece <record of type GamePiece>
  Output: string"
  (juxt :game :color))

(defmethod format-piece [:checkers :black] [m]
  (if (= (:shape m) :man)
    (buffer-piece (str \u26C2))
    (buffer-piece (str \u26C3))))

(defmethod format-piece [:checkers :white] [m]
  (if (= (:shape m) :man)
    (buffer-piece (str \u26C0))
    (buffer-piece (str \u26C1))))

(defmethod format-piece [:rithmomachy :blue] [m]
  (ansi/blue (buffer-piece (str (.toUpperCase (.substring (str (:shape m)) 1 2))
                                (str (:value m))))))
(defmethod format-piece [:rithmomachy :red] [m]
  (ansi/red (buffer-piece (str (.toUpperCase (.substring (str (:shape m)) 1 2))
                               (str (:value m))))))

;; To be used in querying the coordinate map.
;; (map #(gameengine.games/checkers-board %) (gameengine.games/board-coordinates 8 8)) 

;; (defmacro colorize-piece [m s]
;;   `(#spy/p ~@(symbol (str 'ansi\/ (name (:color m)))) #spy/p ~s))

;; (defn display-board
;;   "Displays the current game board. This function is only used for it's side effects.
;;   Input -> Game board <map>
;;   Output -> nil"
;;   [sm]
;;   (let [x (:x (:dimensions sm))
;;         y (:y (:dimensions sm))]
;;     (doseq [iy (range y 0 -1)
;;             ix (range 0 (inc x))
;;             :let [v [iy ix]]]
;;       (cond (and (sm v) (= ix x)) (println (format-piece (sm v)))
;;             (sm v) (print (format-piece (sm v)))
;;             (= ix x) (println ".")
;;             :else (print the-empty-space)))))

;; (defn display-board
;;   "Displays the current game board in horizontal orientation, i.e. x = 16
;;   y = 8. Additionally, it will display x & y coordinates on the border of
;;   the board.
;;   Input -> Game board <record of type GameBoard>
;;   Output -> nil, side effect of displaying the board"
;;   [gamemap]
;;   (let [x-dim (:x (:dimensions gamemap))
;;         y-dim (:y (:dimensions gamemap))]
;;     (doseq [y-i (range y 0 -1)
;;             x-i (range 0 (inc x))
;;             :let [v [y-i x-i]]]
      
;;       )))



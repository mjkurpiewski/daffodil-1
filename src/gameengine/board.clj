(ns gameengine.board
  (:require [io.aviso.ansi :as ansi])
  (:import [gameengine.games GamePiece]))

(def the-buffer "     ")
(def the-empty-space (GamePiece. :nil :nil :nil "."))

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

;; !! IMPORTANT NOTES!!!

;; It appears to me that there should be a way to encode the formatting requirements
;; within the data structure of the Game. itself. Perhaps, in the case of checkers,
;; the value of a piece would be a string of its unicode representation?

;; There ARE further opportunities for abstraction here. Keep your eye on the prize.

;; !! IMPORTANT NOTES!!!

;; For checkers
(defmethod format-piece [:checkers :black] [m]
  (if (= (:shape m) :man)
    (buffer-piece (str \u26C2))
    (buffer-piece (str \u26C3))))

(defmethod format-piece [:checkers :white] [m]
  (if (= (:shape m) :man)
    (buffer-piece (str \u26C0))
    (buffer-piece (str \u26C1))))

(defmethod format-piece [:nil :nil] [m]
  (buffer-piece (:value m)))

;; For rithmomachy
(defmethod format-piece [:rithmomachy :blue] [m]
  (ansi/blue (buffer-piece (str (.toUpperCase (.substring (str (:shape m)) 1 2))
                                (str (:value m))))))
(defmethod format-piece [:rithmomachy :red] [m]
  (ansi/red (buffer-piece (str (.toUpperCase (.substring (str (:shape m)) 1 2))
                               (str (:value m))))))

(defn index-row
  "Creates an index row with indices running from 1 to x-dimension, inclusive.
  The index row is preceded with a buffered empty space so as to preserve proper
  indexing."
  [i]
  (conj (map buffer-piece (map str (range 1 (inc i)))) the-buffer))

(defn format-row
  "When given an empty sequence, will print an indexing row with a leading blank space.
  Otherwise, format-row is given a sequence of eight objects, said objects are either
  nil or of record type GamePiece. format-row will appropriately format each piece or
  nil and return a string prefixed with an index and a line drawing character.
  Input <- A sequence of eight objects, or a special nil sequence.
  Output -> A properly formatted string, with properly formatted objects, terminated with \n"
  [s]
  )

(defn display-board
  "Given a game object, will display an approprate terminal representation of the board 
  associated with the game provided. Will provide border fringe along the X and Y axes to
  denote positioning on the board. Fringe index begins at 1, unlike actual coordinate
  representation in the structure, which begins at 0.
  Input <- An object of record type Game
  Output -> (side-effect) Display current board."
  [game]
  (doseq [rows-to-iterate (range 0 (inc (-> game :dimensions :y))),
          :let [rowmap (fn [board] (partition 8 (:game-board game)))]]
    (println (rowmap))))

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


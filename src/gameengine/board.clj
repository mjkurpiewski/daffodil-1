(ns gameengine.board
  (:require [io.aviso.ansi :as ansi]
            [gameengine.games :as games])
  (:import gameengine.games.GamePiece))

(def the-buffer "       ")
(def the-empty-space (GamePiece. :nil :nil :nil "."))

(defn buffer-piece
  "This function takes a string, representative of a piece on the game board, and buffers it 
  with spaces until the representation of the piece is 5 characters in length. All whitespace 
  is added to the right side of the piece.
  Input -> A piece representation <string>
  Output -> A buffered piece representation <string>"
  [piece-string target]
  (loop [result piece-string]
    (if (= (.length result) target)
      result
      (recur (str result " ")))))

(defmulti format-piece
  "Multimethod to format a GamePiece. object to a game-specific representation.
  Dispatches based on the game & color of the object passed to format-piece.
  Input: A playing piece <record of type GamePiece>
  Output: string"
  (fn [m]
    (if m
      ((juxt :game-name :color) m)
      [:empty])))

;; !! IMPORTANT NOTES!!!

;; It appears to me that there should be a way to encode the formatting requirements
;; within the data structure of the Game. itself. Perhaps, in the case of checkers,
;; the value of a piece would be a string of its unicode representation?

;; There ARE further opportunities for abstraction here. Keep your eye on the prize.

;; !! IMPORTANT NOTES!!!

;; For checkers
(defmethod format-piece [:checkers :black] [m]
  (if (= (:shape m) :man)
    (buffer-piece (str \u26C2) 5)
    (buffer-piece (str \u26C3) 5)))

(defmethod format-piece [:checkers :white] [m]
  (if (= (:shape m) :man)
    (buffer-piece (str \u26C0) 5)
    (buffer-piece (str \u26C1) 5)))

(defmethod format-piece [:empty] [m]
  (buffer-piece (str (:value the-empty-space)) 5))

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
  indexing. Can take an optional offset argument of type integer to improve display.
  Input <- i, an integer for the length of index row & an optional offset argument.
  Output -> A string representing a row that indexes the x positions on a board."
  [i & offset]
  (let [indexstring (map str (range 1 (inc i)))]
    (conj (map (fn [n]
                 (buffer-piece n (first offset)))
               indexstring)
          the-buffer)))

(defn format-row
  "When given an empty sequence, will print an indexing row with a leading blank space.
  Otherwise, format-row is given a sequence of eight objects, said objects are either
  nil or of record type GamePiece. format-row will appropriately format each piece or
  nil and return a string prefixed with an index and a line drawing character.
  Input <- A sequence of eight objects, or a special nil sequence.
  Output -> A properly formatted string, with properly formatted objects, terminated with \n"
  [row]
  (map format-piece row))

(defn query-board [game]
  (let [gameboard (:game-board game)
        x (-> game :dimensions :x)
        y (-> game :dimensions :y)]
    (map gameboard (games/board-coordinates x y))))

(defn display-board
  "Given a game object, will display an approprate terminal representation of the board 
  associated with the game provided. Will provide border fringe along the X and Y axes to
  denote positioning on the board. Fringe index begins at 1, unlike actual coordinate
  representation in the structure, which begins at 0.
  Input <- An object of record type Game
  Output -> (side-effect) Display current board."
  [game]
  (doseq [rowmap (partition (-> game
                                :dimensions
                                :y)
                            (query-board game))]
    (println (format-row rowmap))))

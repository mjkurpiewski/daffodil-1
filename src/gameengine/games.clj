(ns gameengine.games)

(defrecord GamePiece [color shape game value])
(defrecord GameRules [movement extra-rules])
(defrecord Game [game-name game-board dimensions rules])

;; Example Rules
(def checkers-rules (GameRules. {:king '([-1 -1] [-1 1] [1 -1] [1 1]),
                                 :man {:white '([-1 1] [1 1]),
                                       :black '([-1 -1] [-1 1])}}
                                nil))

;; Standard pieces
(def black-piece (GamePiece. :black :man :checkers nil))
(def white-piece (GamePiece. :white :man :checkers nil))

;; Sketch of a map function for querying the board
(map #(gameengine.games/checkers-board %) (gameengine.games/board-coordinates 8 8)) 

;; Board map [y x] coordinate, origin [0,0] @ top-left
(def checkers-board {[0 1] black-piece,
                     [0 3] black-piece,
                     [0 5] black-piece,
                     [0 7] black-piece,
                     [1 0] black-piece,
                     [1 2] black-piece,
                     [1 4] black-piece,
                     [1 6] black-piece,
                     [2 1] black-piece,
                     [2 3] black-piece,
                     [2 5] black-piece,
                     [2 7] black-piece,
                     [5 0] white-piece,
                     [5 2] white-piece,
                     [5 4] white-piece,
                     [5 6] white-piece,
                     [6 1] white-piece,
                     [6 3] white-piece,
                     [6 5] white-piece,
                     [6 7] white-piece,
                     [7 0] white-piece,
                     [7 2] white-piece,
                     [7 4] white-piece,
                     [7 6] white-piece})

(defn board-coordinates [x-dimension y-dimension]
  (for [x (range x-dimension),
        y (range y-dimension)]
    [x y]))

;;Types of pieces: Men or Kings. Men can move diagonally, as many times as they can capture.
(def checkers (Game.
               :checkers
               checkers-board
               {:x 8, :y 8}
               checkers-rules))

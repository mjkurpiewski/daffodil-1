(ns gameengine.games)

(defrecord Game [game-name game-board dimensions rules])
(defrecord GamePiece [color shape game-name value])
(defrecord GameRules [movement extra-rules])

(defn board-coordinates [x-dimension y-dimension]
  (for [x (range x-dimension),
        y (range y-dimension)]
    [x y]))

(def memo-coords (memoize board-coordinates))

;; Sketch of a map function for querying the board
;; (map #(gameengine.games/checkers-board %) (gameengine.games/board-coordinates 8 8)) 

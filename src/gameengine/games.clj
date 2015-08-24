(ns gameengine.games)

(defrecord GamePiece [color shape value])
(defrecord GameRules [movement extra-rules])
(defrecord Game [game-name game-board dimensions rules])

;; Example piece
(def a-piece (GamePiece. :black :man nil))
(def b-piece (GamePiece. :white :man nil))
(def c-piece (GamePiece. :black :king nil))

;; Example Rules
(def checkers-rules (GameRules. {:king '([-1 -1] [-1 1] [1 -1] [1 1]),
                                 :man {:white '([-1 1] [1 1]),
                                       :black '([-1 -1] [-1 1])}}
                                nil))

;; Types of pieces: Men or Kings. Men can move diagonally, as many times as they can capture.
;; (def checkers (Game.
;;                :checkers
;;                {
;;                 }
;;                {:x 8, :y 8}
;;                ))

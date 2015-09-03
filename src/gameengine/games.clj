(ns gameengine.games)

(defrecord Game [game-name game-board dimensions rules])
(defrecord GamePiece [color shape game-name value])
(defrecord GameRules [movement conditions])

(defn board-coordinates
  "Produces a lazy sequence of all possible [y,x] vector coordinate pairs for a
  board of given dimensions.
  Input <- Two integers, one representing the y dimension of the board, the
           other representing the x dimension.
  Output -> A lazy sequence of [y,x] coordinate pairs covering every position on
            the x by y dimensional board."
  [x-dimension y-dimension]
  (for [x (range x-dimension),
        y (range y-dimension)]
    [x y]))

(def memo-coords
  "This might not really matter as far as optimization goes, at least for cases
  of the size board games often deal with. I haven't spent much time testing it yet,
  but the amount of time required to generate that lazyseq for boards of dimension
  8x8 and 16x8 differ very little from the amount of time of the memoized function.
  Clojure's native implementation of memoization is supposed to not be very great,
  either, so a separate library or hand written memoizer might be a better option yet.
  If this is even necessary in the first place. Maybe a 128x128 board would truly
  benefit. I should spend some time calculating performance characteristics this
  weekend of 9/5."
  (memoize board-coordinates))

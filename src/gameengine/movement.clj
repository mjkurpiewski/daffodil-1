(ns gameengine.movement
  (:use [gameengine.board :as board]))

(defn generate-moves
  "Given a position, and a sequence of possible movements to make from that position,
  return the list of possible moves from a position.
  Input -> position <vec>, movement <seq of vecs>
  Output -> possible-moves <seq of vecs>"
  [position movement]
  (map (fn [move] (map + position move)) movement))

(defn in-range?
  "Predicate function which determines whether a given position is in range on the kind of
  board it is to be placed on.
  Input -> pos <vec>
  Output -> boolean"
  [pos]
  (let [y-range (:y (:dimensions board/game-board))
        x-range (:x (:dimensions board/game-board))
        y (nth pos 0)
        x (nth pos 1)]
    (and (and (>= x 1) (<= x x-range))
         (and (>= y 1) (<= y y-range)))))

(defn valid-moves
  "Given a position, and a sequence of possible movements to make from that position,
  return the list of valid moves the piece at position could make.
  Input -> position <vec>, movement <seq of vecs>
  Output -> valid-moves <seq of vecs>"
  [position movement]
  (filter in-range? (generate-moves position movement)))

(defn valid-move? [board from to]
  (let [movement ((:shape (board from)) (:movement board))
        move-pool (valid-moves from movement)]
    (if (nil? (some #{to} move-pool))
      false
      true)))

(defn move! [from to board]
  (let [piece (board from),
        destination-occupied? (#(if (nil? (%1 %2)) false true) board to)]
    (if (and (not destination-occupied?) (valid-move? board from to))
      (let [interim-board (dissoc board from)]
        (assoc interim-board to piece))
      false)))

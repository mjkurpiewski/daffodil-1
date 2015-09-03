(ns gameengine.movement
  (:import [gameengine.games
            Game
            GamePiece
            GameRules]))

(defn generate-moves
  "Given a position, and a sequence of possible movements to make from that position,
  return the list of possible moves from a position.
  Input -> position <vec>, movement <seq of vecs>
  Output -> possible-moves <seq of vecs>"
  [position movement]
  (map (fn [move]
         (map + position move))
       movement))

(defn in-range?
  "Predicate function which determines whether a given position is in range on the kind of
  board it is to be placed on.
  Input -> pos <vec>
  Output -> boolean"
  [game pos]
  (let [y-range (:y (:dimensions game))
        x-range (:x (:dimensions game))
        y (nth pos 0)
        x (nth pos 1)]
    (and  (>= x 1) (<= x x-range)
          (>= y 1) (<= y y-range))))

(defn valid-moves
  "Given a position, and a sequence of possible movements to make from that position,
  return the list of valid moves the piece at position could make.
  Input -> position <vec>, movement <seq of vecs>
  Output -> valid-moves <seq of vecs>"
  [game position movement]
  (filter (fn [pos]
            (in-range? game pos))
          (generate-moves position movement)))

(defn valid-move? [game from to]
  (let [piece ((:game-board game) from)
        movement ((:color piece) ((:shape piece) (:movement (:rules game))))
        move-pool (valid-moves game from movement)]
    ;; (if (nil? (some #{to} move-pool))
    ;;   false
    ;;   true)
    (some #{to} move-pool)))

(defn move! [game from to]
  (let [board (:game-board game)
        piece (board from)
        destination (board to)]
    (if (and (nil? destination)
             (valid-move? game from to))
      (let [interim-board (dissoc board from)]
        ;;        (Game. (:game-name game) (assoc board to piece) (:dimensions game) (:rules game)))
        (assoc game :game-board (assoc interim-board to piece))
      false))))

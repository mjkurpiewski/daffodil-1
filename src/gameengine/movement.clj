(ns gameengine.movement
  (:import [gameengine.games
            Game
            GamePiece
            GameRules]))

(defn generate-moves
  "Given a position, and a sequence of possible movements to make from that position,
  return the list of possible moves from a position.
  Input <- A y,x coordinate vector that denotes the position of the piece to be moved,
           A sequence of vectors that denote the possible moves the piece could take.
  Output -> A sequence of vectors of moves that are technically possible for the piece
            to make, but they may or may not actually be legal/valid moves."
  [position movement]
  (map (fn [move]
         (map + position move))
       movement))

(defn in-range?
  "Predicate function which determines whether a given position is in range on the kind of
  board it is to be placed on.
  Input <- The current stateful Game object,
           A y,x coordinate vector that we want to ascertain is in the boundary
           of the board.
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
  Input <- The current stateful Game object,
           A y,x coordinate vector for the piece to be moved,
           A sequence of possible ways that the piece may legally move.
  Output -> A sequence of vectors denoting all possible valid moves the piece could make,
            disregarding whether or not the space is currently occupied."
  [game position movement]
  (filter (fn [pos]
            (in-range? game pos))
          (generate-moves position movement)))

(defn valid-move?
  "This predicate will ascertain if a move is valid on some game board. Validity
  is defined by whether or not the destination denoted as 'to' is in the pool of
  valid moves that we created using the function valid-moves. This is done by
  testing for membership of 'to' in the set of valid moves.
  Input <- The current stateful Game object,
           A y,x coordinate vector of the piece to be moved,
           A y,x coordinate vector of the destination of the piece.
  Output -> A boolean value."
  [game from to]
  (let [piece ((:game-board game) from)
        movement ((:color piece) ((:shape piece) (:movement (:rules game))))
        move-pool (valid-moves game from movement)]
    (some #{to} move-pool)))

(defn move!
  "This function executes the movement of a piece from one location, 'from', to
  a destination, 'to', both of which are y,x coordinate vectors. After checking
  for the validity of the move, move! returns a new object of type Game that
  reflects the updated state of the game.
  Input <- The current stateful Game object,
           A y,x coordinate vector of the piece to be moved,
           A y,x coordinate vector of the destination of the piece.
  Output -> An updated object of type Game."
  [game from to]
  (let [board (:game-board game)
        piece (board from)
        destination (board to)]
    (if (and (nil? destination)
             (valid-move? game from to))
      (let [interim-board (dissoc board from)]
        (assoc game :game-board (assoc interim-board to piece)))
      false)))

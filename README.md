# daffodil-1, a somewhat generic board game engine in Clojure

## Status

This is a work in progress that has taken a back seat to other projects, namely
ones that are better designed to improve my proficiency with mathematics and
computer science. While I very much enjoy working on this project and hope to see
it through to a usable state by the end of the year, it remains to be seen if that
will actually happen.

As far as a roadmap to completion, I need to more fully define what constitutes a
game rule and provide facilities for the game to use these rules to test for
correctness of rules, as well as conditions that may indicate victory.

A longer term goal for the project is to utilize ClojureScript and Om to create a
user friendly and interactive front-end system that will allow users to play games,
potentially of their own definition, in the browser!

Features I have also thought about implementing, and still hope to, include:
* Pretty terminal/ASCII graphic support
* Interactivity with a Datomic database to facilitate playback of completed games
using the initial game configuration as well as the subsequent diffs from the initial state.

## Usage

Current usage is look at the code, maybe clone the repository and play with it
in your own Clojure repl.

### Bugs

"Here there be dragons."



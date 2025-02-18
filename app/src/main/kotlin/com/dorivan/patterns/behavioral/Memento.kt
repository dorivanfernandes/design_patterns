package com.dorivan.patterns.behavioral

/*
Memento is used when you want to have snapshots from your object. You can restore or undo modifications, going back
to the previous state.
 */


data class ChessMemento(val boardState: Map<String, String>)

class ChessBoard {
    private val board = mutableMapOf<String, String>() // Position -> Piece

    init {
        setupBoard()
    }

    private fun setupBoard() {
        board["E2"] = "Pawn White"
        board["E7"] = "Pawn Black"
        board["D1"] = "Queen White"
        board["D8"] = "Queen Black"
    }

    fun movePiece(from: String, to: String) {
        val piece = board[from]
        if (piece != null) {
            board.remove(from)
            board[to] = piece
            println("$piece moved from $from to $to")
        } else {
            println("No piece at $from!")
        }
    }

    fun createMemento(): ChessMemento {
        return ChessMemento(board.toMap())
    }

    fun restoreMemento(memento: ChessMemento) {
        board.clear()
        board.putAll(memento.boardState)
        println("Board state restored!")
    }

    fun printBoard() {
        println("Current Board State: $board")
    }
}

// Class to manage the History (Caretaker)
class MoveHistory {
    private val history = mutableListOf<ChessMemento>()

    fun saveState(board: ChessBoard) {
        history.add(board.createMemento())
    }

    fun undo(board: ChessBoard) {
        if (history.isNotEmpty()) {
            val lastState = history.removeLast()
            board.restoreMemento(lastState)
        } else {
            println("No moves to undo!")
        }
    }
}

fun main() {
    val board = ChessBoard()
    val history = MoveHistory()

    board.printBoard()

    history.saveState(board)
    board.movePiece("E2", "E4")
    history.saveState(board)

    board.movePiece("E7", "E5")
    history.saveState(board)

    board.printBoard()

    println("\nUndo last move...")
    history.undo(board)
    board.printBoard()

    println("\nUndo another move...")
    history.undo(board)
    board.printBoard()
}

package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private lateinit var statusTextView: TextView
    private lateinit var playAgainButton: Button


    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } } // 3x3 grid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        statusTextView = findViewById(R.id.statusTextView)
        playAgainButton = findViewById(R.id.playAgainButton)

        buttons = arrayOf(
            findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3),
            findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6),
            findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9)
        )


        for (i in buttons.indices) {
            buttons[i].setOnClickListener {
                onCellClick(it as Button, i / 3, i % 3)
            }
        }

        playAgainButton.setOnClickListener { resetGame() }
    }

    private fun onCellClick(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty() || board[row][col].isNotEmpty()) return


        button.text = currentPlayer
        board[row][col] = currentPlayer


        if (checkWinner(row, col)) {
            statusTextView.text = "Player $currentPlayer Wins!"
            endGame()
        } else if (isDraw()) {
            statusTextView.text = "It's a Draw!"
            endGame()
        } else {

            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusTextView.text = "Player $currentPlayer's Turn"
        }
    }

    private fun checkWinner(row: Int, col: Int): Boolean {

        return (board[row].all { it == currentPlayer } ||
                board.all { it[col] == currentPlayer } ||
                (row == col && board.indices.all { board[it][it] == currentPlayer }) ||
                (row + col == 2 && board.indices.all { board[it][2 - it] == currentPlayer }))
    }

    private fun isDraw(): Boolean {
        return board.all { row -> row.all { it.isNotEmpty() } }
    }

    private fun endGame() {
        for (button in buttons) button.isEnabled = false
        playAgainButton.visibility = View.VISIBLE
    }

    private fun resetGame() {

        for (i in buttons.indices) {
            buttons[i].text = ""
            buttons[i].isEnabled = true
        }
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        statusTextView.text = "Player X's Turn"
        playAgainButton.visibility = View.GONE
    }
}

package org.example

data class Words(val word: String, val description: String)

class GameData(var lives: Int){
    private var gameRunning = true
    private lateinit var guessedWordStatus: CharArray
    private lateinit var m_randomWord: String

    // Helper function to keep things tidy and concise in the game loop
    fun generateRandom(): Words{
        val wordsList = listOf<Words>(
            Words("Hangman", "The name of the game"),
            Words("Awry", "Take a detour from originally planned"),
            Words("Ubiquitous", "Seeming to be everywhere"),
            Words("Corroborate", "Support an alibi")
        )
        return wordsList.random()
    }
    fun startGameLoop(){
        println("Welcome to Hangman!")

        val (randomWord, randomDescription) = generateRandom()
        m_randomWord = randomWord
        guessedWordStatus = CharArray(m_randomWord.length){ '_' }
        while (gameRunning){
            println("Lives remaining: $lives")
            println("Your guess: ${guessedWordStatus.joinToString(" ")}")
            println("I'm thinking of: $randomDescription")
            print("Your guess: ")
            val guess = readln().lowercase()

            if(guess.length != 1) {
                println("Please enter one character!")
                continue
            }
            if(String(guessedWordStatus).contains(guess)){
                println("$guess already appended!")
                continue
            }

            if(m_randomWord.lowercase().contains(guess)){
                m_randomWord.lowercase().forEachIndexed { index, char ->
                    if(char.toString() == guess){
                        println("Guess found at index: $index")
                        guessedWordStatus[index] = m_randomWord[index]
                    }
                }
            }else{
                lives--
            }
            performConditionChecks()
        }
    }
    fun performConditionChecks(){
        if(lives <= 0){
            println("Out of lives! I was thinking of: $m_randomWord")
            gameRunning = false
        }
        if(String(guessedWordStatus) == m_randomWord){
            println("Nice! I was thinking of: $m_randomWord")
            gameRunning = false
        }
        return
    }
}
fun main() {
    val game = GameData(5)
    game.startGameLoop()
}
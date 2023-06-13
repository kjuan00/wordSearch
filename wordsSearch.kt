import java.io.File

/************************************************************
 *  Name:         Alonzo Silvaz
 *  Date:         6/15/2021
 *  Assignment:   WordSearch Generator
 *  Class Number: 283
 *  Description:  Program that generates a Puzzle and a Key file
                  from a words.txt
 ************************************************************/

class Puzzle(var height: Int, var width: Int, var fileName: String){

    public var grid1 = Array(height) { Array(width) {'_'}  }
    var wordList = ArrayList<String>()

    var fdKey = File("src/main/kotlin/puzzleKey.txt").printWriter()
    var fdPuz = File("src/main/kotlin/puzzle.txt").printWriter()

    fun createPuzzle(){

        //Calling File
        var inputFD = File(fileName).forEachLine { wordList += it }

        var orientations = arrayOf("leftRight", "rightLeft", "upDown", "downUp", "diagonalLeftRightUp", "diagonalRightLeftUp", "diagonalLeftRightDown", "diagonalRightLeftDown")


        //Main Loop for Printing Grid w/words
        for (word in wordList){
            //Set variable equal to word length
            var wordLength = word.length
            var placed = false

            //Checking each word that doesn't place
            while (placed == false){

                var stepX = 0
                var stepY = 0

                //Orientation Setup
                var orientation = orientations.random()

                if (orientation == "leftRight"){stepX = 1; stepY = 0}
                if (orientation == "rightLeft"){stepX = -1; stepY = 0}
                if (orientation == "upDown"){stepX = 0; stepY = 1}
                if (orientation == "downUp"){stepX = 0; stepY = -1}
                if (orientation ==  "diagonalLeftRightUp"){stepX = 1; stepY = -1}
                if (orientation == "diagonalRightLeftUp"){stepX = -1; stepY = -1}
                if (orientation == "diagonalLeftRightDown"){stepX = 1; stepY = 1}
                if (orientation == "diagonalRightLeftDown"){stepX = -1; stepY = 1}


                //Random Beginning Point Pos
                var xPos = (0..width-1).random()
                var yPos = (0..height-1).random()


                //Ending Points Placement
                var endingPosX = xPos + wordLength * stepX
                var endingPosY = yPos + wordLength * stepY


                //Check if Placement in Range of Grid
                if (endingPosX < 0 || endingPosX >= width){continue}
                if (endingPosY < 0 || endingPosY >= height){continue}


                //Verify if Passed
                var failed = false


                //Check to see if it Can place
                for (i in (0..wordLength - 1)){
                    var char = word[i]

                    var newPosX = xPos + i*stepX
                    var newPosY = yPos + i*stepY

                    var newCharPos = grid1[newPosY][newPosX]



                    if (newCharPos != '_'){
                        if (newCharPos == char){
                            continue
                        }else{
                            failed = true
                            break
                        }
                    }
                }

                //If failed == false than place / else return to loop
                if (failed == true){
                    continue
                }else{
                    for (i in 0..wordLength - 1){
                        var char = word[i]

                        var newPosX = xPos + i*stepX
                        var newPosY = yPos + i*stepY

                        grid1[newPosY][newPosX] = char.toUpperCase()
                    }
                    placed = true
                }


            }
        }

        //Writing to File PuzzleKey
        for (y in grid1){
            fdKey.println()
            for (x in y){
                fdKey.print(x + "\t")
            }
        }
        fdKey.println("")
        for (y in displayWordList()){

            for (x in y){
                fdKey.print(x)
            }
        }
        fdKey.close()


    }

    fun displayPuzzleKey(): ArrayList<String>{
        var wordLines = ArrayList<String>()

        for (y in grid1){
            for (x in y){
                wordLines += x!!.toString()
            }
            wordLines += "\n"
        }
        wordLines += "\n"

        return wordLines
    }

    fun displayPuzzle(): ArrayList<String>{
        var wordLines = ArrayList<String>()

        var allLetters = wordList.toString().replace(",", "").replace("[", "").replace("]", "")
        var letters = allLetters.toCharArray().distinct().toString().replace(",", "").replace("[", "").replace("]", "").replace(" ", "")


        for (y in 0..grid1.size - 1){
            for (x in 0..grid1.size - 1){
                var randNum = (0..letters.length - 1).random()
                if (grid1[y][x] == '_'){
                    grid1[y][x] = letters[randNum].uppercaseChar()
                }
            }
        }

        //Writing to File Puzzle
        for ( y in grid1){
            fdPuz.println()
            for (x in y){
                fdPuz.print(x + "\t")
            }
        }
        fdPuz.println("")
        for (y in displayWordList()){
            for (x in y){
                fdPuz.print(x)
            }
        }
        fdPuz.close()

        for (y in grid1){
            for (x in y){
                wordLines += x.toString()
            }
            wordLines += "\n"
        }
        return wordLines
    }

    fun displayWordList(): java.util.ArrayList<String>{
        var wordL = ArrayList<String>()
        wordL += "Choose a word: \n"
        for ((index,word) in wordList.withIndex()){

            if ((index + 1) % 3 == 0 && index != 0){
                wordL += "\t"
                wordL += word + "\n"
            }else{
                wordL += "\t"
                wordL += word + "\t"
            }
        }
        return wordL
    }
}

fun main(){
    var puz = Puzzle(40,40,"src/main/kotlin/words.txt")

    puz.createPuzzle()
    println(puz.displayPuzzleKey().toString().replace("," , "").replace("[", " ").replace("]", ""))
    println(puz.displayWordList().toString().replace("," , "").replace("[", " ").replace("]", ""))
    println(puz.displayPuzzle().toString().replace("," , "").replace("[", " ").replace("]", ""))
    println(puz.displayWordList().toString().replace("," , "").replace("[", " ").replace("]", ""))


}
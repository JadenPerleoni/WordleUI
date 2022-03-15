import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.*;

public class Wordle {


    public void play(){
        Game oGame = new Game();
        // Number of user's guesses.
        int iNumGuesses = 1;

        // Makes a multidimensional array that will be used to format the game.
        String[][] asGrid = new String[5][6];

        // Makes an ArrayList of five-letter words.
        ArrayList<String> alWords = new ArrayList<>();

        // Makes an object of SecureRandom.
        SecureRandom oRand = new SecureRandom();

        // Reads the words from words.txt into alWords.
        try {
            File myObj = new File("words.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                alWords.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Gets a random word from words.txt
        String sRandomWord = (alWords.get(oRand.nextInt(alWords.size())));

        // Stores the inputted letters that are not in the array.
        List<String> alUnusedLetters = new ArrayList<>();

        oGame.createGame();


        // Creates a blank game board
        for (int y = 0; y < asGrid[0].length; y++) {
            for (int x = 0; x < asGrid.length; x++) {
                asGrid[x][y] = "1";
            }
        }
        // While the number of guesses has not reached 7, the game is active.
        while (iNumGuesses < 7) {

            // Gets user's guess
            String sUserGuess = JOptionPane.showInputDialog("Enter guess #" + iNumGuesses);

            // Splits the user's guess into separate letters and stores them in an array.
            String[] aUserWord = sUserGuess.split("");

            // Converts array to an arraylist to access arraylist methods.
            List alUserWord = Arrays.asList(aUserWord);

            // Splits the random word into separate letters and stores them in an array.
            String[] aRandomWord = sRandomWord.split("");

            // Converts array to an arraylist to access arraylist methods.
            List alRandomWord = Arrays.asList(aRandomWord);

            // Checks if user input is valid
            if (sUserGuess.length()>5) {
                JOptionPane.showMessageDialog(oGame.getFrame(),"Too many letters.");
                continue;

            }else if (!alWords.contains(sUserGuess)){
                JOptionPane.showMessageDialog(oGame.getFrame(),"Word does not exist.");
                continue;
            }

            // Edits the game grid with the user's input.
            createGrid(asGrid,iNumGuesses,alUserWord,alRandomWord,alUnusedLetters);

            // Converts the arraylist into a set, to remove duplicate letters.
            Set<String> set = new LinkedHashSet<>(alUnusedLetters);
            alUnusedLetters.clear();
            alUnusedLetters.addAll(set);

            // Updates the game board.
            oGame.updateGame(asGrid);

            // Tells the user what letters aren't in the word.
            oGame.unusedLetters(alUnusedLetters);

            // Checks if user's input is the correct word.
            if (sUserGuess.equals(sRandomWord)) {
               oGame.setWinStatus("You win! " + iNumGuesses + "/6");
               break;
            }
            // Checks if the user has reached the max number of guesses,
            // and tells them that they lost.
            else if (iNumGuesses == 6) {
               oGame.setWinStatus("You lose. The correct word was: " + sRandomWord);
               break;
            }

            iNumGuesses++;
        }
    }
    public void createGrid(String[][] asGrid, int iNumGuesses, List<String> alUserWord,List<String> alRandomWord,
                           List<String> alUnusedLetters) {

        // Int y is set to the number of guesses minus one, to ensure that previous attempts
        // are not written over.
        for (int y = iNumGuesses-1; y < asGrid[0].length; y++) {
            for (int x = 0; x < asGrid.length; x++) {

                // Checks if any letters of userWord are in the same place as random word, and
                // if there are, set the position in the array to that letter.
                if (alUserWord.get(x).equals(alRandomWord.get(x)) && y == iNumGuesses-1) {
                    asGrid[x][y] = (String) alUserWord.get(x);

                }
                // Checks if any letters of userWord exist in the correct word, and if
                // there are, set the position in the array to a capitalized version of that letter.

                // NOTE: Capitalized letters indicate that the letter exists in the word, but
                // is not in the correct position.
                else if (alRandomWord.contains(alUserWord.get(x)) && y == iNumGuesses-1) {
                    String temp = (String) alUserWord.get(x);
                    asGrid[x][y] = temp.toUpperCase();
                }
                // If the letter is not in the word, it will set the position to an X, and
                // add the unused letters to arraylist.
                else {

                    // Makes sure that the only row that is updated is the
                    // row that the user is guessing on.
                    if (y == iNumGuesses-1) {
                        alUnusedLetters.add((String) alUserWord.get(x));
                        asGrid[x][y] = "X";
                    } else {
                        asGrid[x][y] = "0";
                    }
                }
            }
        }
    }
}

package uk.ac.aber.cs21120.rhymes.solution;
import uk.ac.aber.cs21120.rhymes.interfaces.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;
/**
 * A class that implements the dictionary interface. It creates a  hash map which
 * acts as a dictionary of words and their pronunciations, it is created from the
 * CMU pronouncing dictionary. This dictionary can be used to find the rhymes for
 * each word.
 *
 * @author Beck Chamberlain
 */

public class Dictionary implements IDictionary {
    Map<String, IWord> dictionary;
    int numberOfPronunciations;

    /**
     * Dictionary constructor, initialises an empty HashMap which has the standard English
     * spelling of the word as the key and the IWord object itself as the value.
     * Also sets the variable numberOfPronunciations to zero.
     *
     */

    public Dictionary() {
        dictionary = new HashMap<>();
        numberOfPronunciations = 0; //Holds the value of how many pronunciations there are in the whole dictionary.
    }

    /**
     * Returns the IWord object that aligns with the key given in the dictionary. The key being the
     * word parameter.
     *
     * @param word the english spelling of the word
     * @return the word or null if it is not in the dictionary
     */
    @Override
    public IWord getWord(String word) {
        return dictionary.get(word); //Will return null if not found
    }

    /**
     * Adds a word to the dictionary.
     * If the word already exists, IllegalArgumentException is thrown.
     * Increments numberOfPronunciations appropriately.
     *
     * @param word the word to add
     * @throws IllegalArgumentException if the word already exists
     */
    @Override
    public void addWord(IWord word) throws IllegalArgumentException {
        String currentWord = word.getWord(); //Sets currentWord to the spelling of the word for the object passed in
        if(dictionary.containsKey(currentWord)){
            throw new IllegalArgumentException();
        }
        dictionary.put(currentWord,word);
        numberOfPronunciations += word.getPronunciations().size(); //Increments numberOfPronunciations by how many pronunciations the word has.
    }

    /**
     * Returns the number of words in the dictionary.
     *
     * @return the size of the hashmap dictionary.
     */
    @Override
    public int getWordCount() {
        return dictionary.size();
    }

    /**
     * Returns the value of the global variable numberOfPronunciations. Which tracks the number
     * of pronunciations in the dictionary.
     *
     * @return the number of pronunciations in the dictionary.
     */
    @Override
    public int getPronunciationCount() {
        return numberOfPronunciations;
    }

    /**
     * Parses a line from the CMU Pronouncing Dictionary.
     * This process removes negligible information, gets the word and its pronunciation, and
     * adds it to the Hashmap.
     *
     *
     * @param line the line to parse
     */
    @Override
    public void parseDictionaryLine(String line) {
        String word = line.substring(0, line.indexOf(" ")); //Gets the word itself and its qualifier in brackets if it has one.
        String pronunciationString; //This variable will contain the Phonemes that make up the words' pronunciation.
        IPronunciation pronunciation = new Pronunciation(); //Initialises a new object of the Pronunciation class that will hold the phonemes for this words pronunciation.

        if (line.contains("#")) { //If the line contains negligible information about the word the substring will have an endpoint as well to cut it out.
            pronunciationString = line.substring(line.indexOf(" ") + 1, line.indexOf("#")).trim(); //Gets the phonemes from the line and removes any extra spaces from the end.
        } else {
            pronunciationString = line.substring(line.indexOf(" ") + 1).trim(); //Gets the phonemes from the line and removes any extra spaces from the end.
        }

        String[] pronunciationArray = pronunciationString.split(" "); //Splits the string containing all the phonemes to get them individually in smaller strings and then puts the result into an array.

        for (String currentPhoneme : pronunciationArray) { //Loop that goes through every phoneme in the pronunciationArray.
            char lastCharacter = currentPhoneme.charAt(currentPhoneme.length() - 1); //Initialised as the last character of the Phoneme.
            int stress = -1; //Initialises stress to -1, so it is the 'default' value.
            if (Character.isDigit(lastCharacter)) { //If the last character is a digit it means the phoneme is a vowel and thus the last character is holding its stress value.
                stress = Character.getNumericValue(lastCharacter); //Converts the last character to an integer and stores it in stress.
                currentPhoneme = currentPhoneme.substring(0, currentPhoneme.length() - 1); //Gets just the phoneme without its stress.
            }
            pronunciation.add(new Phoneme(Arpabet.valueOf(currentPhoneme), stress)); //Adds the current phoneme and its stress value (or lack thereof) to the pronunciation.
        }
        int openBracketIndex = word.indexOf("("); //Finds the index of an open bracket in the line if it exists.
        if (openBracketIndex != -1) { //If there are brackets after the word then make a substring to get just the word.
            word = word.substring(0, openBracketIndex);
        }
        IWord preExistingWord = getWord(word); //Looks for if the word exists in the dictionary with getWord, if it does then sets this variable to the preexisting word.
        if (preExistingWord != null) { //If the word already exists.
            preExistingWord.addPronunciation(pronunciation); //Adds the new pronunciation to the pre-existing word.
            numberOfPronunciations ++; //Increments numberOfPronunciations by 1.
        } else { //If the word doesn't already exist.
            IWord wordToAdd = new Word(word); //Initialise a new object of the IWord class with the word as the parameter.
            wordToAdd.addPronunciation(pronunciation); //Add the pronunciation to the word object.
            addWord(wordToAdd); //Add the new word to the dictionary.
        }
    }

    /**
     * Loads the CMU Pronouncing Dictionary from a file.
     *
     * This method parses every line in the file using the parseDictionaryLine method.
     *
     * @param fileName the file to be read
     * @throws IOException if the file is not found or cannot be opened
     */
    @Override
    public void loadDictionary(String fileName) {
        try (
                //Uses the Scanner class and file reader to read and easily parse file.
                Scanner infileDictionary = new Scanner(new FileReader(fileName + ".txt")); //Adds .txt to the end so file can be found.

        ) {
            while (infileDictionary.hasNextLine()) { //Iterates through every line in the file until it reaches the end.
                parseDictionaryLine(infileDictionary.nextLine()); //Parses the current line in the file.
            }
        } catch (IOException e) { //If an problem occurs an appropriate error message will be printed.
            System.err.println(e.getMessage());
        }
    }

    /**
     * Get all the rhymes for all pronunciations of a word
     *
     * Should throw an IllegalArgumentException if the word is not in the dictionary,
     * or is null.
     *
     * This will check all the pronunciations of every word in the dictionary against all the pronunciations of
     * a given word to see if they rhyme.
     *
     * @param word the word to get rhymes for
     * @return a set of words that rhyme with the given word
     */
    @Override
    public Set<String> getRhymes(String word) {
        Set<String> wordsThatRhyme = new HashSet<>(); //Initialises an empty set which will hold all the words that Rhyme with the given word.
        if(word == null){ //If the word passed in is null throw an exception
            throw new IllegalArgumentException();
        }
        IWord givenWord = getWord(word); //Gets the word object associated with the word passed in.
        if (givenWord == null){ //If the word passed in isn't a key in the dictionary throw an exception
            throw new IllegalArgumentException();
        }
        Set<IPronunciation> givenWordsPronunciations = givenWord.getPronunciations(); // Initialises a set that contains all the given words pronunciations.
        for(IWord fetchedWord: dictionary.values()){ //Goes through every word in the dictionary
            Set<IPronunciation> fetchedWordsPronunciations = fetchedWord.getPronunciations(); //Gets all the pronunciations for the fetched word.
            for(IPronunciation Gwp: givenWordsPronunciations){ //Goes through all the  given words pronunciations.
                for(IPronunciation Fwp: fetchedWordsPronunciations){ //Goes through all the fetched words pronunciations.
                    if(Gwp.rhymesWith(Fwp)){ //If the two pronunciations rhyme.
                        wordsThatRhyme.add(fetchedWord.getWord()); //Adds the current fetchedWord to the words that rhyme set.
                        break; //Once we have found one rhyme no point checking if other pronunciations do as well
                    }
                }
            }

        }
        return wordsThatRhyme; //Return the words that Rhyme in a set.
    }
}

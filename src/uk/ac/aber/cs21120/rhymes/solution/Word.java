package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.interfaces.IWord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * This class represents a word and the different ways it can be pronounced.
 *
 * The constructor creates a new word with no pronunciations and sets its English spelling.
 */
public class Word implements IWord {
    String word;
    Set<IPronunciation> wordsPronunciations;

    /**
     * Word constructor, sets the word value to the parameter given and initialises an empty set of the wordsPronunciations List.
     * wordPronunciations will hold all the possible pronunciations of a word.
     *
     * @param word A word in the Dictionary
     *
     * @throws IllegalArgumentException if word given is null then throws IllegalArgumentException.
     */
    public Word(String word) throws IllegalArgumentException{
        if(word ==null){
            throw new IllegalArgumentException();
        }
        this.word = word;
        wordsPronunciations = new HashSet<IPronunciation>();
    }

    /**
     * Returns the standard English spelling of the word, as it was created.
     * @return the word
     */
    @Override
    public String getWord() {
        return word;
    }
    /**
     * Adds a pronunciation to the word.
     * @throws IllegalArgumentException if the pronunciation is null
     * @param pronunciation the pronunciation to add
     */
    @Override
    public void addPronunciation(IPronunciation pronunciation) throws IllegalArgumentException {
        if(pronunciation == null ){
            throw new IllegalArgumentException();
        }
        wordsPronunciations.add(pronunciation);

    }

    /**
     * Returns all the possible pronunciations of the word.
     * @return the pronunciations
     */
    @Override
    public Set<IPronunciation> getPronunciations() {
        return  wordsPronunciations;
    }

}

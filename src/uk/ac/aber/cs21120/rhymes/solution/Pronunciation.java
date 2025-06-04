package uk.ac.aber.cs21120.rhymes.solution;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a pronunciation of a word in the CMU Pronouncing Dictionary.
 * Consists of a list of phonemes, which are themselves consisting
 * of Arpabet enum and optional stress values.
 */
public class Pronunciation implements IPronunciation {
    List<IPhoneme> pronunciationPhonemes;

    /**
     * Pronunciation constructor, initialises an empty List of type IPhoneme that will hold the
     * Phonemes correlating with the pronunciation.
     */
    public Pronunciation(){
        pronunciationPhonemes = new ArrayList<IPhoneme>();
    }

    /**
     * Add a phoneme to the end of the List pronunciationPhonemes.
     *
     * @throws IllegalArgumentException if the phoneme is null
     * @param phoneme the phoneme to add
     */
    @Override
    public void add(IPhoneme phoneme) throws IllegalArgumentException{
        if(phoneme == null){
            throw new IllegalArgumentException();
        }
        pronunciationPhonemes.add(phoneme);
    }
    /**
     * Return a list of phonemes in this pronunciation.
     * @return the list pronunciationPhonemes containing all phonemes in the pronunciation.
     */
    @Override
    public List<IPhoneme> getPhonemes() {
        return pronunciationPhonemes;
    }

    /**
     * Return the index of the final stressed vowel in the pronunciation.
     * This will be the vowel in the pronunciation with the highest stress
     * value. From highest to lowest the stress values can be 1,2, or 0.
     * If there are multiple vowels with the same highest stress value,
     * the index of the final one is returned.
     *
     * @return the index of the final stressed vowel in the pronunciation or -1 if no vowel is found
     *
     */
    @Override
    public int findFinalStressedVowelIndex() {
        IPhoneme finalStressedVowel = null;
        int finalStressedVowelStress = -1; //Holds the stress value of the last strongest stressed vowel in the pronunciation.
        int finalStressedVowelIndex = -1; //Holds the index of the last strongest stressed vowel in the pronunciation.

        for(int i = pronunciationPhonemes.size() -1 ;i >= 0;i--){ //Goes through all the Phonemes in the pronunciations starting at the end
            IPhoneme currentPhoneme = pronunciationPhonemes.get(i);
            if(currentPhoneme.getStress() == -1){ //Checks if the phoneme is a consonant and if it is goes to next phoneme
                ;
            } else if (currentPhoneme.getStress() == 1) { //Checks if the current vowel has primary stress
                return i;
            } else if (currentPhoneme.getStress() == 2 && finalStressedVowelStress != 2) { //Checks if the current vowel has secondary stress and if the last strongest stressed vowel doesn't have secondary stress
                finalStressedVowelStress = 2;
                finalStressedVowelIndex = i;
            }else if (currentPhoneme.getStress() == 0 && finalStressedVowelStress != 0){ //Checks if the current vowel is unstressed and if the last strongest stressed vowel wasn't unstressed
                finalStressedVowelStress = 0;
                finalStressedVowelIndex = i;
            }
        }
        return finalStressedVowelIndex;
    }
    /**
     * return true if this pronunciation rhymes with the other pronunciation.
     * This will be true if the pronunciations have the same phonemes from
     * the final stressed vowel onwards.
     *
     * @throws IllegalArgumentException if the other pronunciation is null
     *
     * @param other the other pronunciation to compare with
     * @return true if this pronunciation rhymes with the other pronunciation
     */
    @Override
    public boolean rhymesWith(IPronunciation other) throws  IllegalArgumentException{
        if(other == null){ //Checks if the other pronunciation is null
            throw new IllegalArgumentException();
        }
        int selfLastStressedVowelIndex = this.findFinalStressedVowelIndex(); //Sets the index of this pronunciations last strongest stressed vowel
        int otherLastStressedVowelIndex = other.findFinalStressedVowelIndex(); //sets the index of the 'other' pronunciation's last strongest stressed vowel

        int selfIndex = selfLastStressedVowelIndex; //Sets the self index tracker to the index of the last strongest stressed vowel of this pronunciations
        int otherIndex = otherLastStressedVowelIndex; //Sets the 'other' index tracker to the index of the last strongest stressed vowel of the 'other' pronunciations

        List<IPhoneme> otherPronunciationPhonemes = other.getPhonemes(); // Gets the phonemes that make up the other pronunciation

        if(selfLastStressedVowelIndex == -1 || otherLastStressedVowelIndex == -1 ){ //Returns false if either or both of the pronunciations don't have a vowel
            return false;
        }
        if(this.pronunciationPhonemes.size() - selfLastStressedVowelIndex == otherPronunciationPhonemes.size() - otherLastStressedVowelIndex){ //Checks that there's the same amount of phonemes after last strongest stressed vowel to reduce unnecessary comparisons.
            while (selfIndex < this.pronunciationPhonemes.size()){ // Keeps looping until the selfIndex reaches the end of the phoneme list.
                if(!pronunciationPhonemes.get(selfIndex).hasSameArpabet(otherPronunciationPhonemes.get(otherIndex))){ //Compare phonemes arpabets from the last strongest stressed vowel to find if they rhyme. If at any point the arpabets do not match the function will return false.
                    return false;
                }
                //Increment self and other index by +1 so index will be of the next Phoneme to check
                selfIndex++;
                otherIndex++;
            }
        }else{ //If amount of phonemes differ after the last strongest stressed vowel the words cannot rhyme and thus the function returns false.
            return false;
        }
        return true; // If all of the above is passed without returning false at any point then the two pronunciations rhyme
    }
}

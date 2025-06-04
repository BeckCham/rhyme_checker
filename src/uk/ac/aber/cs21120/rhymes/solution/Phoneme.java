package uk.ac.aber.cs21120.rhymes.solution;

import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;

/**
 * Represents one of the phonemes that are in a pronunciation.
 * Has a ARPABET value, and stress(If a vowel)
 */
public class Phoneme implements IPhoneme {
    Arpabet phoneme;
    int stress;
    /**
     * Phoneme constructor, sets the phoneme value, and sets the stress value after checking the parameters given are valid.
     *
     * @param phoneme A letter or pair of letters making a vowel or consonant sound.
     *
     * @param stress The identifier of how stressed a vowel phoneme is. If this is 1 it has primary stress,
     *               2 means secondary stress, and 0 signifies it is unstressed. The value of this variable
     *               will be -1 if the phoneme is not a vowel.
     *
     * @throws IllegalArgumentException if phoneme is null or if stress value is invalid. The stress value
     *              would be invalid if the phoneme wasn't a vowel and had a value other than -1 or if the
     *              phoneme was a vowel and had a value other than 1,2, or 0.
     */
    public Phoneme(Arpabet phoneme,int stress) throws IllegalArgumentException{
        if(phoneme == null){ //Checks if phoneme is null, if it is throws an exception.
            throw new IllegalArgumentException();
        }else{
            this.phoneme =phoneme;
        }

        if(phoneme.isVowel() && (stress == 0 ||stress == 1 || stress== 2)){ // Checks that if the phoneme given is a vowel and if it is that the Stress value is valid.
            this.stress = stress;
        }else if((!phoneme.isVowel()) && stress == -1){ //Checks that if the phoneme given is not a vowel that the stress value is valid
            this.stress = stress;
        }else{ //If either of the two if statements aren't true then the stress value is invalid and it throws an exception
            throw new IllegalArgumentException();
        }
    }
    /**
     * Returns the ARPABET phoneme.
     *
     * @return the phoneme.
     */
    @Override
    public Arpabet getArpabet(){
        return phoneme;
    }
    /**
     * Returns the optional stress value.
     * @return the stress value, or -1 if the phoneme is not a vowel.
     */
    @Override
    public int getStress() {
        return stress;
    }
    /**
     * Returns if the current phoneme has the same ARPABET value as the other phoneme. Stress is ignored.
     *
     * @return true if the ARPABET value is the same as in the other phoneme and false if it is not.
     *
     * @throws IllegalArgumentException if the other phoneme passed in is null.
     */
    @Override
    public boolean hasSameArpabet(IPhoneme other) throws IllegalArgumentException {
        if (other == null){
            throw new IllegalArgumentException();
        }
        return (other.getArpabet() == phoneme); //Checks if other phonemes ARPABET value is the same as this phonemes ARPABET value
    }

}

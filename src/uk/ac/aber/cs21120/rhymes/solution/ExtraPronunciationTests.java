package uk.ac.aber.cs21120.rhymes.solution;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Additional tests for the Pronunciation class to supplement those provided by
 * Aberystwyth University
 *
 * @author Beck Chamberlain
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtraPronunciationTests {
    /**
     * Tests that if no Phonemes are added to the pronunciation then findFinalStressedVowelIndex
     * returns -1
     *
     */
    @Test
    @Order(0)
    void testFindFinalStressedVowelIndex_EmptyPronunciation() {
        IPronunciation p = new Pronunciation();
        assertEquals(-1, p.findFinalStressedVowelIndex());
    }
    /**
     * Tests that in a word with multiple equally strongly stressed vowels that the
     * index of the last one is returned.
     *
     */
    @Test
    @Order(5)
    void testFindFinalStressedVowelIndex_MultipleStrongestStressed() {
        IPronunciation p = new Pronunciation();
        p.add(new Phoneme(Arpabet.S,-1));
        p.add(new Phoneme(Arpabet.AH,2));
        p.add(new Phoneme(Arpabet.K,-1));
        p.add(new Phoneme(Arpabet.EY,1));
        p.add(new Phoneme(Arpabet.AE,1));//Last strongest stressed vowel
        p.add(new Phoneme(Arpabet.HH,-1));
        p.add(new Phoneme(Arpabet.OY,0));
        assertEquals(4, p.findFinalStressedVowelIndex());
    }

}

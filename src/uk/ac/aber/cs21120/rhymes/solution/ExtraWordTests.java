package uk.ac.aber.cs21120.rhymes.solution;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.interfaces.IWord;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Word;
/**
 * Additional tests for the Word class to supplement those provided by
 * Aberystwyth University
 *
 * @author Beck Chamberlain
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtraWordTests {
    /**
     * Tests that creating a word with a null parameter throws an exception
     */
    @Test
    @Order(0)
    void testNullWord() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Word(null);
        });
    }
    /**
     * Test that the newly created Word object has no pronunciations.
     */
    @Test
    @Order(10)
    void testNoPhonemesInNewWord() {
        Word w = new Word("hello");
        assertEquals(0, w.getPronunciations().size());
    }

}

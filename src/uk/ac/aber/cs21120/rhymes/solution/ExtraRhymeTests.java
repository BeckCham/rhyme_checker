package uk.ac.aber.cs21120.rhymes.solution;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IDictionary;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.interfaces.IPronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Dictionary;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;
import uk.ac.aber.cs21120.rhymes.tests.DictionaryTests;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This class tests the rhyming parts of the assignment: Pronunciation.rhymesWith() first,
 * and then Dictionary.getRhymes() to supplement those provided by
 * Aberystwyth University
 *
 * @author Beck Chamberlain
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtraRhymeTests {
    //Tests for Pronunciation.rhymesWith()
    /**
     * Tests that calling rhymesWith() with a null parameter throws an exception
     */
    @Test
    @Order(0)
    void  testRhymesWith_NullPronunciation(){
        assertThrows(IllegalArgumentException.class, () -> {
            IPronunciation p1 = new Pronunciation();
            p1.add(new Phoneme(Arpabet.AA, 0));
            p1.rhymesWith(null);
        });
    }
    /**
     * Tests that calling rhymesWith() with a pronunciation with no phonemes returns false
     */
    @Test
    @Order(5)
    void  testRhymesWith_EmptyPronunciation(){
        IPronunciation p1 = new Pronunciation();
        p1.add(new Phoneme(Arpabet.AA, 0));
        IPronunciation p2 = new Pronunciation();
        assertFalse(p1.rhymesWith(p2));
    }
    //Tests for Dictionary.getRhymes()
    private static IDictionary dictionary = null;

    /**
     * Helper static method that loads the dictionary once, the first time we need it. After that
     * it just returns the dictionary we loaded before.
     * @return the dictionary, filled with words from the CMU dictionary.
     */
    private static IDictionary getDictionary(){
        if(dictionary == null){
            dictionary = new Dictionary();
            dictionary.loadDictionary(DictionaryTests.CMU_DICT);
            if(dictionary.getWordCount() == 0){
                dictionary = null; // so we get this exception for subsequent tests
                throw new IllegalStateException("Dictionary not loaded - perhaps Dictionary isn't yet implemented?");
            }
            // if either of these lines fail, then the dictionary hasn't been loaded correctly.
            // If they fail but testLoadDictionary in DictionaryTests passes, something very weird is happening.
            if(dictionary.getWordCount()!=126046)
                throw new IllegalStateException("Dictionary not loaded correctly - expected 126046 words, got "+dictionary.getWordCount());
            if(dictionary.getPronunciationCount()!=135155)
                throw new IllegalStateException("Dictionary not loaded correctly - expected 135155 pronunciations, got "+dictionary.getPronunciationCount());

        }
        return dictionary;
    }

    /**
     * Tests that calling getRhymes() with a word not in the CMU dictionary throws an exception
     */
    @Test
    @Order(10)
    void  testGetRhymes_InvalidWord(){
        assertThrows(IllegalArgumentException.class, () -> {
            Set<String> rhymes = getDictionary().getRhymes("sdhsdfu");
        });
    }

    /**
     * Tests that calling getRhymes() with a null parameter throws an exception
     */
    @Test
    @Order(15)
    void  testGetRhymes_NullWord(){
        assertThrows(IllegalArgumentException.class, () -> {
            Set<String> rhymes = getDictionary().getRhymes(null);
        });
    }
}

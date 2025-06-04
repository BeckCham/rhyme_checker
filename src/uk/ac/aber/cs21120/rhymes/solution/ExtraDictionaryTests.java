package uk.ac.aber.cs21120.rhymes.solution;
import org.junit.jupiter.api.*;
import uk.ac.aber.cs21120.rhymes.interfaces.*;
import uk.ac.aber.cs21120.rhymes.solution.Dictionary;
import uk.ac.aber.cs21120.rhymes.solution.Pronunciation;
import uk.ac.aber.cs21120.rhymes.solution.Word;
import uk.ac.aber.cs21120.rhymes.tests.PronunciationTests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtraDictionaryTests {
    /**
     * Test we can add a word with Multiple pronunciations without proper indexing
     */
    @Test
    @Order(0)
    void testParseDictionaryLineMultiplePronunciationsNoIndexing() {
        IDictionary d = new Dictionary();
        d.parseDictionaryLine("zumstein Z AH1 M S T IY2 N");
        d.parseDictionaryLine("zumstein Z AH1 M S T AY2 N");
        assertEquals(1, d.getWordCount());
        assertEquals(2, d.getPronunciationCount());

        IWord w = d.getWord("zumstein");
        assertEquals("zumstein", w.getWord());
        assertEquals(2, w.getPronunciations().size());
        List<String> strings = new ArrayList<>();
        for(IPronunciation p : w.getPronunciations()) {
            strings.add(PronunciationTests.pronunciationToString(p));
        }
        strings.sort(String::compareTo);
        assertEquals("Z AH1 M S T IY2 N", strings.get(0));
        assertEquals("Z AH1 M S T AY2 N", strings.get(1));
    }
    /**
     * Attempts to load a non-existing file, this should throw an IO Exception
     */
    @Test
    @Order(10)
    void testLoadDictionaryFromNonExistingFile() {
        IDictionary d = new Dictionary();
        assertThrows(IOException.class, () -> {
            d.loadDictionary("fakeDictionary");
        });
    }

}

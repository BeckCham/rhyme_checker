package uk.ac.aber.cs21120.rhymes.solution;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import uk.ac.aber.cs21120.rhymes.interfaces.Arpabet;
import uk.ac.aber.cs21120.rhymes.interfaces.IPhoneme;
import uk.ac.aber.cs21120.rhymes.solution.Phoneme;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExtraPhonemeTests {
    /**
     * Extra tests to Make sure that the constructor throws an exception if the stress is not -1
     * for any consonant. Extra test ensures that this is true for negative values too.
     */
    @Test
    @Order(0)
    void testStressOnConsonantException() {
        for(Arpabet a : Arpabet.values()) {
            // for every consonant...
            if(!a.isVowel()) {
                // ...make sure a stress which isn't -1 throws an exception
                assertThrows(IllegalArgumentException.class, () -> {
                    new Phoneme(a, -2);
                });
            }
        }
    }

    /**
     * Test that hasSamePhoneme returns true when the phonemes are the same
     * and false when they are different. Added additional test that checks that the
     * same phoneme different stress test works for all stress values.
     */
    @Test
    @Order(10)
    public void testHasSameArpabet() {
        IPhoneme p1 = new Phoneme(Arpabet.AH, 0);
        IPhoneme p2 = new Phoneme(Arpabet.AH, 1);
        assertTrue(p1.hasSameArpabet(p2));

    }
}

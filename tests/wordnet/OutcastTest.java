package wordnet;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by samuel on 25/03/16.
 */
public class OutcastTest {

    @Test
    public void testSimple(){
        WordNet wordnet = new WordNet("resources/wordnet/synsets.txt", "resources/wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        String s = outcast.outcast(new String[]{"probability","statistics","mathematics","physics"});
        Assert.assertEquals("probability",s);
    }
}
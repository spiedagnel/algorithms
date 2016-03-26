package wordnet;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by samuel on 25/03/16.
 */
public class WordNetTest {

    @Test
    public void testSimples(){
        WordNet wordnet = new WordNet("resources/wordnet/synsets15.txt", "resources/wordnet/hypernyms15Path.txt");
        Assert.assertEquals(3, wordnet.distance("a","d"));
    }

    @Test
    public void testWordnet2(){
        WordNet wordnet = new WordNet("resources/wordnet/synsets100-subgraph.txt", "resources/wordnet/hypernyms100-subgraph.txt");
        Assert.assertEquals(1, wordnet.distance("thing","stinker"));
    }

    @Test
    public void testWordnetCreation(){
        WordNet wordnet = new WordNet("resources/wordnet/synsets500-subgraph.txt", "resources/wordnet/hypernyms500-subgraph.txt");
        Assert.assertTrue(true);
    }

    @Test
    public void testWordnetRandom(){
        WordNet wordnet = new WordNet("resources/wordnet/synsets1000-subgraph.txt", "resources/wordnet/hypernyms1000-subgraph.txt");
        Assert.assertEquals(6, wordnet.distance("tract","arachnoid_membrane"));
        Assert.assertEquals("animal_tissue", wordnet.sap("tract","arachnoid_membrane"));
    }
}
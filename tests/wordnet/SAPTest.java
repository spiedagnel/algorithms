package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by samuel on 25/03/16.
 */
public class SAPTest {

    @Test
    public void testSimples(){
        In in = new In("resources/wordnet/digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assert.assertEquals(4,sap.length(3,11));
        Assert.assertEquals(1,sap.ancestor(3,11));

        Assert.assertEquals(3,sap.length(9,12));
        Assert.assertEquals(5,sap.ancestor(9,12));

        Assert.assertEquals(4,sap.length(7,2));
        Assert.assertEquals(0,sap.ancestor(7,2));

    }

    @Test
    public void testCirculars(){
        In in = new In("resources/wordnet/digraph3.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assert.assertEquals(5,sap.length(8,13));
    }

    @Test
    public void test6(){
        In in = new In("resources/wordnet/digraph6.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assert.assertEquals(5,sap.length(5,0));
    }

    @Test
    public void testWordNetDigrah(){
        In in = new In("resources/wordnet/digraph-wordnet.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        Assert.assertEquals(15,sap.length(2657,55738));
    }


}
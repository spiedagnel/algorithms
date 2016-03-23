package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;

/**
 * Created by samuel on 22/03/16.
 */
public class WordNet {

    HashMap<String,Integer> map = new HashMap<>();

    /**
     *  constructor takes the name of the two input files
      */

    public WordNet(String synsets, String hypernyms) {
        if(synsets == null || hypernyms == null){
            throw new NullPointerException();
        }
        In synsetsIn = new In(synsets);
        while(synsetsIn.hasNextLine()){
            String[] l = synsetsIn.readLine().split(",");
            int id = Integer.parseInt(l[0]);
            for (String syn : l[1].split(" ") ){
                map.put(syn,id);
            }

        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return map.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        return map.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
  //  public int distance(String nounA, String nounB)

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
  //  public String sap(String nounA, String nounB)

    // do unit testing of this class
  //  public static void main(String[] args)
}

package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by samuel on 22/03/16.
 */
public class WordNet {

    private HashMap<String,List<Integer>> map = new HashMap<>();
    private HashMap<Integer,String> synsetsMap = new HashMap<>();
    private SAP sap;

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
            synsetsMap.put(id,l[1]);
            for (String syn : l[1].split(" ") ){
                if(!map.containsKey(syn)){
                    map.put(syn, new ArrayList<>());
                }
                map.get(syn).add(id);
            }
        }

        In hypernymsIn = new In(hypernyms);
        Digraph dg = new Digraph(map.size());
        while(hypernymsIn.hasNextLine()){
            String[] l = hypernymsIn.readLine().split(",");
            int id = Integer.parseInt(l[0]);
            for (int i = 1; i< l.length; i++){
                dg.addEdge(id, Integer.parseInt(l[i]));
            }
        }
        sap = new SAP(dg);
        if(!validateRoots(dg))
            throw new IllegalArgumentException();
    }

    private boolean validateRoots(Digraph dg){
        int v = dg.V();
        int count = 0;
        for ( int i = 0; i < v; i++){
            if (dg.outdegree(i) == 0 && dg.indegree(i) !=0)
                count++;
        }
        return count == 1;
    }



    // returns all wordnet.WordNet nouns
    public Iterable<String> nouns(){
        return map.keySet();
    }

    // is the word a wordnet.WordNet noun?
    public boolean isNoun(String word){
        if(word == null)
            throw new NullPointerException();
        return map.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(nounA == null || nounB == null)
            throw new NullPointerException();
        if(!map.containsKey(nounA) || !map.containsKey(nounB))
            throw new IllegalArgumentException();
        return sap.length(map.get(nounA), map.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(nounA == null || nounB == null)
            throw new NullPointerException();
        if(!map.containsKey(nounA) || !map.containsKey(nounB))
            throw new IllegalArgumentException();
        return synsetsMap.get(sap.ancestor(map.get(nounA), map.get(nounB)));
    }

    // do unit testing of this class
  //  public static void main(String[] args)
}

package wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by samuel on 24/03/16.
 */
public class Outcast {
    private WordNet w;
    public Outcast(WordNet wordnet){
        this.w = wordnet;
    }

    public String outcast(String[] nouns){
        String outcast = null;
        int distanceMax = 0;
        for(String noun : nouns){
            int d = 0;
            for(String ni : nouns){
                d += w.distance(noun, ni);
            }
            if (d>=distanceMax){
                outcast = noun;
                distanceMax = d;
            }
        }
        return outcast;
    }

    public static void main(String[] args) {

            WordNet wordnet = new WordNet(args[0], args[1]);
            Outcast outcast = new Outcast(wordnet);
            for (int t = 2; t < args.length; t++) {
                In in = new In(args[t]);
                String[] nouns = in.readAllStrings();
                StdOut.println(args[t] + ": " + outcast.outcast(nouns));
            }
    }
}

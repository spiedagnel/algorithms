package seamCarving;

import edu.princeton.cs.algs4.Picture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

/**
 * Created by samuel on 04/04/16.
 */
public class SeamCarverTest {

    private Picture underTest;

    @Before
    public void init(){
        underTest = new Picture(3,4);
        underTest.set(0,0, new Color(255,101,51));
        underTest.set(0,1, new Color(255,153,51));
        underTest.set(0,2, new Color(255,203,51));
        underTest.set(0,3, new Color(255,255,51));

        underTest.set(1,0, new Color(255,101,153));
        underTest.set(1,1, new Color(255,153,153));
        underTest.set(1,2, new Color(255,204,153));
        underTest.set(1,3, new Color(255,255,153));

        underTest.set(2,0, new Color(255,101,255));
        underTest.set(2,1, new Color(255,153,255));
        underTest.set(2,2, new Color(255,205,255));
        underTest.set(2,3, new Color(255,255,255));
    }

    @Test
    public void testBordersEnergy(){
        SeamCarver sc = new SeamCarver(underTest);
        Assert.assertEquals(1000, sc.energy(0,0),0);
        Assert.assertEquals(1000, sc.energy(2,1),0);
        Assert.assertEquals(1000, sc.energy(1,0),0);
        Assert.assertEquals(1000, sc.energy(1,3),0);
    }

    @Test
    public void testCenterEnergy(){
        SeamCarver sc = new SeamCarver(underTest);
        Assert.assertEquals(Math.sqrt(52024), sc.energy(1,2),0);
        Assert.assertEquals(Math.sqrt(52225), sc.energy(1,1),0);

    }

}
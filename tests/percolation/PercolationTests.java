package percolation;

import org.junit.*;

/**
 * Created by samue_000 on 30/01/2016.
 */
public class PercolationTests {

    @Test
    public void testMatrixOfSize1PercolatesWhenSiteOpen() {
        Percolation p = new Percolation(1);
        p.open(1, 1);
        assert (p.percolates());
    }

    @Test
    public void testMatrixOfSize1DoesNotPercolatesWhenNoSiteOpen() {
        Percolation p = new Percolation(1);
        assert (!p.percolates());
    }

    @Test
    public void testMatrixOfSize2PercolatesWhen2VerticalSiteOpen() {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 1);
        assert (p.percolates());
    }

    @Test
    public void testMatrixOfSize2DoeNotPercolateWhen2HorizontalSiteOpen() {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(1, 2);
        assert (!p.percolates());
    }
    @Test
    public void testMatrixOfSize2DoeNotPercolateWhen2DiagSiteOpen() {
        Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 2);
        assert (!p.percolates());
    }
}

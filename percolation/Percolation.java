import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[] openSites;
    private int size;
    
    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();
        
        size = n;
        openSites = new boolean[size * size + 2];
        for (int i = 1; i <= size * size; i++)
            openSites[i] = false;
        
        openSites[0] = true;
        openSites[size * size + 1] = true;
        
        uf = new WeightedQuickUnionUF(size * size + 2);
    }
    
    public void open(int i, int j) {
        validate(i, j);
        
        final int idx = index(i, j);
        if (openSites[idx])
            return;

        int[] sites = new int[]{left(idx), right(idx), up(idx), down(idx)};
        
        for (int s = 0; s < sites.length; s++) {
            int site = sites[s];
            
            if (site < 0)
                continue;
            
            if (openSites[site])
                uf.union(idx, site);
        }
      
        openSites[idx] = true;
    }
    
    private int left(int idx) {
        if (idx % size == 1)
            return -1;
        
        return idx - 1;
    }
    
    private int right(int idx) {
        if (idx % size == 0)
            return -1;
        
        return idx + 1;
    }
    
    private int up(int idx) {
        if (idx <= size)
            return 0;
        
        return idx - size;
    }
    
     private int down(int idx) {
        if (idx > size * size - size)
            return size * size + 1;
        
        return idx + size;
    }
    
    public boolean isOpen(int i, int j) {
        validate(i, j);
        return openSites[index(i, j)];
    }
    
    public boolean isFull(int i, int j) {
        validate(i, j);
        return uf.connected(0, index(i, j));
    }
    
    public boolean percolates() {
        return uf.connected(0, size * size + 1);
    }
    
    private void validate(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size)
            throw new java.lang.IndexOutOfBoundsException();
    }
    
    private int index(int i, int j) {
        return size * (i - 1) + j;
    }
}

/**
 * 
 */
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * MemManager class
 * 
 * @author Solomon Manamel
 * @version 1
 */
public class MemManager {
    private byte[] bArray;
    private int maxSize;
    private ArrayList[] blocks;
    private int freeSpace;
    private boolean empty;

    /**
     * Constructer of the MemManager class
     * 
     * @param initMemSize
     *            The initial memory size
     */
    public MemManager(int initMemSize) {
        this.bArray = new byte[initMemSize];
        this.maxSize = initMemSize;
        int blockSize = (int)(Math.log(initMemSize) / Math.log(2));
        this.freeSpace = maxSize;
        this.blocks = new ArrayList[blockSize + 1];
        blocks[blockSize] = new ArrayList();
        Pair pair = new Pair(0, (int)Math.pow(2, blockSize));
        blocks[blockSize].add(pair); // CHANGE
        this.empty = true;
    }


    /**
     * Insert a record and return its position handle.
     * 
     * @param space
     *            item being inserted
     * @param size
     *            size of item
     * @param id
     *            key
     * @return memHandle
     */
    @SuppressWarnings("unchecked")
    public Handle insert(byte[] space, int size, int id) {
        empty = false;
        while (size > freeSpace) {
            expand();
        }
        int pos = findBlockIndex(size);
        if (blocks[pos] != null && !blocks[pos].isEmpty()) {
            Pair temp = (Pair)blocks[pos].remove(0);
            freeSpace -= temp.getLen();
            byte[] index = new byte[4];
            // System.arraycopy(space, 0, index, 0, 4);
            Handle newHandle = new Handle(temp.getPos(), size, id);
            // System.arraycopy(bArray, temp.getPos(), space, 0, size);
            System.arraycopy(space, 0, bArray, temp.getPos(), size);
            return newHandle;
        }
        else {
            int i = pos;
            if (pos < blocks.length - 1) {
                i++;
            }
            while ((blocks[i] == null || blocks[i].isEmpty())
                && i < blocks.length - 1) {
                i++;
            }
            Pair temp = null;
            while (blocks[i].isEmpty()) {
                expand();
            }
            while ((blocks[pos] == null || blocks[pos].isEmpty()) && i > 0) {
                temp = (Pair)blocks[i].remove(0);
                i--;
                if (blocks[i] == null) {
                    blocks[i] = new ArrayList();
                }
                Pair newPair1 = new Pair(temp.getPos(), temp.getLen() / 2);
                Pair newPair2 = new Pair(temp.getPos() + temp.getLen() / 2, temp
                    .getLen() / 2);

                blocks[i].add(newPair1);
                blocks[i].add(newPair2);
            }
            temp = (Pair)blocks[i].remove(0);
            freeSpace -= temp.getLen();
            Handle newHandle = new Handle(temp.getPos(), size, id);
            System.arraycopy(space, 0, bArray, temp.getPos(), size);
            return newHandle;
        }

    }


    /**
     * Expands the memory
     */
    private void expand() {
        byte[] b = new byte[maxSize * 2];
        System.arraycopy(bArray, 0, b, 0, maxSize);
        maxSize = maxSize * 2;
        bArray = b;

        ArrayList[] temp = blocks;
        int newSize = temp.length + 1;
        blocks = new ArrayList[newSize];
        System.arraycopy(temp, 0, blocks, 0, newSize - 1);

        blocks[newSize - 2] = new ArrayList();
        blocks[newSize - 1] = new ArrayList();

        if (freeSpace == maxSize / 2) {
            Pair pair = new Pair(0, (int)Math.pow(2, newSize - 1));
            blocks[newSize - 1].add(pair); // CHANGE
        }
        else {
            Pair pair = new Pair((int)Math.pow(2, newSize - 2), (int)Math.pow(2,
                newSize - 2));
            blocks[newSize - 2].add(pair); // CHANGE
            blocks[newSize - 2].sort(null);
        }
        freeSpace += (maxSize / 2);
        System.out.printf("Memory pool expanded to %d bytes\n", (int)Math.pow(2,
            newSize - 1));
    }


    /**
     * Gets the free space
     * 
     * @return freeSpace
     */
    public int getFreeSpace() {
        return freeSpace;
    }


    /**
     * Gets used space
     * 
     * @return usedSpace
     */
    public int getUsedSpace() {
        return maxSize - freeSpace;
    }


    /**
     * Finds the block size needed to store based on size
     * 
     * @param size
     *            of item
     * @return index
     */
    public int findBlockIndex(int size) {
        int blockSizeRequest = 0;
        while (size > Math.pow(2, blockSizeRequest)) {
            blockSizeRequest++;
        }
        return blockSizeRequest;
    }


    /**
     * Free a block at the position specified by theHandle. Merge adjacent free
     * blocks.
     * 
     * @param theHandle
     *            Handle to remove
     */
    public void remove(Handle theHandle) {
        byte[] b = new byte[theHandle.getLen()];
        System.arraycopy(b, 0, bArray, theHandle.getPos(), theHandle.getLen());
        int pow = findBlockIndex(theHandle.getLen());
        freeSpace += Math.pow(2, pow);

        boolean merge = true;
        Handle mergeHandle = theHandle;
        int newPos = theHandle.getPos();
        while (pow < blocks.length && merge) {
            ArrayList list = blocks[pow];
            Pair np = new Pair(newPos, (int)Math.pow(2, pow));
            list.add(np);
            list.sort(null);

            Iterator iter = list.iterator();
            int count = 0;
            while (iter.hasNext()) {
                Pair p = (Pair)iter.next();
                if (np.checkBuddy(p) && !p.equals(np)) {
                    list.remove(count);
                    if (p.pos > np.pos) {
                        list.remove(count - 1);
                        newPos = np.pos;
                    }
                    else {
                        list.remove(count);
                        newPos = p.pos;
                    }
                    merge = true;
                    break;
                }
                count++;
                merge = false;
            }
            pow++;
        }

    }


    /**
     * Return the record with handle posHandle, up to size bytes, by copying it
     * into
     * space.
     * 
     * @param space
     *            array for the item to be copied to
     * @param theHandle
     *            Handle to get the item
     * @param size
     *            size of item
     * @return size
     */
    public int get(byte[] space, Handle theHandle, int size) {
        System.arraycopy(bArray, theHandle.getPos(), space, 0, size);
        return size;
    }


    /**
     * Dump a printout of the freeblock list
     * 
     * @return toString
     */
    public String toString() {
        if (freeSpace > 0) {
            String s = "";

            for (int i = 0; i < blocks.length; i++) {
                if (blocks[i] != null && !blocks[i].isEmpty()) {
                    s += (String.format("%d", (int)Math.pow(2, i)));
                    Iterator iter = blocks[i].iterator();
                    while (iter.hasNext()) {
                        s += (String.format(" %d", ((Pair)iter.next())
                            .getPos()));
                    }
                    s += "\n";
                }
            }
            return s;
        }
        else {
            return "There are no freeblocks in the memory pool\n";
        }
    }


    /**
     * Gets the size
     * 
     * @return size
     */
    public int getSize() {
        return blocks.length;
    }

    private class Pair implements Comparable {
        private int pos;
        private int len;

        /**
         * Constructer for the pair class
         * 
         * @param position
         *            pos
         * @param length
         *            len
         */
        public Pair(int position, int length) {
            this.pos = position;
            this.len = length;
        }


        /**
         * Gets the position
         * 
         * @return pos
         */
        public int getPos() {
            return pos;
        }


        /**
         * Gets the length
         * 
         * @return len
         */
        public int getLen() {
            return len;
        }


        /**
         * Compares this to object o
         * 
         * @param o
         *            object
         */
        @Override
        public int compareTo(Object o) {
            Pair p = (Pair)o;
            if (p.getPos() < pos) {
                return 1;
            }
            return -1;
        }


        /**
         * checks another pair to see if it is a buddy
         * 
         * @param p
         *            possible buddy
         * @return bool
         */
        public boolean checkBuddy(Pair p) {
            return (pos | len) == (p.pos | p.len);
        }
    }
}

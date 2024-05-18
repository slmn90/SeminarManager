/**
 * 
 */

/**
 * @author solom
 * @version 1
 */
public class Hash {
    private static final int MAX_STEPS = 999999;
    private Handle[] handles;
    private int size;
    private Handle tombstone;
    private int length;

    /**
     * Constructor for the Hash class
     * 
     * @param initHashSize
     *            Initial size of hash table
     */
    public Hash(int initHashSize) {
        this.size = initHashSize;
        handles = new Handle[size];
        this.tombstone = new Handle(-1, -1, -1);
        this.length = 0;
    }


    /**
     * Inserts a handle into the table with id as a key
     * 
     * @param id
     *            key
     * @param handle
     *            Handle being inserted
     * @return index
     * @throws Exception
     */
    public int insert(int id, Handle handle) throws Exception {
        if (length >= size / 2) {
            expand();
        }
        int index = hashOne(id);
        if (collision(index)) {
            int step = hashTwo(id);
            int count = 0;
            while (collision(index) && count < MAX_STEPS) {
                index = (index + step) % size;
                count++;
            }
        }
        handles[index] = handle;
        length++;
        return index;
    }


    /**
     * First hash function
     * 
     * @param k
     *            id
     * @return index
     */
    private int hashOne(int k) {
        return k % size;
    }


    /**
     * Second hash function
     * 
     * @param k
     * @return step
     */
    private int hashTwo(int k) {
        return (((k / size) % (size / 2)) * 2) + 1;
    }


    /**
     * Checks for a collision
     * 
     * @param k
     *            id
     * @return bool
     */
    private boolean collision(int k) {
        return !(handles[k] == null || handles[k].equals(tombstone));
    }


    /**
     * Returns the amount of items in the hash table
     * 
     * @return length
     */
    public int length() {
        return length;
    }


    /**
     * Gets the size of the hash table
     * 
     * @return size
     */
    public int size() {
        return size;
    }


    /**
     * Deletes an element from the hash table
     * 
     * @param id
     *            element to be deleted
     * @return Handle
     */
    public Handle delete(int id) {
        Handle deleted = null;
        int index = hashOne(id);
        if (handles[index].getID() == id) {
            deleted = handles[index];
            handles[index] = tombstone;
        }
        else {
            int step = hashTwo(id);
            int count = 0;
            while (handles[index] != null && count < MAX_STEPS) {
                if (handles[index].getID() == id) {
                    deleted = handles[index];
                    handles[index] = tombstone;
                    break;
                }
                index = (index + step) % size;
                count++;
            }
        }
        length--;
        return deleted;
    }


    /**
     * Expands the hash table
     * 
     * @throws Exception
     */
    private void expand() throws Exception {

        // System.out.printf("YEAHHH\n");
        size = 2 * size;
        length = 0;
        Handle[] temp = handles;
        Handle[] newHandles = new Handle[size];
        handles = newHandles;
        for (int i = 0; i < size / 2; i++) {
            if (temp[i] != null) {
                if (!temp[i].equals(tombstone)) {
                    insert(temp[i].getID(), temp[i]);
                }
            }
        }
        System.out.printf("Hash table expanded to %d records\n", size);
    }


    /**
     * Searches the hash table for handle with the corresponding id
     * 
     * @param id
     *            key
     * @return Handle
     */
    public Handle search(int id) {
        Handle handle = null;
        int index = hashOne(id);
        int count = 0;
        while (handles[index] != null && handles[index].getID() != id
            && count < MAX_STEPS) {
            int step = hashTwo(id);
            count++;
            index = (index + step) % size;
        }
        handle = handles[index];
        return handle;
    }


    /**
     * Represents the hashTable as a string
     * 
     * @return toString
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < size; i++) {
            if (handles[i] != null) {
                if (handles[i] == tombstone) {
                    s += String.format("%d: TOMBSTONE\n", i);
                }
                else {
                    s += String.format("%d: %d\n", i, handles[i].getID());
                }
            }
        }
        return s;
    }

}

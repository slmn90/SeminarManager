/**
 * 
 */

/**
 * Handle class
 * 
 * @author Solomon Manamel
 * @version 1
 */
public class Handle {
    private int pos;
    private int len;
    private int id;

    /**
     * Constructer for the handle class
     * 
     * @param position
     *            pos in the byte array
     * @param length
     *            len of item
     * @param id
     *            key
     * 
     */
    public Handle(int position, int length, int id) {
        this.pos = position;
        this.len = length;
        this.id = id;
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
     * Gets the id
     * 
     * @return id
     */
    public int getID() {
        return id;
    }


    /**
     * Represents the handle as a string
     * 
     * @return string
     */
    public String toString() {
        return String.format("%s\n", id);
    }
}

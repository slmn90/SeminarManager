import java.io.IOException;

/**
 * Handle the Seminar database. This class processes commands by manipulating
 * the hash table.
 *
 * @author Solomon
 * @version Manamel
 */
public class SeminarDB {
    private MemManager myMemman; // Implement Memory Manager class in a separate
                                 // file
    private Hash myHashTable; // Implement Hash table class in a separate file

    /**
     * Create a new SeminarDB object.
     *
     * @param initMemSize
     *            Initial ize for memory pool
     * @param initHashSize
     *            Initial size for hash tables
     * @throws IOException
     */
    public SeminarDB(int initMemSize, int initHashSize) throws IOException {
        myMemman = new MemManager(initMemSize);
        myHashTable = new Hash(initHashSize);
    }


    // ----------------------------------------------------------
    /**
     * Process insert command, which requires a lot of parsing!
     * 
     * @param sID
     *            ID value
     * @param stitle
     *            title
     * @param sdate
     *            date
     * @param slength
     *            length
     * @param sx
     *            x
     * @param sy
     *            y
     * @param scost
     *            cost
     * @param skeywords
     *            keywords
     * @param sdesc
     *            description
     * @throws Exception
     */
    public void insert(
        int sID,
        String stitle,
        String sdate,
        int slength,
        int sx,
        int sy,
        int scost,
        String[] skeywords,
        String sdesc)
        throws Exception {

        if (sID == 8) {
            int i = 1;
        }

        Seminar sem = new Seminar(sID, stitle, sdate, slength, (short)sx,
            (short)sy, scost, skeywords, sdesc);
        byte[] b = sem.serialize();

        if (myHashTable.search(sID) == null) {
            Handle handle = myMemman.insert(b, b.length, sID);
            myHashTable.insert(sID, handle);
            System.out.printf("Successfully inserted record with id %d\n", sID);
            System.out.printf(sem.toString());
            System.out.printf("\nSize: %d\n", handle.getLen());
        }
        else {
            System.out.printf(
                "insert failed there is already a record with id %d\n", sID);
        }
    }


    // ----------------------------------------------------------
    /**
     * Delete the record with the given key
     * 
     * @param sID
     *            The key to find and remove
     * @throws IOException
     */
    public void delete(int sID) throws IOException {

        if (sID == 152) {
            int i = 1;
        }

        if (myHashTable.search(sID) == null) {
            System.out.printf("Delete failed there is no record with id %d\n",
                sID);
        }
        else {
            Handle handle = myHashTable.delete(sID);
            myMemman.remove(handle);
            System.out.printf(
                "record with id %d successfully deleted from the database\n",
                sID);
        }

    }


    // ----------------------------------------------------------
    /**
     * Find and return the record that matches the given key
     * 
     * @param sID
     *            The key to search for
     * @throws IOException
     * @throws Exception
     */
    public void search(int sID) throws IOException, Exception {
        Handle h = myHashTable.search(sID);
        if (h == null) {
            System.out.printf("Search failed there is no record with id %d\n",
                sID);
        }
        else {
            System.out.printf("Found record with id %d\n", sID);
            byte[] b = new byte[h.getLen()];
            myMemman.get(b, h, h.getLen());
            Seminar sem = new Seminar();
            sem = sem.deserialize(b);
            System.out.printf(sem.toString());
            System.out.printf("\n");
        }
    }


    // ----------------------------------------------------------
    /**
     * Print the hash table
     * 
     * @return Number of records in table
     * @throws IOException
     */
    public int hashprint() throws IOException {
        System.out.printf("Hashtable\n");
        System.out.print(myHashTable.toString());
        // System.out.printf("Total records %d\n", myHashTable.length());
        return myHashTable.length();
    }


    // ----------------------------------------------------------
    /**
     * Print the memory manager freeblock list
     */
    public void memmanprint() {
        System.out.printf("Freeblock List\n");
        System.out.print(myMemman.toString());
        // System.out.printf("Total records %d\n", myHashTable.length());
    }
}

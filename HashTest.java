
/**
 * 
 */

import student.TestCase;

/**
 * Testing hash class
 * 
 * @author solom
 * @version 1
 * 
 */
public class HashTest extends TestCase {

    /**
     * Tests insert
     * 
     * @throws Exception
     */
    public void testInsert() throws Exception {
        Hash hash = new Hash(16);
        Handle h = new Handle(1, 1, 1);
        Handle h39 = new Handle(39, 39, 39);
        assertEquals(h39.toString(), "39\n");
        assertEquals(hash.insert(55, h), 7);
        assertEquals(hash.insert(39, h39), 12);
        assertEquals(hash.insert(92, h), 2);
        assertEquals(hash.insert(93, h), 13);
        assertEquals(hash.insert(94, h), 14);
        assertEquals(hash.insert(95, h), 15);
        assertEquals(hash.insert(96, h), 0);
        // assertEquals(hash.insert(97, h), 1);
        // assertEquals(hash.toString(), "");

        assertEquals(hash.delete(39), h39);

        assertEquals(hash.insert(39, h39), 12);

        assertEquals(hash.delete(39), h39);

        Handle h28 = new Handle(28, 28, 28);
        assertEquals(hash.insert(28, h28), 12);

        assertEquals(hash.insert(39, h39), 1);
        assertEquals(hash.insert(97, h), 8);
    }


    /**
     * Tests delete
     * 
     * @throws Exception
     */
    public void testDelete() throws Exception {
        Hash hash = new Hash(16);
        assertEquals(hash.size(), 16);
        Handle h = new Handle(1, 1, 1);
        Handle h39 = new Handle(39, 39, 39);
        Handle h55 = new Handle(39, 39, 55);
        Handle h92 = new Handle(39, 39, 92);
        Handle h94 = new Handle(39, 39, 94);
        Handle h96 = new Handle(39, 39, 96);
        Handle h95 = new Handle(39, 39, 95);
        Handle h93 = new Handle(39, 39, 93);

        assertEquals(hash.insert(55, h55), 7);
        assertEquals(hash.insert(39, h39), 12);
        assertEquals(hash.insert(92, h92), 2);
        assertEquals(hash.insert(93, h93), 13);
        assertEquals(hash.insert(94, h94), 14);
        assertEquals(hash.insert(95, h95), 15);
        assertEquals(hash.insert(96, h96), 0);

        assertEquals(hash.delete(39), h39);
        assertEquals(hash.delete(39), null);
        assertEquals(hash.delete(96), h96);
        assertEquals(hash.delete(94), h94);
        assertEquals(hash.delete(93), h93);
        assertEquals(hash.delete(95), h95);
        assertEquals(hash.delete(92), h92);
        assertEquals(hash.delete(55), h55);

        assertEquals(hash.insert(97, h), 1);

    }


    /**
     * Tests search
     * 
     * @throws Exception
     */
    public void testSearch() throws Exception {
        Hash hash = new Hash(16);
        Handle h = new Handle(1, 1, 1);
        Handle h39 = new Handle(39, 39, 39);
        int n = 17;
        Handle h17 = new Handle(n, n, n);
        n = 4;
        Handle h1 = new Handle(n, n, n);
        n = 65;
        Handle h2 = new Handle(n, n, n);
        n = 2;
        Handle h3 = new Handle(n, n, n);
        n = 201;
        Handle h4 = new Handle(n, n, n);
        n = 33;
        Handle h5 = new Handle(n, n, n);
        n = 256;
        Handle h6 = new Handle(n, n, n);
        n = 16;
        Handle h7 = new Handle(n, n, n);
        assertEquals(hash.search(0), null);
        assertEquals(hash.insert(h.getID(), h), 1);
        assertEquals(hash.insert(39, h39), 7);
        assertEquals(hash.insert(17, h17), 4);

        assertEquals(hash.search(39), h39);
        assertEquals(hash.search(1), h);
        assertEquals(hash.search(17), h17);

        hash.delete(1);
        assertEquals(hash.search(17), h17);
        assertEquals(hash.search(1), null);

        assertEquals(hash.insert(h1.getID(), h1), 5);
        assertEquals(hash.insert(h2.getID(), h2), 1);
        assertEquals(hash.insert(h3.getID(), h3), 2);
        assertEquals(hash.insert(h4.getID(), h4), 9);
        assertEquals(hash.insert(h5.getID(), h5), 6);
        assertEquals(hash.insert(h6.getID(), h6), 0);
        assertEquals(hash.insert(h7.getID(), h7), 16);

        assertEquals(hash.search(4), h1);
        assertEquals(hash.search(65), h2);
        assertEquals(hash.search(2), h3);
        assertEquals(hash.search(201), h4);
        assertEquals(hash.search(33), h5);
        assertEquals(hash.search(256), h6);
        assertEquals(hash.search(16), h7);
    }


    /**
     * Tests search again
     * 
     * @throws Exception
     */
    public void testSearchTwo() throws Exception {
        Hash hash = new Hash(16);
        Handle h = new Handle(1, 1, 1);
        Handle h39 = new Handle(39, 39, 39);
        Handle h55 = new Handle(39, 39, 55);
        Handle h92 = new Handle(39, 39, 92);
        Handle h94 = new Handle(39, 39, 94);
        Handle h96 = new Handle(39, 39, 96);
        Handle h95 = new Handle(39, 39, 95);
        Handle h93 = new Handle(39, 39, 93);
        Handle h97 = new Handle(39, 39, 97);
        Handle h33 = new Handle(39, 39, 33);
        Handle h65 = new Handle(39, 39, 65);
        Handle h129 = new Handle(39, 39, 129);
        Handle h19 = new Handle(39, 39, 19);
        Handle h10 = new Handle(39, 39, 10);

        assertEquals(hash.insert(55, h55), 7);
        assertEquals(hash.insert(39, h39), 12);
        assertEquals(hash.insert(92, h92), 2);
        assertEquals(hash.insert(93, h93), 13);
        assertEquals(hash.insert(94, h94), 14);
        assertEquals(hash.insert(95, h95), 15);
        assertEquals(hash.insert(96, h96), 0);

        assertEquals(hash.search(39), h39);
        assertEquals(hash.search(55), h55);
        assertEquals(hash.search(92), h92);
        assertEquals(hash.search(93), h93);
        assertEquals(hash.search(94), h94);
        assertEquals(hash.search(95), h95);
        assertEquals(hash.search(96), h96);
        hash.delete(95);

        assertEquals(hash.insert(97, h97), 1);
        assertEquals(hash.insert(33, h33), 6);
        assertEquals(hash.insert(65, h65), 6);
        // assertEquals(hash.hashTwo(129), 10);
        assertEquals(hash.insert(10, h10), 10);
        assertEquals(hash.insert(19, h19), 19);
        assertEquals(hash.insert(129, h129), 5);

        assertEquals(hash.search(39), h39);
        assertEquals(hash.search(55), h55);
        assertEquals(hash.search(92), h92);
        assertEquals(hash.search(93), h93);
        assertEquals(hash.search(94), h94);
        assertEquals(hash.search(95), null);
        assertEquals(hash.search(96), h96);
        assertEquals(hash.search(97), h97);
        assertEquals(hash.search(33), h33);
        assertEquals(hash.search(65), h65);
        assertEquals(hash.search(129), h129);
        assertEquals(hash.search(10), h10);
        assertEquals(hash.search(19), h19);
        assertEquals(hash.search(10023), null);
        assertEquals(hash.search(231), null);
        assertEquals(hash.search(52352), null);
        assertEquals(hash.search(112312), null);
        assertEquals(hash.search(95), null);
        // assertEquals(hash.toString(), "");
    }
}

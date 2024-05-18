
/**
 * 
 */

import student.TestCase;

/**
 * @author solom
 * @version 1
 */
public class MemManagerTest extends TestCase {

    /**
     * Tests find block
     */
    public void testFindBlock() {
        MemManager memMan = new MemManager(64);
        assertEquals(memMan.getSize(), 7);
        assertEquals(memMan.findBlockIndex(1), 0);
        assertEquals(memMan.findBlockIndex(2), 1);
        assertEquals(memMan.findBlockIndex(3), 2);
        assertEquals(memMan.findBlockIndex(4), 2);
        assertEquals(memMan.findBlockIndex(5), 3);
        assertEquals(memMan.findBlockIndex(8), 3);
        assertEquals(memMan.findBlockIndex(9), 4);
        assertEquals(memMan.findBlockIndex(16), 4);
        assertEquals(memMan.findBlockIndex(17), 5);
        assertEquals(memMan.findBlockIndex(32), 5);
        assertEquals(memMan.findBlockIndex(33), 6);
        assertEquals(memMan.findBlockIndex(64), 6);
    }


    /**
     * Tests insert
     */
    public void testInsert() {
        MemManager memMan = new MemManager(256);
        assertEquals(memMan.getFreeSpace(), 256);
        assertEquals(memMan.getUsedSpace(), 0);
        byte[] b = new byte[256];
        Handle handle = memMan.insert(b, 3, 1);
        assertEquals(handle.getPos(), 0);
        assertEquals(handle.getLen(), 3);
        assertEquals(memMan.getFreeSpace(), 252);
        assertEquals(memMan.getUsedSpace(), 4);
        handle = memMan.insert(b, 2, 2);
        assertEquals(handle.getPos(), 4);
        assertEquals(handle.getLen(), 2);
        assertEquals(memMan.getFreeSpace(), 250);
        assertEquals(memMan.getUsedSpace(), 6);
        handle = memMan.insert(b, 10, 2);
        assertEquals(handle.getPos(), 16);
        assertEquals(handle.getLen(), 10);
        assertEquals(memMan.getFreeSpace(), 234);
        assertEquals(memMan.getUsedSpace(), 22);
    }


    /**
     * Tests remove
     */
    public void testRemove() {
        MemManager memMan = new MemManager(256);
        assertEquals(memMan.getFreeSpace(), 256);
        assertEquals(memMan.getUsedSpace(), 0);
        byte[] b = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        byte[] c = { 11, 12 };
        Handle handle1 = memMan.insert(b, 10, 1);
        // Handle handle2 = memMan.insert(c, 2, 2);
        // Handle handle3 = memMan.insert(b, 10, 3);
        assertEquals(memMan.getFreeSpace(), 240);
        assertEquals(memMan.getUsedSpace(), 16);
        memMan.remove(handle1);
        assertEquals(memMan.getFreeSpace(), 256);
        assertEquals(memMan.getUsedSpace(), 0);
    }


    /**
     * Tests expand
     */
    public void testExpandOne() {
        MemManager memMan = new MemManager(256);
        byte[] a = new byte[7];
        byte[] b = new byte[54];
        byte[] c = new byte[24];
        byte[] d = new byte[45];
        byte[] e = new byte[2];
        byte[] f = new byte[7];
        byte[] g = new byte[30];
        byte[] h = new byte[30];
        byte[] i = new byte[120];
        Handle h1 = memMan.insert(a, 7, 1);
        Handle h2 = memMan.insert(b, 54, 2);
        Handle h3 = memMan.insert(c, 24, 3);
        Handle h4 = memMan.insert(d, 45, 4);
        Handle h5 = memMan.insert(e, 2, 5);
        Handle h6 = memMan.insert(f, 7, 6);

        memMan.remove(h2);

        Handle h7 = memMan.insert(g, 30, 7);
        Handle h8 = memMan.insert(h, 30, 8);

        memMan.remove(h7);
        assertEquals(memMan.getFreeSpace(), 110);
        assertEquals(memMan.getUsedSpace(), 146);

        Handle h9 = memMan.insert(i, 120, 9);
        assertEquals(memMan.getFreeSpace(), 238);
        assertEquals(memMan.getUsedSpace(), 274);
    }


    /**
     * Tests expand again
     */
    public void testExpandTwo() {
        MemManager memMan = new MemManager(256);
        byte[] a = new byte[7];
        byte[] b = new byte[54];
        byte[] c = new byte[24];
        byte[] d = new byte[45];
        byte[] e = new byte[2];
        byte[] f = new byte[7];
        byte[] g = new byte[30];
        byte[] h = new byte[30];
        byte[] i = new byte[16];
        byte[] j = new byte[32];
        byte[] k = new byte[64];
        Handle h1 = memMan.insert(a, 7, 1);
        Handle h2 = memMan.insert(b, 54, 2);
        Handle h3 = memMan.insert(c, 24, 3);
        Handle h4 = memMan.insert(d, 45, 4);
        Handle h5 = memMan.insert(e, 2, 5);
        Handle h6 = memMan.insert(f, 7, 6);

        memMan.remove(h2);

        Handle h7 = memMan.insert(g, 30, 7);
        Handle h8 = memMan.insert(h, 30, 8);

        memMan.remove(h7);
        assertEquals(memMan.getFreeSpace(), 110);
        assertEquals(memMan.getUsedSpace(), 146);

        Handle h9 = memMan.insert(i, 16, 9);
        Handle h10 = memMan.insert(j, 32, 10);
        assertEquals(memMan.getFreeSpace(), 62);
        assertEquals(memMan.getUsedSpace(), 194);
        memMan.remove(h5);

        Handle h11 = memMan.insert(k, 64, 11);
        assertEquals(memMan.getFreeSpace(), 256);
        assertEquals(memMan.getUsedSpace(), 256);
        assertEquals(h11.getPos(), 256);
    }


    /**
     * Tests expand one last time
     */
    public void testExpandThree() {
        MemManager memMan = new MemManager(256);
        byte[] a = new byte[7];
        byte[] b = new byte[54];
        byte[] c = new byte[24];
        byte[] d = new byte[45];
        byte[] e = new byte[2];
        byte[] f = new byte[7];
        byte[] g = new byte[30];
        byte[] h = new byte[30];
        byte[] i = new byte[16];
        byte[] j = new byte[32];
        byte[] k = new byte[512];
        Handle h1 = memMan.insert(a, 7, 1);
        Handle h2 = memMan.insert(b, 54, 2);
        Handle h3 = memMan.insert(c, 24, 3);
        Handle h4 = memMan.insert(d, 45, 4);
        Handle h5 = memMan.insert(e, 2, 5);
        Handle h6 = memMan.insert(f, 7, 6);

        memMan.remove(h2);

        Handle h7 = memMan.insert(g, 30, 7);
        Handle h8 = memMan.insert(h, 30, 8);

        memMan.remove(h7);
        assertEquals(memMan.getFreeSpace(), 110);
        assertEquals(memMan.getUsedSpace(), 146);

        Handle h9 = memMan.insert(i, 16, 9);
        Handle h10 = memMan.insert(j, 32, 10);
        assertEquals(memMan.getFreeSpace(), 62);
        assertEquals(memMan.getUsedSpace(), 194);
        memMan.remove(h5);

        Handle h11 = memMan.insert(k, 512, 11);
        assertEquals(h11.getPos(), 512);
        assertEquals(memMan.getFreeSpace(), 320);
        assertEquals(memMan.getUsedSpace(), 704);
    }
    
    /**
     * Tests merge/split 
     */
    public void testMergeSplit() {
        MemManager memMan = new MemManager(256);
        byte[] a = new byte[512];
        Handle h1 = memMan.insert(a, 512, 1);
        assertEquals(h1.getPos(), 0);
    }
}

package Tests;

import library.entities.Book;
import org.junit.*;
import java.lang.reflect.Constructor;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<Book> c = Book.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testBuild(){
        Book b = Book.build("A","B","1");
        assertEquals("A",b.getTitle());
    }

    @Test
    public void testFormat(){
        Book b = new Book("T","A","2");
        b.setAvailable(false);
        String f = b.format();
        assertEquals("T,A,2,false",f);
    }

    @Test
    public void testParse(){
        Book b = Book.parse("X,Y,3,true");
        assertEquals("X",b.getTitle());
        assertTrue(b.isAvailable());
    }

    @Test
    public void testCopy(){
        Book b = new Book("A","B","10");
        b.setAvailable(false);
        Book c = b.copy();
        assertEquals(b.getISBN(),c.getISBN());
        assertFalse(c.isAvailable());
    }

    @Test
    public void testToggle(){
        Book b = new Book("M","N","5");
        b.toggleAvailable();
        assertFalse(b.isAvailable());
    }

    @Test
    public void testSetters(){
        Book b = new Book("1","2","3");
        b.setTitle("X");
        b.setAuthor("Y");
        b.setISBN("Z");
        assertEquals("X",b.getTitle());
        assertEquals("Y",b.getAuthor());
        assertEquals("Z",b.getISBN());
    }

    @Test
    public void testEquals(){
        Book a = new Book("A","B","100");
        Book b = new Book("X","Y","100");
        assertEquals(a,b);
    }

    @Test
    public void testHashCode(){
        Book a = new Book("A","B","200");
        assertEquals("200".hashCode(),a.hashCode());
    }
}

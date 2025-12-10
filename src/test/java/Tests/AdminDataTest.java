package Tests;

import library.data.AdminData;
import library.entities.Admin;
import org.junit.*;
import java.lang.reflect.Constructor;
import static org.junit.Assert.*;

public class AdminDataTest {

    @Before
    public void setup(){
        AdminData.clear();
        AdminData.addAdmin(new Admin("admin1","pass123"));
        AdminData.addAdmin(new Admin("admin2","adminpass"));
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor<AdminData> c = AdminData.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        assertTrue(true);
    }

    @Test
    public void testSize(){
        assertEquals(2, AdminData.size());
    }

    @Test
    public void testExists(){
        assertTrue(AdminData.exists("admin1"));
    }

    @Test
    public void testNotExists(){
        assertFalse(AdminData.exists("xxx"));
    }

    @Test
    public void testGetAdmin(){
        Admin a = AdminData.getAdminByUsername("admin1");
        assertEquals("admin1",a.getUsername());
    }

    @Test
    public void testAdd(){
        AdminData.addAdmin(new Admin("new","123"));
        assertTrue(AdminData.exists("new"));
    }

    @Test
    public void testClear(){
        AdminData.clear();
        assertEquals(0,AdminData.size());
    }
}

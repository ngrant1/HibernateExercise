package edu.matc.persistence;

import edu.matc.entity.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created on 9/13/16.
 *
 * @author pwaite
 */
public class UserDaoTest {

    UserDao dao;
    int initialRecordAmount;

    @Before
    public void setup() {
        dao = new UserDao();
        initialRecordAmount = dao.getAllUsers().size();
    }

    @Test
    public void getAllUsers() throws Exception {
        List<User> users = dao.getAllUsers();
        assertTrue(users.size() > 0);
    }

    @Test
    public void getUser() throws Exception {
        User user = dao.getUser(1);
        assertTrue(user.getUserid() == 1);
    }

    @Test
    public void addUser() throws Exception {
        User user = new User();
        user.setFirstName("Natasha");
        user.setLastName("Grant");
        user.setDateOfBirth(LocalDate.of(1992,10,16));
       // user.setStatus("2");
        int userid = dao.addUser(user);

        assertTrue(user.getUserid() == 10);
    }

    @Test
    public void deleteUser() throws Exception {
        User user = dao.getUser(1);
        dao.deleteUser(1);
        List<User> users = dao.getAllUsers();

        assertFalse(users.contains(user));
    }

    @Test
    public void updateUser() throws Exception {
        User user = dao.getUser(5);
        String name = "Grant";
        user.setLastName(name);
        dao.updateUser(user);
        assertTrue(dao.getUser(5).getLastName().equals("Grant"));
    }

}
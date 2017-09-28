package edu.matc.persistence;

import edu.matc.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulawaite on 2/2/16.
 */
public class UserDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Return a list of all users
     *
     * @return All users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        users = session.createCriteria(User.class).list();
        return users;
    }

    /**
     * retrieve a user given their id
     *
     * @param id the user's id
     * @return user
     */
    public User getUser(int id) {
        User user = null;
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            user = (User) session.get(User.class, id);
        } catch (HibernateException hibernateException) {
            log.error("Error retrieving user with id: " + id, hibernateException);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    /**
     * add a user
     *
     * @param user
     * @return the id of the inserted record
     */
    public int addUser(User user) {
        int id = 0;
        Transaction transaction = null;
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (int) session.save(user);
            transaction.commit();
        } catch (HibernateException hibernateException1){
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (HibernateException hibernateException2) {
                    log.error("Error rolling back save of user: " + user, hibernateException2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return id;
    }

    public void deleteUser(int id) {

        User user = new User();
        user.setUserid(id);

        Transaction transaction = null;
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch (HibernateException hibernateException1){
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (HibernateException hibernateException2) {
                    log.error("Error rolling back delete of user id: " + id, hibernateException2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    /**
     * Update the user
     * @param user
     */

    public void updateUser(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (HibernateException hibernateException1){
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (HibernateException hibernateException2) {
                    log.error("Error rolling back save of user: " + user, hibernateException2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }



}

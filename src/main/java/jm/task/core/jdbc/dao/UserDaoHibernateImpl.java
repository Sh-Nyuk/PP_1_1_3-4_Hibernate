package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tx = null;

        try(Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            String sqlQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            NativeQuery<?> query = session.createNativeQuery(sqlQuery);
            query.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tx = null;

        try(Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            String sqlQuery = "DROP TABLE IF EXISTS users";
            NativeQuery<?> query = session.createNativeQuery(sqlQuery);
            query.executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;

        try(Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);

            session.save(user);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try(Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            User user = (User) session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        List<User> users = null;
        try(Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Query<User> query = session.createQuery("from User", User.class);
            users = query.getResultList();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try(Session session = getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }

    }
}

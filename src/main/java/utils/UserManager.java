package utils;

import models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserManager {
    Session session;

    public UserManager() {
        this.session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }

    public User getUser(int id) {
        return session.get(User.class, id);
    }

    public User getUser(String name) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root).where(cb.equal(root.get("name"), name));

        Query<User> query = session.createQuery(cr);
        User user = query.uniqueResult();

        return user;
    }

    public List<User> getUsersList() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root);

        Query<User> query = session.createQuery(cr);
        List<User> list = query.getResultList();

        return list;
    }

    public Boolean userIsExists(String name) {
        User user = getUser(name);
        if (user == null)
            return false;
        return true;
    }

    public void addUser(User user) {
        if (session == null) {
            System.out.println("Error! Session not exists!");
            return;
        }
        if (userIsExists(user.getName())) {
            System.out.println("Error! The same user exists!");
            return;
        }

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    public void deleteUser(int id) {
        session.beginTransaction();
        User user = getUser(id);
        if (user == null) {
            System.out.println("Error! Wrong id!");
            return;
        }
        session.delete(user);
        session.getTransaction().commit();
    }

    public void editUser(User user) {
        if (getUser(user.getId()) == null) {
            System.out.println("Error! Wrong id!");
            return;
        }
        if (session == null) {
            System.out.println("Error! Session not exists!");
            return;
        }
        if (userIsExists(user.getName())) {
            System.out.println("Error! The same user exists!");
            return;
        }

        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }

    public void readUsers() {
        for (User u : getUsersList()) {
            System.out.println(u.toString());
        }
    }

    public void readUser(int id) {
        User user = getUser(id);
        if (user == null)
            System.out.println("Error! Wrong id!");
        else
            System.out.println(user.toString());
    }

    public void shutDown() {
        session.close();
    }
}

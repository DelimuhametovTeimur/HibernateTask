package dao;


import entities.Discipline;
import entities.User;
import enums.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.List;

public class DaoImplementaion implements AllFromOrToDatabase {

    private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    public List<User> getUsersByRole (String role) {

        Session s = null;
        Transaction t = null;
        List<User> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("SELECT u FROM User u JOIN u.roles r WHERE r.name=:role");
            query.setParameter("role", role);
            list = query.list();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }

    public List<User> getUsersByDiscipline (String discipline) {

        Session s = null;
        Transaction t = null;
        List<User> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("SELECT u FROM Discipline d JOIN d.members u WHERE d.name=:discipline");
            query.setParameter("discipline", discipline);
            list = query.list();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }

    public List<User> getUsersByTaskStatus (Status status) {

        Session s = null;
        Transaction t = null;
        List<User> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("SELECT u FROM User u JOIN u.tasks t WHERE t.status=:status");
            query.setParameter("status", status);
            list = query.list();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }

    public void updateHeadOfDiscipline (User newHead, String disciplineName) {

        Session s = null;
        Transaction t = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query = s.createQuery("UPDATE Discipline d SET d.headOfDiscipline=:newHead WHERE d.name=:disciplineName");
            query.setParameter("newHead", newHead);
            query.setParameter("disciplineName", disciplineName);
            query.executeUpdate();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
    }

    public List<Discipline> getDisciplineWith2OrLessUsers () {

        Session s = null;
        Transaction t = null;
        List<Discipline> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("SELECT d FROM Discipline d JOIN d.members m " +
                    "GROUP BY d HAVING COUNT(m) < 3");
            list = query.list();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }

    public void softDeleteUser (Long userId) {

        Session s = null;
        Transaction t = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("UPDATE User u SET u.enabled = false WHERE u.id=:userId");
            query.setParameter("userId", userId);
            query.executeUpdate();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
    }
}

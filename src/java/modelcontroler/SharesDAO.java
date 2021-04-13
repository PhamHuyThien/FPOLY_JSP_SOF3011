/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelcontroler;

import java.util.List;
import model.Shares;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author MSII
 */
public class SharesDAO implements DataAccessObject<Shares> {

    private static final Session session = HibernateUtil.getSession();
    private String error;

    @Override
    public List<Shares> getAll() {
        return session.createCriteria(Shares.class).list();
    }

    @Override
    public List<Shares> getAll(int start, int end) {
        return session.createCriteria(Shares.class).setFirstResult(start).setMaxResults(end).list();
    }

    @Override
    public List<Shares> query(String sql) {
        return query(sql, -1, -1);
    }

    @Override
    public List<Shares> query(String sql, int start, int end) {
        return query(sql, start, end, (String[]) null);
    }

    @Override
    public List<Shares> query(String sql, int start, int end, String... param) {
        SQLQuery sqlq = session.createSQLQuery(sql);
        sqlq.addEntity(Shares.class);
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                sqlq.setString(i, param[i]);
            }
        }
        if (start != -1 && end != -1) {
            sqlq.setFirstResult(start);
            sqlq.setMaxResults(end);
        }
        return sqlq.list();
    }

    @Override
    public boolean save(Shares users) {
        try {
            session.save(users);
            session.beginTransaction().commit();
            return true;
        } catch (Exception e) {
            error = e.toString();
        }
        return false;
    }

    @Override
    public boolean update(Shares users) {
        try {
            session.update(users);
            session.beginTransaction().commit();
            return true;
        } catch (Exception e) {
            error = e.toString();
        }
        return false;
    }

    @Override
    public boolean delete(Shares users) {
        try {
            session.delete(users);
            session.beginTransaction().commit();
            return true;
        } catch (Exception e) {
            error = e.toString();
        }
        return false;
    }

    @Override
    public Shares get(int id) {
        return (Shares) session.get(Shares.class, id);
    }

    @Override
    public String getError() {
        return error;
    }
}

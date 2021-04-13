/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelcontroler;

import java.util.List;
import model.Videos;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author MSII
 */
public class VideosDAO implements DataAccessObject<Videos> {

    private static final Session session = HibernateUtil.getSession();
    private String error;

    @Override
    public List<Videos> getAll() {
        return session.createCriteria(Videos.class).list();
    }

    @Override
    public List<Videos> getAll(int start, int end) {
        return session.createCriteria(Videos.class).setFirstResult(start).setMaxResults(end).list();
    }

    @Override
    public List<Videos> query(String sql) {
        return query(sql, -1, -1);
    }

    @Override
    public List<Videos> query(String sql, int start, int end) {
        return query(sql, start, end, (String[]) null);
    }

    @Override
    public List<Videos> query(String sql, int start, int end, String... param) {
        SQLQuery sqlq = session.createSQLQuery(sql);
        sqlq.addEntity(Videos.class);
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
    public boolean save(Videos users) {
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
    public boolean update(Videos users) {
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
    public boolean delete(Videos users) {
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
    public Videos get(int id) {
        return (Videos) session.get(Videos.class, id);
    }

    @Override
    public String getError() {
        return error;
    }
}

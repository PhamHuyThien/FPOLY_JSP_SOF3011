package modelcontroler;

import java.sql.Connection;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.internal.SessionImpl;

public class HibernateUtil {

    private static final Session session;

    static {
        try {
            session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();
        } catch (HibernateException ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return session;
    }

    public static Connection getConnection() {
        return ((SessionImpl) HibernateUtil.getSession()).connection();
    }
}

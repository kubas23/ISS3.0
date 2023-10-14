import com.dmdev.entity.EntityClassISSDataHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {
    void insertLocationTable(String longitude, String latitude, String message, String time) {

        Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            EntityClassISSDataHibernate entityClassISSDataHibernate =
                    EntityClassISSDataHibernate.builder()
                            .longitude(longitude)
                            .latitude(latitude)
                            .message(message)
                            .time(time)
                            .build();
            session.save(entityClassISSDataHibernate);
            session.getTransaction().commit();
        }
    }
}


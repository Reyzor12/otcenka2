package ru.eleron.osa.lris.otcenka.service.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.eleron.osa.lris.otcenka.entities.Department;
import ru.eleron.osa.lris.otcenka.entities.User;
import ru.eleron.osa.lris.otcenka.service.dao.UserDao;

import java.util.List;

@Component
@EnableTransactionManagement
public class UserDaoImp extends BaseOperation<User> implements UserDao<User> {

    private static final String GET_USER_BY_PARAM = "FROM User WHERE name=:name AND secondName=:secondName AND lastName=:lastName AND department=:department";

    @Autowired
    private SessionFactory sessionFactory;

    public UserDaoImp() {
        super(User.class);
    }

    @Override
    @Transactional
    public List<User> getUserByData(String name, String surname, String lastname, Department department) {

        return sessionFactory.getCurrentSession().createQuery(GET_USER_BY_PARAM)
                .setParameter("name",name)
                .setParameter("secondName",surname)
                .setParameter("lastName",lastname)
                .setParameter("department",department).list();
    }
}

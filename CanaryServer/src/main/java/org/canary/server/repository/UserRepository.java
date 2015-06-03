package org.canary.server.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.server.model.Role;
import org.canary.server.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User> {

    private static final String USERNAME_PROPERTY_NAME = "username";

    private static final Logger LOGGER = Logger.getLogger(UserRepository.class);

    private UserRepository() {
	super();
    }

    public User create(final String username, final String password) {

	LOGGER.debug("create[username=" + username + ", password=("
		+ StringUtils.isNotBlank(password) + ")]");

	if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; username or password cannot be blank.");
	}

	final User user = new User();
	final List<Role> roles = new ArrayList<Role>();
	final Session session = super.getSession();
	final int id;

	user.setUsername(username);
	user.setPassword(password);

	roles.add(Role.USER);
	user.setRoles(roles);

	id = (int) session.save(user);

	return super.read(id);
    }

    public User read(final String username) {

	LOGGER.debug("read[username=" + username + "]");

	if (StringUtils.isBlank(username)) {

	    throw new IllegalArgumentException(
		    "Illegal argument; username cannot be blank.");
	}

	final Session session = super.getSession();
	final Criteria criteria = session.createCriteria(User.class);

	criteria.add(Restrictions.eq(USERNAME_PROPERTY_NAME, username));

	return (User) criteria.uniqueResult();
    }

    @Override
    public final Class<User> getModelClass() {

	LOGGER.debug("getModelClass");

	return User.class;
    }

}

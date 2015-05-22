package org.canary.server;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.canary.server.model.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class AdminUserGenerator {

    private static final String PATH = "canary.properties";

    private static final String ADMIN_USERNAME_PROPERTY_NAME = "admin.username";
    private static final String ADMIN_PASSWORD_PROPERTY_NAME = "admin.password";

    private static final String DATABASE_DRIVER_PROPERTY_NAME = "database.driver";
    private static final String DATABASE_URL_PROPERTY_NAME = "database.url";
    private static final String DATABASE_USERNAME_PROPERTY_NAME = "database.username";
    private static final String DATABASE_PASSWORD_PROPERTY_NAME = "database.password";

    private static final String USER_SQL = //
    "INSERT INTO `canary`.`user` (`Username`, `Password`) VALUES "
	    + "('%s', '%s');";

    private static final String USER_ROLE_SQL = //
    "INSERT INTO `canary`.`user_role` (`User_Id`, `Role_Id`) VALUES ("
	    + "(SELECT user.`Id` FROM `canary`.`user` user "
	    + "WHERE user.`Username` = '%s'),"
	    + "(SELECT role.`Id` FROM `canary`.`role` role "
	    + "WHERE role.`Name` = '%s'));";

    public static void main(final String[] args) throws IOException,
	    ClassNotFoundException, SQLException {

	final Thread thread = Thread.currentThread();
	final ClassLoader classLoader = thread.getContextClassLoader();
	final InputStream inputStream = classLoader.getResourceAsStream(PATH);
	final Properties properties = new Properties();
	final String username;
	final String password;
	final BCryptPasswordEncoder encoder;
	final String passwordHash;

	properties.load(inputStream);
	username = properties.getProperty(ADMIN_USERNAME_PROPERTY_NAME);
	password = properties.getProperty(ADMIN_PASSWORD_PROPERTY_NAME);
	encoder = new BCryptPasswordEncoder();
	passwordHash = encoder.encode(password);

	AdminUserGenerator.insertUser(username, passwordHash, properties);
	AdminUserGenerator.insertUserRoles(username, properties);

    }

    private static void insertUser(final String username,
	    final String passwordHash, final Properties properties)
	    throws ClassNotFoundException, SQLException {

	final String userSQL = String.format(USER_SQL, username, passwordHash);

	AdminUserGenerator.runSQL(userSQL, properties);
    }

    private static void insertUserRoles(final String username,
	    final Properties properties) throws ClassNotFoundException,
	    SQLException {

	String roleName;
	String userRoleSQL;

	for (final Role role : Role.values()) {

	    roleName = role.getAuthority();
	    userRoleSQL = String.format(USER_ROLE_SQL, username, roleName);

	    AdminUserGenerator.runSQL(userRoleSQL, properties);
	}
    }

    private static void runSQL(final String SQL, final Properties properties)
	    throws ClassNotFoundException, SQLException {

	final String driver = properties
		.getProperty(DATABASE_DRIVER_PROPERTY_NAME);
	final String url = "jdbc:"
		+ properties.getProperty(DATABASE_URL_PROPERTY_NAME);
	final String username = properties
		.getProperty(DATABASE_USERNAME_PROPERTY_NAME);
	final String password = properties
		.getProperty(DATABASE_PASSWORD_PROPERTY_NAME);
	final Statement statement;

	Connection connection = null;

	try {

	    Class.forName(driver);
	    connection = DriverManager.getConnection(url, username, password);
	    statement = connection.createStatement();
	    statement.executeUpdate(SQL);

	} finally {

	    if (connection != null) {
		connection.close();
	    }
	}
    }

}

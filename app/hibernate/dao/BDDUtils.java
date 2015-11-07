package hibernate.dao;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import play.Play;

public class BDDUtils {
	
	//public static String CONFIG_FILE = Play.application().getFile("hibernate.cfg.xml").getAbsolutePath();
	public static Boolean BYTECODE_REFLECTION_OPTIMIZER = Play.application().configuration().getBoolean("DB_BYTECODE_REFLECTION_OPTIMIZER");
	public static Boolean SHOW_SQL = Play.application().configuration().getBoolean("DB_SHOW_SQL", false);
	public static Boolean FORMAT_SQL = Play.application().configuration().getBoolean("DB_FORMAT_SQL", false);
	public static String HBM2DDL_AUTO = Play.application().configuration().getString("DB_HBM2DDL_AUTO", "validate");
	public static String CACHE_PROVIDER = Play.application().configuration().getString("DB_CACHE_PROVIDER", "org.hibernate.cache.NoCacheProvider");
	public static Boolean ID_NEW_GENERATOR_MAPPING = Play.application().configuration().getBoolean("DB_ID_NEW_GENERATOR_MAPPING", true);
	public static String CURRENT_SESSION_CONTEXT = Play.application().configuration().getString("DB_CURRENT_SESSION_CONTEXT", "thread");
    public static Boolean AUTO_COMMIT = Play.application().configuration().getBoolean("DB_AUTO_COMMIT", false);
	public static Integer MAXIMUM_POOLSIZE = Play.application().configuration().getInt("DB_MAXIMUM_POOLSIZE", 10);
	public static Integer IDLE_TIMEOUT = Play.application().configuration().getInt("DB_IDLE_TIMEOUT", 300000);
	public static Boolean USE_SECOND_LEVEL_CACHE = Play.application().configuration().getBoolean("DB_USE_SECOND_LEVEL_CACHE", false);
    public static Boolean USE_QUERY_CACHE = Play.application().configuration().getBoolean("DB_USE_QUERY_CACHE", false);
    
    public static Boolean SSL = Play.application().configuration().getBoolean("DB_SSL");
    public static String SSL_FACTORY = Play.application().configuration().getString("DB_SSL_FACTORY");
	
	public static String DIALECT = Play.application().configuration().getString("DB_DIALECT", "org.hibernate.dialect.PostgreSQL9Dialect");
	public static String CLASS = Play.application().configuration().getString("DB_CLASS", "org.postgresql.ds.PGSimpleDataSource");
	
	public static String URL = Play.application().configuration().getString("DB_URL");
	
	public static String SERVER = Play.application().configuration().getString("DB_SERVER");
	public static String NAME = Play.application().configuration().getString("DB_NAME");
	public static Integer PORT = Play.application().configuration().getInt("DB_PORT");
	
	public static String USER = Play.application().configuration().getString("DB_USER");
	public static String PASSWORD = Play.application().configuration().getString("DB_PASSWORD");
	
	protected static SessionFactory sessionFactory;
	
	public static Properties createDBProperties() {
		Properties configDB = new Properties();
		
		configDB.setProperty("hibernate.bytecode.use_reflection_optimizer", String.valueOf(BYTECODE_REFLECTION_OPTIMIZER));
		configDB.setProperty("hibernate.show_sql", String.valueOf(SHOW_SQL));
		configDB.setProperty("hibernate.format_sql", String.valueOf(FORMAT_SQL));
		configDB.setProperty("hibernate.hbm2ddl.auto", HBM2DDL_AUTO);
		configDB.setProperty("hibernate.cache.provider_class", CACHE_PROVIDER);
		configDB.setProperty("hibernate.id.new_generator_mappings", String.valueOf(ID_NEW_GENERATOR_MAPPING));
	    configDB.setProperty("hibernate.current_session_context_class", CURRENT_SESSION_CONTEXT);
		configDB.setProperty("hibernate.hikari.autoCommit", String.valueOf(AUTO_COMMIT));
		configDB.setProperty("hibernate.hikari.maximumPoolSize", String.valueOf(MAXIMUM_POOLSIZE));
		configDB.setProperty("hibernate.hikari.idleTimeout", String.valueOf(IDLE_TIMEOUT));
	    configDB.setProperty("hibernate.cache.use_second_level_cache", String.valueOf(USE_SECOND_LEVEL_CACHE));
		configDB.setProperty("hibernate.cache.use_query_cache", String.valueOf(USE_QUERY_CACHE));

		if(SSL != null && !SSL.equals("")){
			configDB.setProperty("hibernate.hikari.dataSource.ssl", String.valueOf(SSL));
		}
		
		if(SSL_FACTORY != null && !SSL_FACTORY.equals("")){
			configDB.setProperty("hibernate.hikari.dataSource.sslfactory", SSL_FACTORY);
		}
		
		if(DIALECT != null && !DIALECT.equals("")){
			configDB.setProperty("hibernate.dialect", DIALECT);
		}
		
		if(CLASS != null && !CLASS.equals("")){
			configDB.setProperty("hibernate.hikari.dataSourceClassName", CLASS);
		}
		
		if(URL != null && !URL.equals("") && !URL.startsWith("jdbc")) {
			//USER
			String subURL = URL.substring(11);
			String tmpValue = subURL.substring(0, subURL.indexOf(":"));
			configDB.setProperty("hibernate.hikari.dataSource.user", tmpValue);
			
			//PASSWORD
			subURL = subURL.substring(subURL.indexOf(":") + 1);
			tmpValue =  subURL.substring(0, subURL.indexOf("@"));
			configDB.setProperty("hibernate.hikari.dataSource.password", tmpValue);
			
			//SERVER
			subURL = subURL.substring(subURL.indexOf("@") + 1);
			tmpValue =  subURL.substring(0, subURL.indexOf(":"));
			configDB.setProperty("hibernate.hikari.dataSource.serverName", tmpValue);
			
			//PORT
			subURL = subURL.substring(subURL.indexOf(":") + 1);
			tmpValue =  subURL.substring(0, subURL.indexOf("/"));
			configDB.setProperty("hibernate.hikari.dataSource.portNumber", tmpValue);
			
			//NAME
			subURL = subURL.substring(subURL.indexOf("/") + 1);
			configDB.setProperty("hibernate.hikari.dataSource.databaseName", subURL);
		} else {
			if(URL != null && !URL.equals("") && URL.startsWith("jdbc")) {
				configDB.setProperty("hibernate.hikari.dataSource.url", URL);
			} else {
				if(SERVER != null && SERVER.equals("")){
					configDB.setProperty("hibernate.hikari.dataSource.serverName", SERVER);
				}
				
				if(NAME != null && !NAME.equals("")) {
					configDB.setProperty("hibernate.hikari.dataSource.databaseName", NAME);
				}
				
				if(PORT != null && PORT != 0) {
					configDB.setProperty("hibernate.hikari.dataSource.portNumber", String.valueOf(PORT));
				}
			}
			
			if(USER != null && !USER.equals("")) {
				configDB.setProperty("hibernate.hikari.dataSource.user", USER);
			}
			
			if(PASSWORD != null && !PASSWORD.equals("")) {
				configDB.setProperty("hibernate.hikari.dataSource.password", PASSWORD);
			}
		}
		
		return configDB;
	}
	
	private static void buildSessionFactory() {
		if (sessionFactory==null) {
			Configuration config = new Configuration();//.configure(new File(CONFIG_FILE));
			
			//Ajout de la configuration de Hibernate et HikariCP
			config.addProperties(createDBProperties());
			
			//Ajout des classes
			config.addAnnotatedClass(hibernate.model.Connexion.class)
				.addAnnotatedClass(hibernate.model.Genre.class)
				.addAnnotatedClass(hibernate.model.Ville.class)
				.addAnnotatedClass(hibernate.model.TypeUtilisateur.class)
				.addAnnotatedClass(hibernate.model.Utilisateur.class);
			
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
			sessionFactory = config.buildSessionFactory(ssr);
		}
	}

	public static Session getCurrentSession() throws HibernateException {
		return getSessionFactory().getCurrentSession();
	}

	protected static SessionFactory getSessionFactory() throws HibernateException {
		if(sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory;
	}

	public static boolean getTransactionStatus() throws HibernateException {
		return getCurrentSession().getTransaction().getStatus() == TransactionStatus.ACTIVE;
	}
	
	public static Transaction beginTransaction(boolean isActive) throws HibernateException {
		if(!isActive) {
			return getSessionFactory().getCurrentSession().beginTransaction();
		}
		return null;
	}
	
	public static void insert(Object o) throws HibernateException {
		getCurrentSession().save(o);
	}
	
	public static void update(Object o) throws HibernateException {
		getCurrentSession().update(o);
	}
	
	public static void delete(Object o) throws HibernateException {
		getCurrentSession().delete(o);
	}
	
	public static void commit(boolean isActive, Transaction tx) throws HibernateException {
		if(!isActive) {
			tx.commit();
		}
	}
	
	public static void rollback(boolean isActive, Transaction tx) throws HibernateException {
		if(!isActive && tx != null) {
			tx.rollback();
		}
	}
}

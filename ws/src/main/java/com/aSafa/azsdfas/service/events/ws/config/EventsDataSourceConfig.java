
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ConfigurationProperties
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@PropertySources(value={@PropertySource("classpath:events-database.properties")})
public class EventsDataSourceConfig {
	private static final Log LOGGER = LogFactory.getLog(EventsDataSourceConfig.class);

	@Value("${spring.ORA_RDO.driver-class-name}")
	private String rdoDatabaseDriverClassName;

	@Value("${spring.ORA_RDO.url}")
	private String rdoDatasourceUrl;

	@Value("${spring.ORA_RDO.username}")
	private String rdoDatabaseUserName;

	@Value("${spring.ORA_RDO.password}")
	private String rdoDatabasePassword;

	@Value("${spring.ORA_RDO.initialSize}")
	private String rdoInitialSize;

	@Value("${spring.ORA_RDO.maxActive}")
	private String rdoMaxActive;

	@Value("${spring.ORA_RDO.minIdle}")
	private String rdoMinIdle;

	@Value("${spring.ORA_RDO.maxIdle}")
	private String rdoMaxIdle;

	@Value("${spring.ORA_RDO.maxWait}")
	private String rdoMaxWait;

	@Value("${spring.ORA_RDO.testOnBorrow}")
	private String rdoTestOnBorrow;

	@Value("${spring.ORA_RDO.testOnReturn}")
	private String rdoTestOnReturn;

	@Value("${spring.ORA_RDO.testWhileIdle}")
	private String rdoTestWhileIdle;

	@Value("${spring.ORA_RDO.poolPreparedStatements}")
	private String rdoPoolPreparedStatements;

	@Value("${spring.ORA_RDO.validationQuery}")
	private String rdoValidationQuery;

	@Value("${spring.ORA_RDO.timeBetweenEvictionRunsMillis}")
	private String rdoTimeBetweenEvictionRunsMillis;

	@Value("${spring.ORA_RDO.minEvictableIdleTimeMillis}")
	private String rdoMinEvictableIdleTimeMillis;

	@Value("${spring.RDOORA.url}")
	private String rdoORADatasourceUrl;

	@Value("${spring.RDOORA.username}")
	private String rdoORADatabaseUserName;

	@Value("${spring.RDOORA.password}")
	private String rdoORADatabasePassword;

	@Value("${encryptionAlgo}")
	private String encryptionAlgo;

	@Value("${encriptionPwd}")
	private String deccryptionkey;

	@Value("${spring.RDMORA.url}")
	private String rdmDatasourceUrl;

	@Value("${spring.RDMORA.username}")
	private String rdmDatabaseUserName;

	@Value("${spring.RDMORA.password}")
	private String rdmDatabasePassword;
	//GREENPLUM DETAILS


	@Value("${spring.ANGDW.driver-class-name}")
	private String databaseDriverClassNameGP;

	@Value("${spring.ANGDW.url}")
	private String datasourceUrlGP;

	@Value("${spring.ANGDW.username}")
	private String databaseUsernameGP;

	@Value("${spring.ANGDW.password}")
	private String databasePasswordGP;



	@Value("${spring.ANGDW.initialSize}") 
	private String initialSizeGP;

	@Value("${spring.ANGDW.maxActive}") 
	private String maxActiveGP;

	@Value("${spring.ANGDW.minIdle}") 
	private String minIdleGP;

	@Value("${spring.ANGDW.maxIdle}") 
	private String maxIdleGP;

	@Value("${spring.ANGDW.maxWait}") 
	private String maxWaitGP;

	@Value("${spring.ANGDW.testOnBorrow}") 
	private String testOnBorrowGP;

	@Value("${spring.ANGDW.testOnReturn}") 
	private String testOnReturnGP;

	@Value("${spring.ANGDW.testWhileIdle}") 
	private String testWhileIdleGP;

	@Value("${spring.ANGDW.validationQuery}") 
	private String validationQueryGP;

	@Value("${spring.ANGDW.timeBetweenEvictionRunsMillis}") 
	private String timeBetweenEvictionRunsMillisGP;

	@Value("${spring.ANGDW.minEvictableIdleTimeMillis}") 
	private String minEvictableIdleTimeMillisGP;

	//crn
	
	@Value("${spring.CNRORA.driver-class-name}")
	 	private String cnrDatabaseDriverClassName;
	 
	 	@Value("${spring.CNRORA.url}")
	 	private String cnrDatasourceUrl;
	 
	 	@Value("${spring.CNRORA.username}")
	 	private String cnrDatabaseUserName;
	 
	 	@Value("${spring.CNRORA.password}")
	 	private String cnrDatabasePassword;
	 	
	 	@Value("${spring.CNRORA.initialSize}")
	 	private String cnrInitialSize;
	 
	 	@Value("${spring.CNRORA.maxActive}")
	 	private String cnrMaxActive;
	 
	 	@Value("${spring.CNRORA.minIdle}")
	 	private String cnrMinIdle;
	 
	 	@Value("${spring.CNRORA.maxIdle}")
	 	private String cnrMaxIdle;
	 
	 	@Value("${spring.CNRORA.maxWait}")
	 	private String cnrMaxWait;
	 
	 	@Value("${spring.CNRORA.testOnBorrow}")
	 	private String cnrTestOnBorrow;
	 
	 	@Value("${spring.CNRORA.testOnReturn}")
	 	private String cnrTestOnReturn;
	 
	 	@Value("${spring.CNRORA.testWhileIdle}")
	 	private String cnrTestWhileIdle;
	 
	 	@Value("${spring.CNRORA.poolPreparedStatements}")
	 	private String cnrPoolPreparedStatements;
	 
	 	@Value("${spring.CNRORA.validationQuery}")
	 	private String cnrValidationQuery;
	 
	 	@Value("${spring.CNRORA.timeBetweenEvictionRunsMillis}")
	 	private String cnrTimeBetweenEvictionRunsMillis;
	 
	 	@Value("${spring.CNRORA.minEvictableIdleTimeMillis}")
	 	private String cnrMinEvictableIdleTimeMillis;
	
		@Value("${spring.ANGPD.url}")
		private String datasourceUrlGPD;

		@Value("${spring.ANGPD.username}")
		private String databaseUsernameGPD;

		@Value("${spring.ANGPD.password}")
		private String databasePasswordGPD;
		
		/* v1/operator */
		@Value("${operatorUrl}")
		private String operatorUrl;
		
		@Value("${operatorClientId}")
		private String operatorClientId;
		
		@Value("${operatorClientSecret}")
		private String operatorClientSecret;

		@Value("${operatorScope}")
		private String operatorScope;
		
		@Value("${operatorPingUrl}")
		private String operatorPingUrl;
		
		@Value("${operatorTokenKey}")
		private String operatorTokenKey;
		
		// Connection details for FDM database
		
		@Value("${spring.ORA_FDM.driver-class-name}")
		private String fdmDatabaseDriverClassName;

		@Value("${spring.ORA_FDM.url}")
		private String fdmDatasourceUrl;

		@Value("${spring.ORA_FDM.username}")
		private String fdmDatabaseUserName;

		@Value("${spring.ORA_FDM.password}")
		private String fdmDatabasePassword;
		

		
		// Connection details for GP database
		@Value("${spring.ORA_GP.driver-class-name}")
		private String gpDatabaseDriverClassName;

		@Value("${spring.ORA_GP.url}")
		private String gpDatasourceUrl;

		@Value("${spring.ORA_GP.username}")
		private String gpDatabaseUserName;

		@Value("${spring.ORA_GP.password}")
		private String gpDatabasePassword;
		
		// Common connection pool properties..
		@Value("${spring.COMMON_PROP.initialSize}")
		private String poolInitialSize;

		@Value("${spring.COMMON_PROP.maxActive}")
		private String poolMaxActive;

		@Value("${spring.COMMON_PROP.minIdle}")
		private String poolMinIdle;

		@Value("${spring.COMMON_PROP.maxIdle}")
		private String poolMaxIdle;
		@Value("${spring.COMMON_PROP.maxWait}")
		private String poolMaxWait;

		@Value("${spring.COMMON_PROP.testOnBorrow}")
		private String poolTestOnBorrow;

		@Value("${spring.COMMON_PROP.testOnReturn}")
		private String poolTestOnReturn;

		@Value("${spring.COMMON_PROP.testWhileIdle}")
		private String poolTestWhileIdle;

		@Value("${spring.COMMON_PROP.poolPreparedStatements}")
		private String poolPoolPreparedStatements;

		@Value("${spring.COMMON_PROP.validationQuery}")
		private String poolValidationQuery;
		
		@Value("${spring.GP.validationQuery}")
		private String gpPoolValidationQuery;

		@Value("${spring.COMMON_PROP.timeBetweenEvictionRunsMillis}")
		private String poolTimeBetweenEvictionRunsMillis;

		@Value("${spring.COMMON_PROP.minEvictableIdleTimeMillis}")
		private String poolMinEvictableIdleTimeMillis;
		
		@Value("${spring.ORA_RDO_PROD.driver-class-name}")
		private String oraRdoDatabaseDriverClassName;

		@Value("${spring.ORA_RDO_PROD.url}")
		private String oraRdoDatasourceUrl;

		@Value("${spring.ORA_RDO_PROD.username}")
		private String oraRdoDatabaseUserName;

		@Value("${spring.ORA_RDO_PROD.password}")
		private String oraRdoDatabasePassword;


	@Bean(name = "dataSourceRDO") 
	public DataSource dataSourceRDO() throws IOException {
		DataSource  dataSource = null;
		try{
			PoolProperties prop = new PoolProperties(); 
			prop.setDriverClassName(rdoDatabaseDriverClassName);
			prop.setUrl(rdoDatasourceUrl);
			prop.setUsername(rdoDatabaseUserName);
			prop.setPassword(DataServiceUtils.getSecurePassword("spring.ORA_RDO.password", rdoDatabasePassword, encryptionAlgo, deccryptionkey));

			prop.setLogValidationErrors(true);
			prop.setValidationQuery(rdoValidationQuery);
			prop.setInitialSize(Integer.parseInt(rdoInitialSize));
			prop.setMaxActive(Integer.parseInt(rdoMaxActive));
			prop.setMinIdle(Integer.parseInt(rdoMinIdle));
			prop.setMaxIdle(Integer.parseInt(rdoMaxIdle));
			prop.setMaxWait(Integer.parseInt(rdoMaxWait));

			prop.setTestOnBorrow(Boolean.parseBoolean(rdoTestOnBorrow));
			prop.setTestOnReturn(Boolean.parseBoolean(rdoTestOnReturn));
			prop.setTestWhileIdle(Boolean.parseBoolean(rdoTestWhileIdle));
			prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(rdoTimeBetweenEvictionRunsMillis));
			prop.setMinEvictableIdleTimeMillis(Integer.parseInt(rdoMinEvictableIdleTimeMillis));
			dataSource = new DataSource(prop);
			LOGGER.info("DB CONNECTION POOLING PROPS RDO::"+dataSource.getPoolProperties());
		}catch(Exception e){
			LOGGER.error("EXCEPTION OCCURED IN datasource RDO::", e);
		}

		return dataSource;		

	}

	//For Greenplum
	@Bean(name = "dataSource") 
	public DataSource dataSourceGP() throws IOException {
		DataSource dataSource = new DataSource();

		try{

			PoolProperties prop = new PoolProperties(); 

			prop.setDriverClassName(databaseDriverClassNameGP);
			prop.setUrl(datasourceUrlGP);
			prop.setUsername(databaseUsernameGP);
			prop.setPassword(DataServiceUtils.getSecurePassword("spring.ANGDW.password", databasePasswordGP, encryptionAlgo, deccryptionkey));
			prop.setLogValidationErrors(true);
			prop.setValidationQuery(validationQueryGP);
			prop.setInitSQL(validationQueryGP);
			prop.setInitialSize(Integer.parseInt(initialSizeGP));
			prop.setMaxActive(Integer.parseInt(maxActiveGP));
			prop.setMinIdle(Integer.parseInt(minIdleGP));
			prop.setMaxIdle(Integer.parseInt(maxIdleGP));
			prop.setMaxWait(Integer.parseInt(maxWaitGP));
			prop.setTestOnBorrow(Boolean.parseBoolean(testOnBorrowGP));
			prop.setTestOnReturn(Boolean.parseBoolean(testOnReturnGP));
			prop.setTestWhileIdle(Boolean.parseBoolean(testWhileIdleGP));

			prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(timeBetweenEvictionRunsMillisGP));
			prop.setMinEvictableIdleTimeMillis(Integer.parseInt(minEvictableIdleTimeMillisGP));
			dataSource = new DataSource(prop);	
			LOGGER.info("DB CONNECTION POOLING PROPS GP::"+dataSource.getPoolProperties());
		}catch(Exception e){

			LOGGER.error("EXCEPTION OCCURED IN datasourceGP::", e);
		}
		return dataSource;		

	}
	
	@Bean(name = "dataSourceRDOORA") 
	public DataSource dataSourceRDOORA() throws IOException {
		DataSource  dataSource = new DataSource();


		try{

			PoolProperties prop = new PoolProperties(); 

			prop.setDriverClassName(rdoDatabaseDriverClassName);
			prop.setUrl(rdoORADatasourceUrl);
			prop.setUsername(rdoORADatabaseUserName);
			prop.setPassword(DataServiceUtils.getSecurePassword("spring.RDOORA.password", rdoORADatabasePassword, encryptionAlgo, deccryptionkey));
			prop.setLogValidationErrors(true);
			prop.setValidationQuery(rdoValidationQuery);
			prop.setInitialSize(Integer.parseInt(rdoInitialSize));
			prop.setMaxActive(Integer.parseInt(rdoMaxActive));
			prop.setMinIdle(Integer.parseInt(rdoMinIdle));
			prop.setMaxIdle(Integer.parseInt(rdoMaxIdle));
			prop.setMaxWait(Integer.parseInt(rdoMaxWait));
			prop.setTestOnBorrow(Boolean.parseBoolean(rdoTestOnBorrow));
			prop.setTestOnReturn(Boolean.parseBoolean(rdoTestOnReturn));
			prop.setTestWhileIdle(Boolean.parseBoolean(rdoTestWhileIdle));
			prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(rdoTimeBetweenEvictionRunsMillis));
			prop.setMinEvictableIdleTimeMillis(Integer.parseInt(minEvictableIdleTimeMillisGP));
			dataSource = new DataSource(prop);
			LOGGER.info("DB CONNECTION POOLING PROPS dataSourceRDOORA::"+dataSource.getPoolProperties());
		}catch(Exception e){
			LOGGER.error("EXCEPTION OCCURED IN dataSourceRDOORA::", e);
		}

		return dataSource;		

	}

	@Bean(name = "dataSourceRDMORA") 
	public DataSource dataSourceRDMORA() throws IOException {
		DataSource  dataSource = new DataSource();	
		try{

			PoolProperties prop = new PoolProperties(); 

			prop.setDriverClassName(rdoDatabaseDriverClassName);
			prop.setUrl(rdmDatasourceUrl);
			prop.setUsername(rdmDatabaseUserName);
			prop.setPassword(DataServiceUtils.getSecurePassword("spring.RDMORA.password", rdmDatabasePassword, encryptionAlgo, deccryptionkey));
			prop.setLogValidationErrors(true);
			prop.setValidationQuery(rdoValidationQuery);
			prop.setInitialSize(Integer.parseInt(rdoInitialSize));
			prop.setMaxActive(Integer.parseInt(rdoMaxActive));
			prop.setMinIdle(Integer.parseInt(rdoMinIdle));
			prop.setMaxIdle(Integer.parseInt(rdoMaxIdle));
			prop.setMaxWait(Integer.parseInt(rdoMaxWait));
			prop.setTestOnBorrow(Boolean.parseBoolean(rdoTestOnBorrow));
			prop.setTestOnReturn(Boolean.parseBoolean(rdoTestOnReturn));
			prop.setTestWhileIdle(Boolean.parseBoolean(rdoTestWhileIdle));
			prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(rdoTimeBetweenEvictionRunsMillis));
			prop.setMinEvictableIdleTimeMillis(Integer.parseInt(rdoMinEvictableIdleTimeMillis));
			dataSource = new DataSource(prop);
			LOGGER.info("DB CONNECTION POOLING PROPS RDMORA::"+dataSource.getPoolProperties());
		}catch(Exception e){
			
			LOGGER.error("EXCEPTION OCCURED IN dataSourceRDMORA::", e);
		}
		return dataSource;		
	}
	
	@Bean(name = "dataSourceCNRORA") 
	 	public DataSource dataSourceCNRORA() throws IOException {
	 		DataSource  dataSource = new DataSource();
	 
	 
	 		try{
	 
	 			PoolProperties prop = new PoolProperties(); 
	 
	 			prop.setDriverClassName(cnrDatabaseDriverClassName);
	 			prop.setUrl(cnrDatasourceUrl);
	 			prop.setUsername(cnrDatabaseUserName);
	 			prop.setPassword(DataServiceUtils.getSecurePassword("spring.CNRORA.password", cnrDatabasePassword, encryptionAlgo, deccryptionkey));
	 			prop.setLogValidationErrors(true);
	 			prop.setValidationQuery(cnrValidationQuery);
	 			prop.setInitialSize(Integer.parseInt(cnrInitialSize));
	 			prop.setMaxActive(Integer.parseInt(cnrMaxActive));
	 			prop.setMinIdle(Integer.parseInt(cnrMinIdle));
				prop.setMaxIdle(Integer.parseInt(cnrMaxIdle));
	 			prop.setMaxWait(Integer.parseInt(cnrMaxWait));
	 
	 			prop.setTestOnBorrow(Boolean.parseBoolean(cnrTestOnBorrow));
	 			prop.setTestOnReturn(Boolean.parseBoolean(cnrTestOnReturn));
	 			prop.setTestWhileIdle(Boolean.parseBoolean(cnrTestWhileIdle));
	 			prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(cnrTimeBetweenEvictionRunsMillis));
	 			prop.setMinEvictableIdleTimeMillis(Integer.parseInt(cnrMinEvictableIdleTimeMillis));
	 			dataSource = new DataSource(prop);
	 		}catch(Exception e){
				LOGGER.error("EXCEPTION OCCURED IN dataSourceCNRORA::", e);
	 		}
	 
	 		return dataSource;		
	 
	 	}
	
	//For Greenplum Discovery - DiagDDS Events Overlay service
		@Bean(name = "dataSourceGPD") 
		public DataSource dataSourceGPD() throws IOException {
			DataSource dataSource = new DataSource();

			try{

				PoolProperties prop = new PoolProperties(); 

				prop.setDriverClassName(databaseDriverClassNameGP);
				prop.setUrl(datasourceUrlGPD);
				prop.setUsername(databaseUsernameGPD);
				prop.setPassword(DataServiceUtils.getSecurePassword("spring.ANGPD.password", databasePasswordGPD, encryptionAlgo, deccryptionkey));
				prop.setLogValidationErrors(true);
				prop.setValidationQuery(validationQueryGP);
				prop.setInitSQL(validationQueryGP);
				prop.setInitialSize(Integer.parseInt(initialSizeGP));
				prop.setMaxActive(Integer.parseInt(maxActiveGP));
				prop.setMinIdle(Integer.parseInt(minIdleGP));
				prop.setMaxIdle(Integer.parseInt(maxIdleGP));
				prop.setMaxWait(Integer.parseInt(maxWaitGP));

				prop.setTestOnBorrow(Boolean.parseBoolean(testOnBorrowGP));
				prop.setTestOnReturn(Boolean.parseBoolean(testOnReturnGP));
				prop.setTestWhileIdle(Boolean.parseBoolean(testWhileIdleGP));

				prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(timeBetweenEvictionRunsMillisGP));
				prop.setMinEvictableIdleTimeMillis(Integer.parseInt(minEvictableIdleTimeMillisGP));
				dataSource = new DataSource(prop);	
				LOGGER.info("DB CONNECTION POOLING PROPS GP::"+dataSource.getPoolProperties());
			}catch(Exception e){

				LOGGER.error("EXCEPTION OCCURED IN datasourceGP::", e);
			}
			return dataSource;		

		}
		
		
		@Bean(name = "operatorProperties") 
		public OperatorProperties setAllOperatorProperties() throws IOException {
			OperatorProperties properties = new OperatorProperties(); 
			 
			properties.setOperatorUrl(operatorUrl);
			properties.setOperatorClientId(operatorClientId);
			properties.setOperatorClientSecret(operatorClientSecret);
			properties.setOperatorScope(operatorScope);
			properties.setOperatorPingUrl(operatorPingUrl);
			properties.setOperatorTokenKey(operatorTokenKey);

			return properties;		
		}
		

		@Bean(name = "dataSourceFDM") 
		public DataSource getFDMDataSource() throws IOException {
			DataSource  dataSource = null;
			try{
				PoolProperties prop = new PoolProperties(); 
				prop.setDriverClassName(fdmDatabaseDriverClassName);
				prop.setUrl(fdmDatasourceUrl);
				prop.setUsername(fdmDatabaseUserName);
				prop.setPassword(DataServiceUtils.getSecurePassword("spring.ORA_FDM.password", fdmDatabasePassword, encryptionAlgo, deccryptionkey));
				prop.setLogValidationErrors(true);
				prop.setValidationQuery(poolValidationQuery);
				prop.setInitialSize(Integer.parseInt(poolInitialSize));
				prop.setMaxActive(Integer.parseInt(poolMaxActive));
				prop.setMinIdle(Integer.parseInt(poolMinIdle));
				prop.setMaxIdle(Integer.parseInt(poolMaxIdle));
				prop.setMaxWait(Integer.parseInt(poolMaxWait));

				prop.setTestOnBorrow(Boolean.parseBoolean(poolTestOnBorrow));
				prop.setTestOnReturn(Boolean.parseBoolean(poolTestOnReturn));
				prop.setTestWhileIdle(Boolean.parseBoolean(poolTestWhileIdle));
				prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(poolTimeBetweenEvictionRunsMillis));
				prop.setMinEvictableIdleTimeMillis(Integer.parseInt(poolMinEvictableIdleTimeMillis));
				dataSource = new DataSource(prop);
				LOGGER.info("DB CONNECTION POOLING PROPS pool::"+dataSource.getPoolProperties());
			}catch(Exception e){
				LOGGER.error("EXCEPTION OCCURED IN datasource pool::", e);
			}

			return dataSource;		

		}
		
		
		@Bean(name = "dataSourceRDODB") 
		public DataSource getRDODataSource() throws IOException {
			DataSource  dataSource = null;
			try{
				PoolProperties prop = new PoolProperties(); 
				prop.setDriverClassName(oraRdoDatabaseDriverClassName);
				prop.setUrl(oraRdoDatasourceUrl);
				prop.setUsername(oraRdoDatabaseUserName);
				prop.setPassword(DataServiceUtils.getSecurePassword("spring.ORA_RDO_PROD.password", oraRdoDatabasePassword, encryptionAlgo, deccryptionkey));

				prop.setLogValidationErrors(true);
				prop.setValidationQuery(poolValidationQuery);
				prop.setInitialSize(Integer.parseInt(poolInitialSize));
				prop.setMaxActive(Integer.parseInt(poolMaxActive));
				prop.setMinIdle(Integer.parseInt(poolMinIdle));
				prop.setMaxIdle(Integer.parseInt(poolMaxIdle));
				prop.setMaxWait(Integer.parseInt(poolMaxWait));

				prop.setTestOnBorrow(Boolean.parseBoolean(poolTestOnBorrow));
				prop.setTestOnReturn(Boolean.parseBoolean(poolTestOnReturn));
				prop.setTestWhileIdle(Boolean.parseBoolean(poolTestWhileIdle));
				prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(poolTimeBetweenEvictionRunsMillis));
				prop.setMinEvictableIdleTimeMillis(Integer.parseInt(poolMinEvictableIdleTimeMillis));
				dataSource = new DataSource(prop);
				LOGGER.info("DB CONNECTION POOLING PROPS RDO::"+dataSource.getPoolProperties());
			}catch(Exception e){
				LOGGER.error("EXCEPTION OCCURED IN datasource RDO::", e);
			}

			return dataSource;		

		}
		
		@Bean(name = "dataSourceGP") 
		public DataSource getGPDataSource() throws IOException {
			DataSource  dataSource = null;
			try{
				PoolProperties prop = new PoolProperties(); 
				prop.setDriverClassName(gpDatabaseDriverClassName);
				prop.setUrl(gpDatasourceUrl);
				prop.setUsername(gpDatabaseUserName);
				prop.setPassword(DataServiceUtils.getSecurePassword("spring.ORA_GP.password", gpDatabasePassword, encryptionAlgo, deccryptionkey));

				prop.setLogValidationErrors(true);
				prop.setValidationQuery(gpPoolValidationQuery);
				prop.setInitialSize(Integer.parseInt(poolInitialSize));
				prop.setMaxActive(Integer.parseInt(poolMaxActive));
				prop.setMinIdle(Integer.parseInt(poolMinIdle));
				prop.setMaxIdle(Integer.parseInt(poolMaxIdle));
				prop.setMaxWait(Integer.parseInt(poolMaxWait));

				prop.setTestOnBorrow(Boolean.parseBoolean(poolTestOnBorrow));
				prop.setTestOnReturn(Boolean.parseBoolean(poolTestOnReturn));
				prop.setTestWhileIdle(Boolean.parseBoolean(poolTestWhileIdle));
				prop.setTimeBetweenEvictionRunsMillis(Integer.parseInt(poolTimeBetweenEvictionRunsMillis));
				prop.setMinEvictableIdleTimeMillis(Integer.parseInt(poolMinEvictableIdleTimeMillis));
				dataSource = new DataSource(prop);
				LOGGER.info("DB CONNECTION POOLING PROPS gp::"+dataSource.getPoolProperties());
			}catch(Exception e){
				LOGGER.error("EXCEPTION OCCURED IN datasource gp::", e);
			}

			return dataSource;		

		}
}

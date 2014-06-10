/**
 * Created on Jan 4, 2012
 */
package config;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author Clarence
 * 
 */
@Configuration
@ImportResource( {"classpath:spring/data-source-tx-jpa.xml"})
@ComponentScan(basePackages = {"com.baosight.scc.ec"})
@Profile("test")
public class ServiceTestConfig {

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:sql/schema.sql")
				.build();
	}
	
	@Bean(name="databaseTester")
	public DataSourceDatabaseTester dataSourceDatabaseTester() {
		DataSourceDatabaseTester databaseTester = new DataSourceDatabaseTester(dataSource());
		return databaseTester;
	}
	
	@Bean(name="xlsDataFileLoader")
	public XlsDataFileLoader xlsDataFileLoader() {
		return new XlsDataFileLoader();
	}

    @Bean(name="xmlDataFileLoader")
    public FlatXmlDataFileLoader flatXmlDataFileLoader(){
        return new FlatXmlDataFileLoader();
    }

}

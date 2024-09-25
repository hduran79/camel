package co.com.votre.camel.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = "co.com.votre.camel.repository", sqlSessionFactoryRef = "votreSessionFactory")
public class MybatisConfig {
	
	@Bean(name = "dataSourcePropertiesVotre")
    @ConfigurationProperties(prefix = "spring.datasource.votre")
    public DataSourceProperties votreDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "votreDatasource")
    public DataSource dataSourceVotre(
    		@Qualifier("dataSourcePropertiesVotre") DataSourceProperties dataSourceProperties
    		) {
        HikariDataSource dataSource = (HikariDataSource) dataSourceProperties
                .initializeDataSourceBuilder()
                .build();
        dataSource.setConnectionTestQuery("values 1");

        return dataSource;
    }

    @Primary
    @Bean(name = "votreTransactionManager")
    public DataSourceTransactionManager transactionManager(
    		@Qualifier("votreDatasource") DataSource dataSource
    		) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean(name = "votreSessionFactory")
    public SqlSessionFactory votreSessionFactory(
    		@Qualifier("votreDatasource") DataSource dataSource
    		) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

}
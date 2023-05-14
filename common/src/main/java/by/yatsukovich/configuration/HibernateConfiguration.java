package by.yatsukovich.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class HibernateConfiguration {

    public SessionFactory getSessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        // Package contain entity classes
        factoryBean.setPackagesToScan("by.yatsukovich");
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(getAdditionalProperties());
        factoryBean.afterPropertiesSet();
        //
        SessionFactory sf = factoryBean.getObject();
        //System.out.println("## getSessionFactory: " + sf);
        return sf;
    }

    private Properties getAdditionalProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        return properties;
    }

}

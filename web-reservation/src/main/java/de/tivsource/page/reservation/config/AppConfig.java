package de.tivsource.page.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.registration.RegistrationDaoLocal;

@Configuration
@ComponentScan(basePackages = {
        "de.tivsource.page.reservation.actions",   // Struts Actions
        "de.tivsource.page.reservation.services"   // Services / EJB-Wrapper
})
public class AppConfig {

    @Bean
    public JndiObjectFactoryBean eventDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/EventDao");
        jndiObjectFactoryBean.setProxyInterface(EventDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean locationDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/LocationDao");
        jndiObjectFactoryBean.setProxyInterface(LocationDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean pageDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/PageDao");
        jndiObjectFactoryBean.setProxyInterface(PageDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean propertyDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/PropertyDao");
        jndiObjectFactoryBean.setProxyInterface(PropertyDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean registrationDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/RegistrationDao");
        jndiObjectFactoryBean.setProxyInterface(RegistrationDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    /**
     * Definiert den JtaTransactionManager für TomEE.
     * Spring sucht automatisch im JNDI-Baum von TomEE nach dem globalen Transaktionsmanager.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
        
        // Optionale Absicherung für TomEE:
        // Setzt die Standard-JNDI-Namen für den Transaktionsmanager von OpenEJB/TomEE,
        // falls Spring sie in deiner Version nicht automatisch findet.
        jtaTransactionManager.setAutodetectUserTransaction(true);
        jtaTransactionManager.setAutodetectTransactionManager(true);
        
        return jtaTransactionManager;
    }

}// Ende class
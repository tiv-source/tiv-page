package de.tivsource.page.pdf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import de.tivsource.page.dao.pdf.PDFDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;

@Configuration
@ComponentScan(basePackages = {
        "de.tivsource.page.pdf.actions",   // Struts Actions
        "de.tivsource.page.pdf.services"   // Services / EJB-Wrapper
})
public class AppConfig {

    @Bean
    public JndiObjectFactoryBean pdfDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/PDFDao");
        jndiObjectFactoryBean.setProxyInterface(PDFDaoLocal.class);
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
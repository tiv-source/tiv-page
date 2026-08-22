package de.tivsource.page.image.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import de.tivsource.page.dao.captcha.CaptchaDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.pdf.PDFImageDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.pictureitem.PictureItemDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.slider.SliderImageDaoLocal;

@Configuration
@ComponentScan(basePackages = {
        "de.tivsource.page.image.actions",   // Struts Actions
        "de.tivsource.page.image.services"   // Services / EJB-Wrapper
})
public class AppConfig {

    @Bean
    public JndiObjectFactoryBean captchaDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/CaptchaDao");
        jndiObjectFactoryBean.setProxyInterface(CaptchaDaoLocal.class);
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
    public JndiObjectFactoryBean pdfImageDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/PDFImageDao");
        jndiObjectFactoryBean.setProxyInterface(PDFImageDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean pictureItemDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/PictureItemDao");
        jndiObjectFactoryBean.setProxyInterface(PictureItemDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean pictureDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/PictureDao");
        jndiObjectFactoryBean.setProxyInterface(PictureDaoLocal.class);
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
    public JndiObjectFactoryBean sliderImageDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/SliderImageDao");
        jndiObjectFactoryBean.setProxyInterface(SliderImageDaoLocal.class);
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
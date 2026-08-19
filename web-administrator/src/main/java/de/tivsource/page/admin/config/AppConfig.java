package de.tivsource.page.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import de.tivsource.page.dao.administration.RoleDaoLocal;
import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.dao.appointment.AppointmentDaoLocal;
import de.tivsource.page.dao.captcha.CaptchaDaoLocal;
import de.tivsource.page.dao.companion.CompanionDaoLocal;
import de.tivsource.page.dao.companion.CompanionGroupDaoLocal;
import de.tivsource.page.dao.contententry.ContentEntryDaoLocal;
import de.tivsource.page.dao.contentitem.ContentItemDaoLocal;
import de.tivsource.page.dao.country.CountryDaoLocal;
import de.tivsource.page.dao.course.CourseDaoLocal;
import de.tivsource.page.dao.cssfile.CSSFileDaoLocal;
import de.tivsource.page.dao.cssgroup.CSSGroupDaoLocal;
import de.tivsource.page.dao.event.EventDaoLocal;
import de.tivsource.page.dao.exhibition.ExhibitionDaoLocal;
import de.tivsource.page.dao.feedback.FeedbackDaoLocal;
import de.tivsource.page.dao.feedback.FeedbackOptionDaoLocal;
import de.tivsource.page.dao.gallery.GalleryDaoLocal;
import de.tivsource.page.dao.lecturer.LecturerDaoLocal;
import de.tivsource.page.dao.linkentry.LinkEntryDaoLocal;
import de.tivsource.page.dao.location.LocationDaoLocal;
import de.tivsource.page.dao.mail.MailAddressLockDaoLocal;
import de.tivsource.page.dao.mail.MailAddressLockRequestDaoLocal;
import de.tivsource.page.dao.manual.ManualDaoLocal;
import de.tivsource.page.dao.menuentry.MenuEntryDaoLocal;
import de.tivsource.page.dao.message.MessageDaoLocal;
import de.tivsource.page.dao.news.NewsDaoLocal;
import de.tivsource.page.dao.page.PageDaoLocal;
import de.tivsource.page.dao.pdf.PDFDaoLocal;
import de.tivsource.page.dao.picture.PictureDaoLocal;
import de.tivsource.page.dao.pictureitem.PictureItemDaoLocal;
import de.tivsource.page.dao.property.PropertyDaoLocal;
import de.tivsource.page.dao.province.ProvinceDaoLocal;
import de.tivsource.page.dao.reason.ReasonDaoLocal;
import de.tivsource.page.dao.registration.RegistrationDaoLocal;
import de.tivsource.page.dao.request.RequestDaoLocal;
import de.tivsource.page.dao.reservation.ReservationDaoLocal;
import de.tivsource.page.dao.salutation.SalutationDaoLocal;
import de.tivsource.page.dao.slider.SliderDaoLocal;
import de.tivsource.page.dao.subsumption.SubsumptionDaoLocal;
import de.tivsource.page.dao.vacancy.VacancyDaoLocal;

@Configuration
@ComponentScan(basePackages = {
        "de.tivsource.page.admin.actions",   // Struts Actions
        "de.tivsource.page.admin.services"   // Services / EJB-Wrapper
})
public class AppConfig {

    @Bean
    public JndiObjectFactoryBean roleDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/RoleDao");
        jndiObjectFactoryBean.setProxyInterface(RoleDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean userDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/UserDao");
        jndiObjectFactoryBean.setProxyInterface(UserDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean appointmentDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/AppointmentDao");
        jndiObjectFactoryBean.setProxyInterface(AppointmentDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean captchaDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/CaptchaDao");
        jndiObjectFactoryBean.setProxyInterface(CaptchaDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean companionDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/CompanionDao");
        jndiObjectFactoryBean.setProxyInterface(CompanionDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean companionGroupDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/CompanionGroupDao");
        jndiObjectFactoryBean.setProxyInterface(CompanionGroupDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean contentEntryDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/ContentEntryDao");
        jndiObjectFactoryBean.setProxyInterface(ContentEntryDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean contentItemDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/ContentItemDao");
        jndiObjectFactoryBean.setProxyInterface(ContentItemDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean countryDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/CountryDao");
        jndiObjectFactoryBean.setProxyInterface(CountryDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean courseDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/CourseDao");
        jndiObjectFactoryBean.setProxyInterface(CourseDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean cssFileDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/CSSFileDao");
        jndiObjectFactoryBean.setProxyInterface(CSSFileDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean cssGroupDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/CSSGroupDao");
        jndiObjectFactoryBean.setProxyInterface(CSSGroupDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean eventDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/EventDao");
        jndiObjectFactoryBean.setProxyInterface(EventDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean exhibitionDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/ExhibitionDao");
        jndiObjectFactoryBean.setProxyInterface(ExhibitionDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean feedbackDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/FeedbackDao");
        jndiObjectFactoryBean.setProxyInterface(FeedbackDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean feedbackOptionDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/FeedbackOptionDao");
        jndiObjectFactoryBean.setProxyInterface(FeedbackOptionDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean galleryDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/GalleryDao");
        jndiObjectFactoryBean.setProxyInterface(GalleryDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean lecturerDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/LecturerDao");
        jndiObjectFactoryBean.setProxyInterface(LecturerDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean linkEntryDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/LinkEntryDao");
        jndiObjectFactoryBean.setProxyInterface(LinkEntryDaoLocal.class);
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
    public JndiObjectFactoryBean mailAddressLockDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/MailAddressLockDao");
        jndiObjectFactoryBean.setProxyInterface(MailAddressLockDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean mailAddressLockRequestDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/MailAddressLockRequestDao");
        jndiObjectFactoryBean.setProxyInterface(MailAddressLockRequestDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean manualDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/ManualDao");
        jndiObjectFactoryBean.setProxyInterface(ManualDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean menuEntryDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/MenuEntryDao");
        jndiObjectFactoryBean.setProxyInterface(MenuEntryDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean messageDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/MessageDao");
        jndiObjectFactoryBean.setProxyInterface(MessageDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean newsDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/NewsDao");
        jndiObjectFactoryBean.setProxyInterface(NewsDaoLocal.class);
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
    public JndiObjectFactoryBean pdfDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/PDFDao");
        jndiObjectFactoryBean.setProxyInterface(PDFDaoLocal.class);
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
    public JndiObjectFactoryBean provinceDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/ProvinceDao");
        jndiObjectFactoryBean.setProxyInterface(ProvinceDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean reasonDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/ReasonDao");
        jndiObjectFactoryBean.setProxyInterface(ReasonDaoLocal.class);
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

    @Bean
    public JndiObjectFactoryBean requestDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/RequestDao");
        jndiObjectFactoryBean.setProxyInterface(RequestDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean reservationDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/ReservationDao");
        jndiObjectFactoryBean.setProxyInterface(ReservationDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean salutationDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/SalutationDao");
        jndiObjectFactoryBean.setProxyInterface(SalutationDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean sliderDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/SliderDao");
        jndiObjectFactoryBean.setProxyInterface(SliderDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean subsumptionDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/SubsumptionDao");
        jndiObjectFactoryBean.setProxyInterface(SubsumptionDaoLocal.class);
        jndiObjectFactoryBean.setLookupOnStartup(true); 
        return jndiObjectFactoryBean;
    }

    @Bean
    public JndiObjectFactoryBean vacancyDaoLocal() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("java:global/tiv-page/dao/VacancyDao");
        jndiObjectFactoryBean.setProxyInterface(VacancyDaoLocal.class);
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
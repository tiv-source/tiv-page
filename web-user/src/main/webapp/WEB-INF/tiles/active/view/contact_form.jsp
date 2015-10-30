<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <struts:form
          cssClass="form" 
          action="sent" 
          namespace="/contact" 
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
    >
      <struts:textfield key="contactForm.firstname"  />
      <struts:textfield key="contactForm.lastname"  />
      <struts:textfield key="contactForm.mail"  />
      <struts:textfield key="contactForm.telephone" />
      <struts:textfield key="contactForm.fax" />
      <struts:textarea key="contactForm.message" />
      <struts:submit/>
    </struts:form>

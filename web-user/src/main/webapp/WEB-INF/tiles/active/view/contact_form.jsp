<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <struts:property escape="false" value="page.getContent(getText('language'))" />

    <struts:form
          cssClass="form" 
          action="sent" 
          namespace="/contact" 
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
    >
      <struts:textfield key="message.firstname"  />
      <struts:textfield key="message.lastname"  />
      <struts:textfield key="message.mail"  />
      <struts:textfield key="message.telephone" />
      <struts:textfield key="message.fax" />
      <struts:textarea key="message.content" />
      <struts:submit/>
    </struts:form>

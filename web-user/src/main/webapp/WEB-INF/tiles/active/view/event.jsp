<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    

    
        <h4><struts:property value="event.getName(getText('language'))" /></h4>
        <p><struts:property value="event.price" /></p>
        <p><struts:property value="event.beginning" /></p>
        <p><struts:property value="event.ending" /></p>
        <p><struts:property value="event.deadline" /></p>
        
    <struts:property escape="false" value="page.getContent(getText('language'))" />

    <struts:form
          cssClass="form" 
          action="reserve" 
          namespace="/event" 
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
    >
      <struts:radio     key="reservation.gender" list="#{true:'Frau',false:'Herr'}" />
      <struts:textfield key="reservation.firstname"  />
      <struts:textfield key="reservation.lastname"  />
      <struts:textfield key="reservation.email"  />
      <struts:textfield key="reservation.telephone" />
      <struts:textfield key="reservation.quantity" />
      <struts:textfield key="reservation.time" />
      <struts:textarea  key="reservation.wishes" />
      
      <struts:submit type="button" value="Reservieren" />
      <struts:reset type="button" value="Löschen" />

    </struts:form>
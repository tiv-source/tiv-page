<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    
    <struts:iterator value="events" status="eventsStatus">
      <struts:url var="eventLink" action="index" namespace="/event/%{uuid}"/>

      <struts:a href="%{eventLink}">
        <h4><struts:property value="getName(getText('language'))" /></h4>
        <p><struts:property value="price" /></p>
        <p><struts:property value="beginning" /></p>
        <p><struts:property value="ending" /></p>
        <p><struts:property value="deadline" /></p>
      </struts:a>
      


    </struts:iterator>
    

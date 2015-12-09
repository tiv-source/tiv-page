<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    

    
        <h4><struts:property value="event.getName(getText('language'))" /></h4>
        <p><struts:property value="event.price" /></p>
        <p><struts:property value="event.beginning" /></p>
        <p><struts:property value="event.ending" /></p>
        <p><struts:property value="event.deadline" /></p>
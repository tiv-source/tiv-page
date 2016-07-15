<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<struts:url var="locationOverviewLink" action="index" namespace="/%{event.location.uuid}"/>
<struts:url var="eventOverviewLink" action="list_edit" namespace="/%{event.location.uuid}"/>
<a href="<struts:property value="#locationOverviewLink" />" class="ui-btn ui-icon-home ui-btn-icon-left">Home</a>
<h1><struts:property value="event.getName(getText('language'))" /> am <struts:property value="%{getText('format.reservation.date',{event.beginning})}"/></h1>
<a href="<struts:property value="#eventOverviewLink" />" class="ui-btn ui-icon-back ui-btn-icon-right">Zurück</a>
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>


  <struts:url var="listCreateLink" action="list_create" namespace="/%{location.uuid}"/>
  <struts:url var="listEditLink"   action="list_edit" namespace="/%{location.uuid}"/>
  <struts:url var="listViewLink"   action="list_view" namespace="/%{location.uuid}"/>

  <div data-role="navbar" data-iconpos="top">
    <ul>
      <li><a href="<struts:property value="#listCreateLink" />" data-icon="calendar">Erfassen</a></li>
      <li><a href="<struts:property value="#listEditLink" />" data-icon="edit">Bearbeiten</a></li>
    </ul>
  </div>
  <br>
  <div data-role="navbar" data-iconpos="top">
    <ul>
      <li><a href="#anylink" data-icon="check">Abhaken</a></li>
      <li><a href="<struts:property value="#listViewLink" />" data-icon="bars">Ansehen</a></li>
    </ul>
  </div>
  <br>
  <div data-role="navbar" data-iconpos="top">
    <ul>
      <li><a href="#anylink" data-icon="user">Bestätigen</a></li>
    </ul>
  </div>
  <br>
  <div data-role="navbar" data-iconpos="top">
    <ul>
      <li><a href="#anylink" data-icon="user">Profil anzeigen</a></li>
      <li><a href="#anylink" data-icon="user">Passwort ändern</a></li>
    </ul>
  </div>
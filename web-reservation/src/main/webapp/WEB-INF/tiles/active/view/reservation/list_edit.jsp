<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjm" uri="/struts-jquery-mobile-tags"%>



<ul data-role="listview">

  <struts:iterator value="list" status="reservationStatus">
    <struts:url var="reservationLink" action="editForm" namespace="/reservation/%{uuid}"/>
  
  <li>
    <struts:a href="%{reservationLink}">
      <h4><struts:if test="gender">Frau</struts:if><struts:else>Herr</struts:else> <struts:property value="firstname" /> <struts:property value="lastname" /></h4>
      <p>Tel.: <struts:property value="telephone"/></p>
      <p>Uhrzeit: <struts:date name="time" format="HH:mm" /></p>
      <span class="ui-li-count"><struts:property value="quantity"/></span>
    </struts:a>
  </li>

    
  </struts:iterator>
  
</ul>
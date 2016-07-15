<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjm" uri="/struts-jquery-mobile-tags"%>


<ul data-role="listview">

  <struts:iterator value="list" status="eventStatus">
    <struts:url var="eventLink" action="reservation_list_view" namespace="/event/%{uuid}"/>
  
  <li>
    <struts:a href="%{eventLink}" cssStyle="padding-left: 10.5em;">
      <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.NORMAL.url" />" 
           style="max-height: 50%; max-width: 25%; padding-left: 1em; top: 25%; min-width: 140px;" />
      <h2><struts:property value="getName(getText('language'))" /></h2>
      <p>am <struts:date name="beginning" format="dd.MM.yyyy" /></p>
      <span class="ui-li-count" style="right: 7em;"><struts:property value="countReservation(uuid)"/></span>
      <span class="ui-li-count"><struts:property value="countQuantity(uuid)"/></span>
    </struts:a>
  </li>




  </struts:iterator>
  
</ul>

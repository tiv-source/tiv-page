<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjm" uri="/struts-jquery-mobile-tags"%>



<ul data-role="listview">

  <struts:iterator value="list" status="locationStatus">
    <struts:url var="locationLink" action="index" namespace="%{uuid}"/>
  
  <li>
    <struts:a href="%{locationLink}">
      <h4><struts:property value="getName(getText('language'))" /></h4>
      <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.NORMAL.url" />"  style="max-height: 65px; padding-top:0px;" />
    </struts:a>
  </li>

    
  </struts:iterator>
  
</ul>
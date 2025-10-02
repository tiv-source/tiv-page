<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

  <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

  <struts:iterator value="list" status="locationStatus">
    <struts:url var="locationLink" action="index" namespace="/reservation/%{uuid}"/>
    <struts:a href="%{locationLink}">

      <div class="informations <struts:if test="#locationStatus.odd == true ">color3</struts:if><struts:else>color4</struts:else>">
        <div class="information">
          <h4><struts:property value="getName(getText('language'))" /></h4>
          <p><struts:property value="address.street" /></p>
          <p><struts:property value="address.zip" /> <struts:property value="address.city" /></p>
        </div>
        
        <div class="impression">
          <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
        </div>
        <hr>
      </div>
    
    </struts:a>
  </struts:iterator>

    <hr>
  </div>
  <!-- Content Ende -->

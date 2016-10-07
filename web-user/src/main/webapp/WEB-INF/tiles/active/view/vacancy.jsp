<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <div>
    <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
  </div>

  <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

  <struts:iterator value="list" status="vacancyStatus">
    <struts:url var="vacancyLink" action="index" namespace="/vacancy/%{uuid}"/>
    <struts:a href="%{vacancyLink}">

      <div class="informations <struts:if test="#vacancyStatus.odd == true ">color3</struts:if><struts:else>color4</struts:else>">
        <div class="information">
          <h4><struts:property value="getName(getText('language'))" /></h4>
          <p><struts:property value="workingTime" /></p>
          <p><struts:property value="beginning" /></p>
          <p><struts:property value="location.getName(getText('language'))" /></p>
        </div>

        <div class="impression">
          <img src="/pictures/FULL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
        </div>
        <hr>
      </div>
    
    </struts:a>
  </struts:iterator>

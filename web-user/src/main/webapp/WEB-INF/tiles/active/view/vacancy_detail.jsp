<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <div>
    <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
  </div>

  <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />


    <struts:url var="vacancyLink" action="form" namespace="/vacancy/%{vacancy.uuid}"/>
    <struts:a href="%{vacancyLink}">

      <div class="informations color4">
        <div class="information">
          <h4><struts:property value="vacancy.getName(getText('language'))" /></h4>
          <p><struts:property value="vacancy.workingTime" /></p>
          <p><struts:property value="vacancy.beginning" /></p>
          <p><struts:property value="vacancy.location.getName(getText('language'))" /></p>
        </div>

        <div class="impression">
          <img src="/pictures/FULL/<struts:property value="vacancy.picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
        </div>
        <hr>
      </div>
    
    </struts:a>


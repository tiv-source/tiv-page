<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

  <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

  <struts:iterator value="list" status="vacancyStatus">
    <struts:url var="vacancyLink" action="index" namespace="/vacancy/%{uuid}"/>
    <struts:a href="%{vacancyLink}">

      <div class="informations <struts:if test="#vacancyStatus.odd == true ">color3</struts:if><struts:else>color4</struts:else>">
        <div class="information">
          <h4><struts:property value="getName(getText('language'))" /></h4>

          <p class="title">Arbeitszeit:</p>
          <p class="description"><struts:property value="workingTime" /></p>

          <p class="title">Anfang:</p>
          <p class="description">
            <struts:if test="%{now < beginning}">
              <struts:date name="beginning" format="dd.MM.yyyy" />
            </struts:if>
            <struts:else>
              ab Sofort
            </struts:else>
          </p>

          <p class="title">Filiale:</p>
          <p class="description"><struts:property value="location.getName(getText('language'))" /></p>

          <p class="title">Standort:</p>
          <p class="description"><struts:property value="location.address.city" /></p>
        </div>

        <div class="impression">
          <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
        </div>
        <hr>
      </div>
    
    </struts:a>
  </struts:iterator>

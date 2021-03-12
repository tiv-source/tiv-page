<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="appointment.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="appointment.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <h1><struts:property escapeHtml="false" value="appointment.getName(getText('language'))" /></h1>
    <struts:if test="getProperty('appointment.archive.pointInTime.nice') == 'true'">
      <h6><struts:date name="appointment.pointInTime" nice="true" /></h6>
    </struts:if>
    <h5><struts:property escapeHtml="false" value="appointment.getDescription(getText('language'))" /></h5>
    <struts:property escapeHtml="false" value="appointment.getContent(getText('language'))" />

    <h6>
      <struts:if test="getProperty('appointment.archive.pointInTime.time') == 'true'">
        Beginn um <struts:date name="appointment.pointInTime" format="HH:mm" />
      </struts:if>
      <struts:if test="getProperty('appointment.archive.pointInTime.date') == 'true'">
        am <struts:date name="appointment.pointInTime" format="dd.MM.yyyy" />
      </struts:if>
    </h6>

    <hr>
  </div>
  <!-- Content Ende -->

<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="appointment.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="appointment.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

    <h1><struts:property escapeHtml="false" value="appointment.getName(getText('language'))" /></h1>
    <h6><struts:date name="appointment.pointInTime" nice="true" /></h6>
    <h5><struts:property escapeHtml="false" value="appointment.getDescription(getText('language'))" /></h5>
    <struts:property escapeHtml="false" value="appointment.getContent(getText('language'))" />

    <h6>Beginn um <struts:date name="appointment.pointInTime" format="HH:mm" /> am <struts:date name="appointment.pointInTime" format="dd.MM.yyyy" /></h6>


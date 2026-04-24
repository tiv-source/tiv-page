<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="course.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="course.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id="content">
    <div id="successContent">
      <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />
    </div>
    <div id="courseInfo">
      <p id="successTitle">Sie haben sich erfolgreich in die Warteliste des folgenden Kurses:</p>
      <p id="successCourse"><struts:property value="course.number" /> - <struts:property value="course.getName(getText('language'))" /></p>
      <p id="successAfterCourse">eingetragen.</p>
      <p id="successFooter">Sobald ein Platz in dem Kurs frei wird werden wir uns mit Ihnen in Verbindung setzen.</p>
    </div>
  </div>
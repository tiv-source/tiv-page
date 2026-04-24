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
      <p id="successTitle">Ihre Anmeldung für den folgenden Kurs:</p>
      <p id="successCourse"><struts:property value="course.number" /> - <struts:property value="course.getName(getText('language'))" /></p>
      <p id="successAfterCourse">War erfolgreich.</p>
      <p id="successFooter">Wir werden Ihnen in den nächsten 24 Stunden per Mail eine Bestätigung mit den Zahlungsinformationen zusenden.</p>
    </div>
  </div>
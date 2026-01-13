<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="lecturer.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="lecturer.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="lecturer.getContent(getText('language'))" />

    <hr>
  </div>
  <!-- Content Ende -->


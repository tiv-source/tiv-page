<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:if test="location.event">
      <struts:include value="location_view_event.jsp" />
    </struts:if>
    <struts:else>
      <struts:include value="location_view_normal.jsp" />
    </struts:else>

    <hr>
  </div>
  <!-- Content Ende -->

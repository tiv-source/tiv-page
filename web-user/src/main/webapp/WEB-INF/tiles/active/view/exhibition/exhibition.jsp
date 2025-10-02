<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="exhibition.pictureOnPage">
    <div id="sitePicture">
      <img src="/image/pictureitem/<struts:property value="%{exhibition.uuid}"/>/<struts:property value="getProperty('exhibition.view.image')"/>" alt="<struts:property value="exhibition.getName(getText('language'))" />" title="<struts:property value="exhibition.getName(getText('language'))" />" />
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <h1><struts:property escapeHtml="false" value="exhibition.getName(getText('language'))" /></h1>
    <struts:if test="getProperty('exhibition.list.moment.nice') == 'true'">
      <h6><struts:date name="exhibition.moment" nice="true" /></h6>
    </struts:if>

    <struts:property escapeHtml="false" value="exhibition.getContent(getText('language'))" />

    <h6>
      <struts:if test="getProperty('exhibition.list.start') == 'true'">
        Die Ausstellung läuft vom <struts:date name="exhibition.start" format="dd.MM.yyyy" /> 
      </struts:if>
      <struts:if test="getProperty('exhibition.list.end') == 'true'">
        bis zum <struts:date name="exhibition.end" format="dd.MM.yyyy" /> 
      </struts:if>
    </h6>

    <hr>
  </div>
  <!-- Content Ende -->


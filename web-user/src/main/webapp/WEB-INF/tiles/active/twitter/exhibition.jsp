<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:if test="getProperty('twitter.card.enabled')">
  <meta name="twitter:card" content="summary_large_image">
  <meta name="twitter:site" content="<struts:property value="getProperty('twitter.site')" />" />
  <meta name="twitter:title" content="<struts:property value="page.getName(getText('language'))" />" />
  <meta name="twitter:description" content="<struts:property value="page.getDescription(getText('language'))" />" />
  <meta name="twitter:image" content="<struts:property value="getProperty('server.base.url')" />/image/pictureitem/<struts:property value="%{exhibition.uuid}"/>/<struts:property value="getProperty('og.image.exhibition')" />" />
</struts:if>

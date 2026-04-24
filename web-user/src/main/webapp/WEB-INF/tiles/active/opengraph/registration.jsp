<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:url var="canonicalUrl">
    <struts:param name="request_locale" ><struts:text name="language" /></struts:param>
  </struts:url>

  <meta property="og:type" content="<struts:property value="getProperty('og.type')" />" />
  <meta property="og:title" content="<struts:property value="page.getName(getText('language'))" />" />
  <meta property="og:url" content="<struts:property value="getProperty('server.base.url')" /><struts:property value="canonicalUrl" />" />
  <meta property="og:image" content="<struts:property value="getProperty('server.base.url')" />/image/pictureitem/<struts:property value="%{page.uuid}"/>/<struts:property value="getProperty('og.image.size.page')" />" />
  <meta property="og:description" content="<struts:property value="page.getDescription(getText('language'))" />" />
  <meta property="og:locale" content="<struts:text name="og.locale" />" />
  <meta property="og:locale:alternate" content="<struts:text name="og.locale.alternate" />" />
  <meta property="og:site_name" content="<struts:property value="getProperty('title')" />" />
  

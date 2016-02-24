<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <title>TIV Page - <struts:property value="location.getName(getText('language'))" /> <struts:text name="page" /> <struts:property value="current" /></title>
  <meta name="title" content="TIV Page - <struts:property value="location.getName(getText('language'))" /> <struts:text name="page" /> <struts:property value="current" />" />
  <meta name="searchtitle" content="TIV Page - <struts:property value="location.getName(getText('language'))" /> <struts:text name="page" /> <struts:property value="current" />" />
  <meta name="description" content="<struts:property value="location.getDescription(getText('language'))" /> <struts:text name="page" /> <struts:property value="current" />" />
  <meta name="abstract" content="<struts:property value="location.getDescription(getText('language'))" /> <struts:text name="page" /> <struts:property value="current" />" />
  <meta name="keywords" content="<struts:property value="location.getKeywords(getText('language'))" />" />
  <meta name="language" content="<struts:text name="language" />" />

  <struts:url var="canonicalUrl" />
  <link rel="canonical" href="<struts:text name="server.base.url" /><struts:property escape="false" value="canonicalUrl" />" />

  <meta property="og:title" content="<struts:property value="location.getName(getText('language'))" /> <struts:text name="page" /> <struts:property value="current" />" />
  <meta property="og:url" content="<struts:text name="server.base.url" /><struts:property escape="false" value="canonicalUrl" />" />
  <meta property="og:image" content="<struts:text name="server.picture.url" />/NORMAL/<struts:property value="location.picture.pictureUrls.NORMAL.url" />" />
  <meta property="og:description" content="<struts:property value="location.getDescription(getText('language'))" /> <struts:text name="page" /> <struts:property value="current" />" />


  <meta name="page-topic" content="<struts:text name="meta.page.topic" />" />
  <meta name="classification" content="<struts:text name="meta.classification" />" />
  <meta name="category" content="<struts:text name="meta.category" />" />

  <meta name="author" content="<struts:text name="meta.author" />" />
  <meta name="owner" content="<struts:text name="meta.owner" />" />
  <meta name="publisher" content="<struts:text name="meta.publisher" />" />
  <meta name="copyright" content="<struts:text name="meta.copyright" />" />
  <meta name="generator" content="<struts:text name="meta.generator" />" />

  <meta name="date" content="<struts:date name="location.created" format="yyyy-MM-dd" />T<struts:date name="location.created" format="HH:mm:ss" />+01:00" />
  <meta name="created" content="<struts:date name="location.created" format="yyyy-MM-dd" />T<struts:date name="location.created" format="HH:mm:ss" />+01:00" />
  <meta name="changed" content="<struts:date name="location.modified" format="yyyy-MM-dd" />T<struts:date name="location.modified" format="HH:mm:ss" />+01:00" />
  <meta name="modified" content="<struts:date name="location.modified" format="yyyy-MM-dd" />T<struts:date name="location.modified" format="HH:mm:ss" />+01:00" />


  <meta name="revisit-after" content="7 days" />
  <meta name="robots" content="INDEX,FOLLOW" />

  <meta http-equiv="content-language" content="<struts:text name="language" />" />  
  <meta http-equiv="imagetoolbar" content="false" />
  <meta http-equiv="pragma" content="no-cache" />
  <meta http-equiv="expires" content="0" />
  <meta http-equiv="cache-control" content="no-cache" />

  
  

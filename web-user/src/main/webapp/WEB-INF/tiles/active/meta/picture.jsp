<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <title><struts:property value="getProperty('title')" /> - <struts:property value="picture.getName(getText('language'))" /></title>
  <meta name="title" content="<struts:property value="getProperty('title')" /> - <struts:property value="picture.getName(getText('language'))" />" />
  <meta name="searchtitle" content="<struts:property value="getProperty('title')" /> - <struts:property value="picture.getName(getText('language'))" />" />
  <meta name="description" content="<struts:property value="picture.getDescription(getText('language'))" />" />
  <meta name="abstract" content="<struts:property value="picture.getDescription(getText('language'))" />" />
  <meta name="keywords" content="<struts:property value="picture.getKeywords(getText('language'))" />" />
  <meta name="language" content="<struts:text name="language" />" />

  <struts:url var="canonicalUrl">
    <struts:param name="request_locale" ><struts:text name="language" /></struts:param>
  </struts:url>
  <link rel="canonical" href="<struts:property value="getProperty('server.base.url')" /><struts:property value="canonicalUrl" />" />

  <meta property="og:title" content="<struts:property value="picture.getName(getText('language'))" />" />
  <meta property="og:url" content="<struts:property value="getProperty('server.base.url')" /><struts:property value="canonicalUrl" />" />
  <meta property="og:image" content="<struts:property value="getProperty('server.picture.url')" />/LARGE/<struts:property value="picture.pictureUrls.LARGE.url" />" />
  <meta property="og:description" content="<struts:property value="picture.getDescription(getText('language'))" />" />


  <meta name="page-topic" content="<struts:property value="getProperty('meta.page.topic')" />" />
  <meta name="classification" content="<struts:property value="getProperty('meta.classification')" />" />
  <meta name="category" content="<struts:property value="getProperty('meta.category')" />" />

  <meta name="author" content="<struts:property value="getProperty('meta.author')" />" />
  <meta name="owner" content="<struts:property value="getProperty('meta.owner')" />" />
  <meta name="publisher" content="<struts:property value="getProperty('meta.publisher')" />" />
  <meta name="copyright" content="<struts:property value="getProperty('meta.copyright')" />" />
  <meta name="generator" content="<struts:property value="getProperty('meta.generator')" />" />

  <meta name="date" content="<struts:date name="picture.created" format="yyyy-MM-dd" />T<struts:date name="picture.created" format="HH:mm:ss" />+01:00" />
  <meta name="created" content="<struts:date name="picture.created" format="yyyy-MM-dd" />T<struts:date name="picture.created" format="HH:mm:ss" />+01:00" />
  <meta name="changed" content="<struts:date name="picture.modified" format="yyyy-MM-dd" />T<struts:date name="picture.modified" format="HH:mm:ss" />+01:00" />
  <meta name="modified" content="<struts:date name="picture.modified" format="yyyy-MM-dd" />T<struts:date name="picture.modified" format="HH:mm:ss" />+01:00" />


  <meta name="revisit-after" content="7 days" />
  <meta name="robots" content="INDEX,FOLLOW" />

  <meta http-equiv="content-language" content="<struts:text name="language" />" />  

  <meta name="ICBM" content="<struts:property value="getProperty('icbm')" />" />
  <meta name="geo.position" content="<struts:property value="getProperty('geo.position')" />" />
  <meta name="geo.region" content="<struts:property value="getProperty('geo.region')" />" />
  <meta name="geo.placename" content="<struts:property value="getProperty('geo.placename')" />" />

  <script type="text/javascript" src="/public/raty/jquery.js"></script>
  <script type="text/javascript" src="/public/touch/jquery.touchSwipe.min.js"></script>

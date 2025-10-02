<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <title><struts:property value="getProperty('title')" /> - <struts:property value="page.getName(getText('language'))" /></title>
  <meta name="title" content="<struts:property value="getProperty('title')" /> - <struts:property value="page.getName(getText('language'))" />" />
  <meta name="searchtitle" content="<struts:property value="getProperty('title')" /> - <struts:property value="page.getName(getText('language'))" />" />
  <meta name="description" content="<struts:property value="page.getDescription(getText('language'))" />" />
  <meta name="abstract" content="<struts:property value="page.getDescription(getText('language'))" />" />
  <meta name="keywords" content="<struts:property value="page.getKeywords(getText('language'))" />" />
  <meta name="language" content="<struts:text name="language" />" />

  <struts:url var="canonicalUrl">
    <struts:param name="request_locale" ><struts:text name="language" /></struts:param>
  </struts:url>
  <link rel="canonical" href="<struts:property value="getProperty('server.base.url')" /><struts:property value="canonicalUrl" />" />

  <meta name="date" content="<struts:date name="page.created" format="yyyy-MM-dd" />T<struts:date name="page.created" format="HH:mm:ss" />+01:00" />
  <meta name="created" content="<struts:date name="page.created" format="yyyy-MM-dd" />T<struts:date name="page.created" format="HH:mm:ss" />+01:00" />
  <meta name="changed" content="<struts:date name="page.modified" format="yyyy-MM-dd" />T<struts:date name="page.modified" format="HH:mm:ss" />+01:00" />
  <meta name="modified" content="<struts:date name="page.modified" format="yyyy-MM-dd" />T<struts:date name="page.modified" format="HH:mm:ss" />+01:00" />

  <struts:if test="getProperty('home.appointment.slider')">
    <link rel="stylesheet" type="text/css" href="/css/slider.css">
    <style>
    .sliderElementsWidth {
      width: <struts:property value="100 * sliderList.size()" />%;
    }
    .sliderElementsWidth > li {
      width: <struts:property value="sliderWidth" />%;
    }
    </style>
  </struts:if>
  <struts:if test="getProperty('home.slider') == 'true'">
    <style>
    .homeSliderElementsWidth {
      width: <struts:property value="100 * homeSliderList.size()" />%;
    }
    .homeSliderElementsWidth > li {
      width: <struts:property value="homeSliderWidth" />%;
    }
    </style>
  </struts:if>

  <link rel="stylesheet" href="/public/responsiveslides/responsiveslides.css">
  <link rel="stylesheet" href="/public/responsiveslides/custom.css">
  <script src="/public/responsiveslides/responsiveslides.js"></script>


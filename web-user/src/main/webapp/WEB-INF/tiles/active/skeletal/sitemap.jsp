<%@page pageEncoding="utf-8" contentType="application/xml" %><?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">
<struts:iterator value="responsiveNavigation" status="responsiveNavigationStatus">
<struts:url var="responsiveNavigationLink" action="index" namespace="/%{technical.substring(3)}"/>
 <url>
  <loc><struts:property value="getProperty('server.base.url')" /><struts:property value="%{responsiveNavigationLink}" /></loc>
  <lastmod><struts:date name="modified" format="yyyy-MM-dd" /></lastmod>
  <changefreq>weekly</changefreq>
  <priority>0.5</priority>
 </url>
</struts:iterator>
<struts:iterator value="location" status="locationStatus">


 <struts:iterator begin="1" end="%{getMaxLocationPages(uuid)}">
 <struts:url var="locationLink" action="index" namespace="/reservation/%{uuid}">
   <struts:param name="page"><struts:property value="top" /></struts:param>
 </struts:url>
 <url>
  <loc><struts:property value="getProperty('server.base.url')" /><struts:property value="%{locationLink}" /></loc>
  <lastmod><struts:date name="modified" format="yyyy-MM-dd" /></lastmod>
  <changefreq>daily</changefreq>
  <priority>0.6</priority>
 </url>
 </struts:iterator>

 <struts:iterator value="%{getEvents(uuid)}" status="eventStatus">
 <struts:url var="eventLink" action="index" namespace="/event/%{uuid}"/>
 <url>
  <loc><struts:property value="getProperty('server.base.url')" /><struts:property value="%{eventLink}" /></loc>
  <lastmod><struts:date name="modified" format="yyyy-MM-dd" /></lastmod>
  <changefreq>daily</changefreq>
  <priority>0.7</priority>
 </url>
 </struts:iterator>

</struts:iterator>
</urlset>
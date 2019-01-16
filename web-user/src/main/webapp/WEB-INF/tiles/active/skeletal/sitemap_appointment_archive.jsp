<%@page pageEncoding="utf-8" contentType="application/xml" %><?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">
<struts:iterator value="appointments" status="manualStatus">
 <struts:url var="appointmentLink" action="index" namespace="/appointmentarchive/%{uuid}"/>
 <url>
  <loc><struts:property value="getProperty('server.base.url')" /><struts:property value="%{appointmentLink}" /></loc>
  <lastmod><struts:date name="modified" format="yyyy-MM-dd" /></lastmod>
  <changefreq>daily</changefreq>
  <priority>0.7</priority>
 </url>
</struts:iterator>
</urlset>
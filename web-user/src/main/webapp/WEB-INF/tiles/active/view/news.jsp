<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>

    <h1><struts:property escapeHtml="false" value="news.getName(getText('language'))" /></h1>
    <h6><struts:date name="news.releaseDate" format="dd.MM.yyyy" /></h6>
    <p><struts:property escapeHtml="false" value="news.getDescription(getText('language'))" /></p>
    <struts:property escapeHtml="false" value="news.getContent(getText('language'))" />
    

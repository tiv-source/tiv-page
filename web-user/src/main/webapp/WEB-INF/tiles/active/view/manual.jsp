<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="manual.pictureOnPage">
    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="manual.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

    <h1><struts:property escapeHtml="false" value="manual.getName(getText('language'))" /></h1>
    <h6><struts:date name="manual.created" format="dd.MM.yyyy" /></h6>
    <h5><struts:property escapeHtml="false" value="manual.getDescription(getText('language'))" /></h5>
    <div class="ads" style="max-width:1230px; margin: 20px auto; padding: 0px 15px;">
      <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
      <!-- Comarchiv -->
      <ins class="adsbygoogle" style="display:block" data-ad-client="ca-pub-0384480293276302" data-ad-slot="9662011858" data-ad-format="auto"></ins>
      <script>(adsbygoogle = window.adsbygoogle || []).push({});</script>
    </div>
    <struts:property escapeHtml="false" value="manual.getContent(getText('language'))" />
    

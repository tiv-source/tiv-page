<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:if test="getProperty('facebook.on.pages') == 'true'">
<div id="fb-root"></div>
<script>
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/de_DE/sdk.js#xfbml=1&version=v2.5";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>
</struts:if>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="page.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

  <struts:if test="getProperty('facebook.on.pages') == 'true'">
    <div id="social_net1">
      <div class="picturebox1">
        <div class="fb-page" data-href="<struts:property value="getProperty('facebook.page.url')"/>" data-tabs="timeline" data-width="<struts:property value="getProperty('facebook.on.pages.data.width')"/>"" data-height="<struts:property value="getProperty('facebook.on.pages.data.height')"/>" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="false">
          <div class="fb-xfbml-parse-ignore">
            <blockquote cite="<struts:property value="getProperty('facebook.page.url')"/>">
              <a href="<struts:property value="getProperty('facebook.page.url')"/>"><struts:property value="getProperty('facebook.page.name')"/></a>
            </blockquote>
          </div>
        </div>
      </div>
    </div>
  </struts:if>

  <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

    <hr>
  </div>
  <!-- Content Ende -->

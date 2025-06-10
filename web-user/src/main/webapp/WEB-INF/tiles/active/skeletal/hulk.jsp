<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!doctype html>
<html lang="<struts:text name="language"/>">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <script src="/public/jquery/jquery-3.3.1.min.js"></script>

  <tiles:insertAttribute name="meta" />
  <tiles:insertAttribute name="opengraph" />
  <tiles:insertAttribute name="geo" />
  <tiles:insertAttribute name="twitter" />

  <!-- Anfang der CSS-Dateien -->
  <struts:iterator value="page.cssGroup.files" status="cssFilesStatus">
    <!-- <struts:property value="name" /> -->
    <link rel="stylesheet" type="text/css" href="/cssfile/<struts:property value="uuid" />.css">
  </struts:iterator>
  <!-- Ende der CSS-Dateien -->

  <link rel="shortcut icon" href="<struts:property value="getProperty('favicon.ico.path')"/>" type="image/vnd.microsoft.icon" />
  <link rel="shortcut icon" href="<struts:property value="getProperty('favicon.png.path')"/>" type="image/png" />

  

  <link rel="stylesheet" type="text/css" href="/public/cookieconsent/cookieconsent.min.css" />
  <script src="/public/cookieconsent/cookieconsent.min.js"></script>

</head>
<body>

<struts:if test="getProperty('socialmedia.icons') == 'true'">
  <div id="socialmedia">
    <struts:if test="getProperty('socialmedia.facebook.url') != ''">
      <a href="<struts:property value="getProperty('socialmedia.facebook.url')"/>" target="_blank">
         <img id="facebook" src="<struts:property value="getProperty('socialmedia.facebook.url.icon')"/>" alt="<struts:property value="getProperty('socialmedia.facebook.url.icon.alt')"/>">
      </a>
    </struts:if>

    <struts:if test="getProperty('socialmedia.instagram.url') != ''">
      <a href="<struts:property value="getProperty('socialmedia.instagram.url')"/>" target="_blank">
        <img id="instagram" src="<struts:property value="getProperty('socialmedia.instagram.url.icon')"/>" alt="<struts:property value="getProperty('socialmedia.instagram.url.icon.alt')"/>">
      </a>
    </struts:if>

    <struts:if test="getProperty('socialmedia.twitter.url') != ''">
      <a href="<struts:property value="getProperty('socialmedia.twitter.url')"/>" target="_blank">
        <img id="twitter" src="<struts:property value="getProperty('socialmedia.twitter.url.icon')"/>" alt="<struts:property value="getProperty('socialmedia.twitter.url.icon.alt')"/>">
      </a>
    </struts:if>

    <struts:if test="getProperty('socialmedia.mastodon.url') != ''">
      <a rel="me" href="<struts:property value="getProperty('socialmedia.mastodon.url')"/>" target="_blank">
        <img id="mastodon" src="<struts:property value="getProperty('socialmedia.mastodon.url.icon')"/>" alt="<struts:property value="getProperty('socialmedia.mastodon.url.icon.alt')"/>">
      </a>
    </struts:if>

    <struts:if test="getProperty('socialmedia.whatsapp.url') != ''">
      <a href="<struts:property value="getProperty('socialmedia.whatsapp.url')"/>" target="_blank">
        <img id="whatsapp" src="<struts:property value="getProperty('socialmedia.whatsapp.url.icon')"/>" alt="<struts:property value="getProperty('socialmedia.whatsapp.url.icon.alt')"/>">
      </a>
    </struts:if>
  </div>
</struts:if>


<div id="container">
  <!-- Top Anfang -->

<struts:if test="getProperty('i18n') == 'true'">
  <!-- Language Anfang -->
  <struts:url var="localeDE">
    <struts:param name="request_locale" >de</struts:param>
  </struts:url>
  <struts:url var="localeEN">
    <struts:param name="request_locale" >en</struts:param>
  </struts:url>
  <ul id="flags">
    <li><struts:a href="%{localeDE}"><img src="/public/icons/flag-germany.png" alt="Deutsch" title="Deutsch"></struts:a></li>
    <li><struts:a href="%{localeEN}"><img src="/public/icons/flag-united_kingdom.png" alt="English" title="English"></struts:a></li>
  </ul>
  <!-- Language Ende -->
</struts:if>

  <!-- Topnav Anfang -->
  <ul id="top_navi">
    <struts:iterator value="topNavigation" status="topNavigationStatus">
      <struts:url var="topnavlink" value="%{url}"/>
        <struts:if test="page.technical == technical.substring(3)">
          <li class="activlink3">
            <struts:property value="getName(getText('language'))" />
          </li>
        </struts:if>
        <struts:else>
          <li>
            <struts:a href="%{topnavlink}">
              <struts:property value="getName(getText('language'))" />
            </struts:a>
          </li>
        </struts:else>
    </struts:iterator>
  </ul>
  <!-- Topnav Ende -->
  <!-- Top Ende -->

  <!-- Header Anfang -->
  <div id="logo">
    <struts:url var="homeUrl" action="index" namespace="/" />
    <struts:a href="%{homeUrl}">
      <img id="logo_image" src="<struts:property value="getProperty('logo.path')"/>" alt="<struts:property value="getProperty('logo.alt')"/>">
    </struts:a>
  </div>
  <!-- Header Ende -->			
		
   <div id="mobile_navi">
     <label for="checkbox">
       <span class="ellipsis"><struts:property value="page.getName(getText('language'))" /></span><img src="<struts:property value="getProperty('menu.icon.path')"/>" alt="menu">
     </label>
     <input id="checkbox"  type="checkbox" />
     <ul class="mobile_sub">
       <struts:iterator value="responsiveNavigation" status="responsiveNavigationStatus">
         <struts:url var="responsivenavlink" value="%{url}"/>
         <struts:if test="page.technical == technical.substring(3)">
           <li class="activlink2">
             <struts:property value="getName(getText('language'))" />
           </li>
         </struts:if>
         <struts:else>
           <li>
             <struts:a href="%{responsivenavlink}">
               <struts:property value="getName(getText('language'))" />
             </struts:a>
           </li>
         </struts:else>
       </struts:iterator>
     </ul>		
   </div>     

  <!-- Navigation Anfang -->
  <ul id="main_navi">
      <struts:iterator value="navigation" status="navigationStatus">
        <struts:url var="navlink" value="%{url}"/>
          <struts:if test="page.technical == technical.substring(3)">
            <li class="activlink1">
              <struts:property value="getName(getText('language'))" />
            </li>
          </struts:if>
          <struts:else>
            <li>
              <struts:a href="%{navlink}">
                <struts:property value="getName(getText('language'))" />
              </struts:a>
            </li>
          </struts:else>
      </struts:iterator>
  </ul>
  <!-- Navigation Ende -->

  <tiles:insertAttribute name="content" />

  <!-- Bottomnav Anfang -->
  <ul id="bottom_navi">
  <struts:iterator value="bottomNavigation" status="bottomNavigationStatus">
    <struts:url var="bottomnavlink" value="%{url}"/>
    <struts:if test="page.technical == technical.substring(3)">
      <li class="activlink3">
        <struts:property value="getName(getText('language'))" />
      </li>
    </struts:if>
    <struts:else>
      <li>
        <struts:a href="%{bottomnavlink}">
          <struts:property value="getName(getText('language'))" />
        </struts:a>
      </li>
    </struts:else>
  </struts:iterator>
  </ul>
  <!-- Bottomnav Ende -->

	
</div>

<tiles:insertAttribute name="tracking" />

<struts:hidden id="cookieConsentPosition" name="cookieConsentPositionName" value="%{getProperty('cookieconsent.position')}" theme="simple"/>
<struts:hidden id="cookieConsentPopupBackground" name="cookieConsentPopupBackgroundName" value="%{getProperty('cookieconsent.popup.background')}" theme="simple"/>
<struts:hidden id="cookieConsentButtonBackground" name="cookieConsentButtonBackgroundName" value="%{getProperty('cookieconsent.button.background')}" theme="simple"/>
<struts:hidden id="cookieConsentContentMessage" name="cookieConsentContentMessageName" value="%{getProperty('cookieconsent.content.message')}" theme="simple"/>
<struts:hidden id="cookieConsentContentDismiss" name="cookieConsentContentDismissName" value="%{getProperty('cookieconsent.content.dismiss')}" theme="simple"/>
<struts:hidden id="cookieConsentContentLink" name="cookieConsentContentLinkName" value="%{getProperty('cookieconsent.content.link')}" theme="simple"/>
<struts:hidden id="cookieConsentContentHref" name="cookieConsentContentHrefName" value="%{getProperty('cookieconsent.content.href')}" theme="simple"/>
<struts:hidden id="cookieConsentContentTarget" name="cookieConsentContentTargetName" value="%{getProperty('cookieconsent.content.target')}" theme="simple"/>
<script>
window.addEventListener("load", function(){
window.cookieconsent.initialise({
  "palette": {
    "popup": {
      "background": $("#cookieConsentPopupBackground").val()
    },
    "button": {
      "background": $("#cookieConsentButtonBackground").val()
    }
  },
  "position": $("#cookieConsentPosition").val(),
  "content": {
    "message": $("#cookieConsentContentMessage").val(),
    "dismiss": $("#cookieConsentContentDismiss").val(),
    "link": $("#cookieConsentContentLink").val(),
    "href": $("#cookieConsentContentHref").val(),
    "target": $("#cookieConsentContentTarget").val()
  }
})});
</script>

</body>
</html>

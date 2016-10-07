<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!doctype html>
<html lang="<struts:text name="language"/>">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <tiles:insertAttribute name="meta" />
  <tiles:insertAttribute name="twitter" />

  <link rel="stylesheet" type="text/css" href="/public/css/reset.css">
  <link rel="stylesheet" type="text/css" href="/public/css/main_style.css">
  <link rel="stylesheet" type="text/css" href="/public/css/tiv_page.css">
  <link rel="stylesheet" type="text/css" href="/public/css/tiv_page_sitethemes.css">
  <link rel="stylesheet" type="text/css" href="/public/css/tiv_page_actionthemes.css">
  <link rel="stylesheet" type="text/css" href="/public/css/tiv_page_gallery.css">
  <link rel="stylesheet" type="text/css" href="/public/css/tiv_page_formulars.css">

  <link rel="shortcut icon" href="<struts:property value="getProperty('favicon.ico.path')"/>" type="image/vnd.microsoft.icon" />
  <link rel="shortcut icon" href="<struts:property value="getProperty('favicon.png.path')"/>" type="image/png" />

</head>
<body>

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
      <struts:url var="topnavlink" action="index" namespace="/%{technical}"/>
        <struts:if test="page.technical == technical">
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
      <img src="<struts:property value="getProperty('logo.path')"/>" alt="TIV-Page-Logo">
    </struts:a>
  </div>
  <!-- Header Ende -->			
		
   <div id="mobile_navi">
     <label for="checkbox">
       <span class="ellipsis"><struts:property value="page.getName(getText('language'))" /></span><img src="/public/icons/menu-icon_grau_klein.png" alt="menu">
     </label>
     <input id="checkbox"  type="checkbox" />
     <ul class="mobile_sub">
       <struts:iterator value="responsiveNavigation" status="responsiveNavigationStatus">
         <struts:url var="responsivenavlink" action="index" namespace="/%{technical}"/>
         <struts:if test="page.technical == technical">
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
        <struts:url var="navlink" action="index" namespace="/%{technical}"/>
          <struts:if test="page.technical == technical">
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

  <!-- Content Anfang -->
  <div id=content> 
    <tiles:insertAttribute name="content" />
  </div>
  <!-- Content Ende -->

  <!-- Bottomnav Anfang -->
  <ul id="bottom_navi">
  <struts:iterator value="bottomNavigation" status="bottomNavigationStatus">
    <struts:url var="bottomnavlink" action="index" namespace="/%{technical}"/>
    <struts:if test="page.technical == technical">
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

</body>
</html>

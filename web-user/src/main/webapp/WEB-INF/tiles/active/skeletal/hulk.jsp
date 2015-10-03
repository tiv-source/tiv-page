<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
  <title>TIV Page</title>
  <link href="/public/css/main_style.css" rel="stylesheet" type="text/css">
  <link rel="shortcut icon" href="tp_logo_001/tp_logo_016.ico" type="image/x-icon" />
</head>
<body>

<div id="container">
  <!-- Top Anfang -->
  <!-- Language Anfang -->
  <struts:url id="localeDE">
    <struts:param name="request_locale" >de</struts:param>
  </struts:url>
  <struts:url id="localeEN">
    <struts:param name="request_locale" >en</struts:param>
  </struts:url>
  <ul id="flaggen">
    <li><struts:a href="%{localeDE}"><img src="/public/icons/flags-germany.png" alt="Deutsch" title="Deutsch"></struts:a></li>
    <li><struts:a href="%{localeEN}"><img src="/public/icons/flags-united_kingdom.png" alt="English" title="English"></struts:a></li>
  </ul>
  <!-- Language Ende -->

  <!-- Topnav Anfang -->
  <ul id="top_navi">
    <struts:iterator value="topNavigation" status="topNavigationStatus">
      <struts:url var="topnavlink" action="index" namespace="/%{technical}"/>
      <li>
        <struts:a href="%{topnavlink}">
          <struts:property value="getName(getText('language'))" />
        </struts:a>
      </li>
    </struts:iterator>
  </ul>
  <!-- Topnav Ende -->
  <!-- Top Ende -->

  <!-- Header Anfang -->
  <div id="logo">
    <h1><a id="oben">TIV Page</a></h1>
  </div>
  <!-- Header Ende -->			
		
   <div id="subm">   
   	<ul id="navi_subm">
			<li class="lfloat">
			  <span class="icon">
			    <img src="bilder/menu-icon_olive_klein.svg" alt="menu">
			  </span>
			    <a href="index.html"><br>&nbsp;&nbsp;&nbsp;Home</a>
		      <ul class="submenue">
              <struts:iterator value="responsiveNavigation" status="responsiveNavigationStatus">
                <struts:url var="responsivenavlink" action="index" namespace="/%{technical}"/>
                <li>
                  <struts:a href="%{responsivenavlink}">
                    <struts:property value="getName(getText('language'))" />
                  </struts:a>
                </li>
              </struts:iterator>
		      </ul>		
			</li>		
   	</ul>
   </div>     

  <!-- Navigation Anfang -->
  <ul id="main_navi">
      <struts:iterator value="navigation" status="navigationStatus">
        <struts:url var="navlink" action="index" namespace="/%{technical}"/>
        <li>
          <struts:a href="%{navlink}">
            <struts:property value="getName(getText('language'))" />
          </struts:a>
        </li>
      </struts:iterator>
  </ul>
  <!-- Navigation Ende -->

  <!-- Content Anfang -->
  <div  id=content> 
    <tiles:insertAttribute name="content" />
  </div>
  <!-- Content Ende -->

  <!-- Bottomnav Anfang -->
  <ul id="sub_navi">
  <struts:iterator value="bottomNavigation" status="bottomNavigationStatus">
    <struts:url var="bottomnavlink" action="index" namespace="/%{technical}"/>
      <li>
        <struts:a href="%{bottomnavlink}">
          <struts:property value="getName(getText('language'))" />
        </struts:a>
      </li>
  </struts:iterator>
  </ul>
  <!-- Bottomnav Ende -->


	
</div>

</body>
</html>

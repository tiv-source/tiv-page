<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

  <link media="screen" rel="stylesheet" type="text/css" href="/public/css/main.css" />

</head>
<body>
<div class="container">

  <!-- Top Anfang -->
  <div class="top">
    <!-- Language Anfang -->
    <struts:url id="localeDE">
      <struts:param name="request_locale" >de</struts:param>
    </struts:url>
    <struts:url id="localeEN">
      <struts:param name="request_locale" >en</struts:param>
    </struts:url>
    <div class="language">
      <struts:a href="%{localeDE}">
        <div class="languageitem"><struts:text name="DE"/></div>
      </struts:a>
      <struts:a href="%{localeEN}">
        <div class="languageitem"><struts:text name="EN"/></div>
      </struts:a>
    </div>
    <!-- Language Ende -->

    <!-- Topnav Anfang -->
    <div class="topnav">
      <struts:iterator value="topNavigation" status="topNavigationStatus">
        <struts:url var="topnavlink" action="index" namespace="/%{technical}"/>
        <struts:a href="%{topnavlink}">
          <div class="topnavitem">
            <struts:property value="getName(getText('language'))" />
          </div>
        </struts:a>
      </struts:iterator>
    </div>
    <!-- Topnav Ende -->
  </div>
  <!-- Top Ende -->

  <!-- Header Anfang -->
  <div class="header">
    <h1>TIV-Page</h1>
  </div>
  <!-- Header Ende -->

  <!-- Navigation Anfang -->
  <div class="nav">
      <struts:iterator value="navigation" status="navigationStatus">
        <struts:url var="navlink" action="index" namespace="/%{technical}"/>
        <struts:a href="%{navlink}">
          <div class="navitem">
            <struts:property value="getName(getText('language'))" />
          </div>
        </struts:a>
      </struts:iterator>
  </div>
  <!-- Navigation Ende -->

  <!-- Content Anfang -->
  <div class="content">
    <div class="wrapper">
      <tiles:insertAttribute name="content" />
    </div>
  </div>
  <!-- Content Ende -->

  <!-- Bottomnav Anfang -->
  <div class="bottomnav">
    <struts:iterator value="bottomNavigation" status="bottomNavigationStatus">
    <struts:url var="bottomnavlink" action="index" namespace="/%{technical}"/>
      <struts:a href="%{bottomnavlink}">
        <div class="bottomnavitem">
          <struts:property value="getName(getText('language'))" />
        </div>
      </struts:a>
    </struts:iterator>
  </div>
  <!-- Bottomnav Ende -->

</div>

</body>
</html>
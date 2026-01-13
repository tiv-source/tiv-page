<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <!-- Anfang der CSS-Dateien -->
  <struts:iterator value="page.cssGroup.files" status="cssFilesStatus">
    <!-- <struts:property value="name" /> -->
    <link rel="stylesheet" type="text/css" href="/cssfile/<struts:property value="uuid" />.css">
  </struts:iterator>
  <!-- Ende der CSS-Dateien -->
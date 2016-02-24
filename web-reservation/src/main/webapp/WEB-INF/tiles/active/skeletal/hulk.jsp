<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjm" uri="/struts-jquery-mobile-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>TIV-Page Administration</title>
  <sjm:head jqueryui="false" jquerytheme="redmond" compressed="true"/>
  

</head>
<body>
  <sjm:div role="page" id="textfieldpage">
    <sjm:div role="header">
      <h1>Examples for Textfield Tag</h1>
      
    </sjm:div>

    <sjm:div role="content">
      <tiles:insertAttribute name="content" />
    </sjm:div>

    <sjm:div role="footer">

    </sjm:div>
  </sjm:div>
</body>
</html>
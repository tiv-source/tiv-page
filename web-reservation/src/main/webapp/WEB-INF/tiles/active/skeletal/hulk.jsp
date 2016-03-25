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
  <title>TIV-Page Reservierung</title>
  <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
  <script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
  <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
  

</head>
<body>
  <div data-role="page">
  
  
    <div data-role="header" data-position="fixed" data-fullscreen="false">
      <tiles:insertAttribute name="header" />
    </div>

    <div data-role="main" class="ui-content">
      <tiles:insertAttribute name="content" />
    </div>

    <div data-role="footer" data-position="fixed" data-fullscreen="false">
     <tiles:insertAttribute name="footer" />
    </div>

  </div>
</body>
</html>
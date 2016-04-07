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
  <link rel="stylesheet" href="/reservation/jquery/jquery.mobile-1.4.5.min.css">
  <script src="/reservation/jquery/jquery-1.11.3.min.js"></script>
  <script src="/reservation/jquery/jquery.mobile-1.4.5.min.js"></script>
  <style type="text/css">
    .field label {
      display: block;
      float: left;
      margin: 0;
      width: 154px;
      font-weight: bold;
    }
    .field {
      min-height: 42px;
    }
  </style>
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
  <script type="text/javascript">
  $(document).bind("mobileinit", function(){
    $.mobile.page.prototype.options.domCache = false;
  });
  </script>
</body>
</html>
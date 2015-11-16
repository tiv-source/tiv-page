<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

  
  
  <sj:head jqueryui="true" jquerytheme="redmond"/>

  <link rel="stylesheet" type="text/css" href="/admin/css/reset.css" media="screen" />
  <link rel="stylesheet" type="text/css" href="/admin/css/layout.css" media="screen" />
  <link rel="stylesheet" type="text/css" href="/admin/css/navigation.css" media="screen" />
  <link rel="stylesheet" type="text/css" href="/admin/css/screen.css" media="screen" />
  <link rel="stylesheet" type="text/css" href="/admin/css/forms.css" media="screen" />

  <link rel="stylesheet" type="text/css" href="/admin/css/jquery.custom.css" media="screen" />

  <link rel="stylesheet" type="text/css" href="/admin/chosen/chosen.css" media="screen" />
  <script type="text/javascript" src="/admin/chosen/chosen.jquery.min.js"></script>

  <script type="text/javascript" src="/admin/jquery/persist-min.js"></script>
  <script type="text/javascript" src="/admin/jquery/jquery.scrollTo-1.4.2-min.js"></script>
  <script type="text/javascript" src="/admin/jquery/jquery.jgrowl.js"></script>
  <script type="text/javascript" src="/admin/jquery/jquery.qtip.js"></script>
  <script type="text/javascript" src="/admin/jquery/infiniteCarousel.js"></script>
    
  <script type="text/javascript" src="/admin/jquery/bluebox.js"></script>
  
  <link rel="shortcut icon" href="/admin/icons/tiv_page_favicon.ico" type="image/vnd.microsoft.icon" />
  <link rel="shortcut icon" href="/admin/icons/tiv_page_favicon.png" type="image/png" />
  <title>TIV-Page Administration</title>


</head>
<body>
<div class="container">
  <div class="header">
    <div style="width:475px; float:left;">
      <img src="/admin/images/tiv_page_logo_admin.png" style="width:280px; padding-left: 50px; padding-top: 21px;"/>
    </div>
    <div style="width:475px; float:right;">
      <div style="padding-top:3px; padding-right:28px; text-align:right;">
        Logged in as <%=request.getRemoteUser()%> - <a href="/admin/logout.html">Logout</a>
      </div>
    </div>
  </div>
  <div class="content">
    <div class="wrapper">
      <s:include value="navigation.jsp"/>
      <tiles:insertAttribute name="content" />
    </div>
  </div>
</div>




</body>
</html>
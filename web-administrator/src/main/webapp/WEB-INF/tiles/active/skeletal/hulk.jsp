<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!doctype html>
<html>
<head>
  <tiles:insertAttribute name="meta" />
</head>

<body>
  <div id="container">

    <div id="header">
      <div id="logo">
        <a href="/admin/index.html"><img src="/admin/images/tiv_page_logo_admin.png" alt="TIV-Page_Admin-Logo"></a>
      </div>

      <ul id="loggin">
        <li><a href="/admin/logout.html">Logout</a></li>
        <li>Logged in as <%=request.getRemoteUser()%></li>
      </ul>
    </div>

   	<div id=content>
   	  <tiles:insertAttribute name="navigation" />
   	  <tiles:insertAttribute name="content" />
    </div>
  </div>
</body>
</html>
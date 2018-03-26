<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!doctype html>
<html>
<head>
  <tiles:insertAttribute name="meta" />

  <script type="text/javascript">
    var seconds = 600;
    var redirect = "/admin/logout.html";

    function countdown() {
      var countdown = document.getElementById("countdown");
      if(seconds > 0){
    	seconds--;
    	countdown.innerHTML = "Logout in " + seconds + " seconds.";
    	setTimeout("countdown()", 1000);
      } else {
    	window.location.href = redirect;
      }
    }


    var trackMouse = function (mouseEvent) {
    	seconds = 600;
    };
    document.addEventListener('click', trackMouse);
    document.addEventListener('mousemove', trackMouse);
    

    </script>

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
      <span id="countdown" style="float: right; height: 14px; width: 280px; font-size: 13px; padding-right: 28px; padding-top: 3px;">
        <script type="text/javascript">countdown();</script>
      </span>
    </div>

   	<div id=content>
   	  <tiles:insertAttribute name="navigation" />
   	  <tiles:insertAttribute name="content" />
    </div>
  </div>
</body>
</html>
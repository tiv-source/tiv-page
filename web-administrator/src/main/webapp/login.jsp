<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>TIV-Page Administration</title>
<style type="text/css">
html, body {
    height: 75%;
}
.main {
    height: 70%;
    width: 100%;
    display: table;
}
.wrapper {
    display: table-cell;
    height: 100%;
    vertical-align: middle;
}
</style>
</head>
<body>
<div class="main">
  <div class="wrapper">
    <div style="width:350px; margin: 0 auto;">
      <div style="font-weight: bold; padding-bottom: 14px; padding-left: 7px; font-size: 14px;">Administration</div>
      <div style="border: 1px solid black; width:350px;">
	    <form method="post" action="j_security_check">
        <table style="width:100%; padding: 22px;">
          <tr>
            <td>
              Login:
	        </td>
            <td>
              <input type="text" name="j_username" style="width:200px;"/>
	        </td>
	      </tr>
          <tr>
            <td>
	          Passwort:
            </td>
            <td>
              <input type="password" name="j_password" style="width:200px;"/>
            </td>
          </tr>
          <tr>
            <td colspan="2" style="text-align:right;">
              <input type="submit" name="login" value="Login" />
            </td>
	      </tr>
	    </table>
	    </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
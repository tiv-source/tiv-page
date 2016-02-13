<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <title>Logout</title>
 <meta http-equiv="refresh" content="5; URL=/admin/index.html" />
</head>
<body>
<br />
<div style="text-align:center;">
User '<%=request.getRemoteUser()%>' has been logged out.<br /><br />
<% session.invalidate(); %>
Sie werden in 5 Sekunden auf die Startseite geleitet.
</div>
</body>
</html>
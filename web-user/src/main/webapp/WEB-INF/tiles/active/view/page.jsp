<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div class="main">
      <struts:property escape="false" value="page.getContent(getText('language'))" />
    </div>

<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="event.pictureOnPage">
    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="event.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

    <h1>Reservierung <struts:property value="event.location.getName(getText('language'))" /></h1>
    <h6><struts:property value="event.getName(getText('language'))" /> am <struts:date name="event.beginning" format="dd.MM.yyyy" /></h6>
    <p>Ihre Reservierungsanfrage f&uuml;r die gew&uuml;nschte Veranstaltung ist bei uns eingegangen.</p>
    <p>Sobald wir Ihnen die Reservierung best&auml;tigen k&ouml;nnen, werden wir Sie unter der angegebenen E-Mail benachrichtigen.</p>
    <p>Wir bedanken und freuen uns auf Ihren Besuch!</p>
    

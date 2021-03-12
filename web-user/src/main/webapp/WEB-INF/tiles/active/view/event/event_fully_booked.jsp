<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="event.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="event.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

    <h1>Reservierung <struts:property value="event.location.getName(getText('language'))" /></h1>
    <h6><struts:property value="event.getName(getText('language'))" /> am <struts:date name="event.beginning" format="dd.MM.yyyy" /></h6>
    <p><span class="red">Eine Online Reservierung ist f&uuml;r diesen Termin leider nicht mehr m&ouml;glich, da wir an diesem Termin leider ausgebucht sind.</span></p>
    <p>Eventuell hat jemand seine Reservierung storniert, dies ist leider nicht über das Online-System ersichtlich, f&uuml;r weitere Ausk&uuml;nfte rufen Sie uns bitte unter der folgenden Nummer an: <struts:a href="%{event.location.contactDetails.telephoneAsLink}"><struts:property value="event.location.contactDetails.telephone" /></struts:a></p>

    

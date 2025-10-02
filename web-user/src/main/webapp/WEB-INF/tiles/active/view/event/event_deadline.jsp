<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="event.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="event.picture.pictureUrls.FULL.url" />">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <h1>Reservierung <struts:property value="event.location.getName(getText('language'))" /></h1>
    <h6><struts:property value="event.getName(getText('language'))" /> am <struts:date name="event.beginning" format="dd.MM.yyyy" /></h6>
    <p><span class="red">Eine Online Reservierung ist f&uuml;r diesen Termin leider nicht mehr m&ouml;glich.</span></p>
    <p>Wenn Sie dennoch einen Tisch reservieren m&ouml;chten, so rufen Sie uns bitte unter der folgenden Nummer an: <struts:a href="%{event.location.contactDetails.telephoneAsLink}"><struts:property value="event.location.contactDetails.telephone" /></struts:a></p>

    <hr>
  </div>
  <!-- Content Ende -->

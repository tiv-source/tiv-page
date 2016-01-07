<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div>
      <img alt="" src="/uploads/<struts:property value="event.picture" />" style="width: 100%;" />
    </div>
    <h1>Reservierung <struts:property value="event.location.getName(getText('language'))" /></h1>
    <h6><struts:property value="event.getName(getText('language'))" /> am <struts:date name="event.beginning" format="dd.MM.yyyy" /></h6>
    <p><span class="red">Eine Online Reservierung ist f&uuml;r diesen Termin leider nicht mehr m&ouml;glich.</span></p>
    <p>Wenn Sie dennoch einen Tisch reservieren m&ouml;chten, so rufen Sie uns bitte unter der folgenden Nummer an: <struts:property value="event.location.contactDetails.telephone" /></p>

    

<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div>
      <img alt="" src="/uploads/<struts:property value="event.picture" />" style="width: 100%;" />
    </div>
    <h1><struts:property value="event.getName(getText('language'))" /> im <struts:property value="event.location.getName(getText('language'))" /></h1>
    <p>Eine Online Reservierung ist für diesen Termin leider nicht mehr möglich.</p>
    <p>Um dennoch einen Tisch zu reservieren rufen Sie bitte folgende Nummer an: <struts:property value="event.location.contactDetails.telephone" /></p>

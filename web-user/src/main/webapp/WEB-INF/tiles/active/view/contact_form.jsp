<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <struts:form action="sent">
      <struts:textfield key="contactForm.firstname" cssStyle="width:415px; resize:none;" />
      <struts:textfield  key="contactForm.lastname" cssStyle="width:415px; resize:none;" />
      <struts:textfield key="contactForm.mail" cssStyle="width:415px; resize:none;" />
      <struts:textfield key="contactForm.telephone" cssStyle="width:415px; resize:none;"/>
      <struts:textfield key="contactForm.fax" cssStyle="width:415px; resize:none;"/>
      <struts:textarea key="contactForm.message" cssStyle="width:415px; height:400px; resize:none;"/>
      <struts:submit/>
    </struts:form>

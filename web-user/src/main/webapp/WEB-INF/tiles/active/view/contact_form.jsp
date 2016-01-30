<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>

    <struts:property escape="false" value="page.getContent(getText('language'))" />

    <struts:form
          cssClass="form" 
          action="sent" 
          namespace="/contact" 
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
    >
      <fieldset class="fieldset">
        <div class="field">
          <struts:radio     key="message.gender" list="#{true:'Frau',false:'Herr'}" />
        </div>

        <div class="field">        
          <struts:textfield key="message.firstname"  />
        </div>

        <div class="field">
          <struts:textfield key="message.lastname"  />
        </div>

        <div class="field">
          <struts:textfield key="message.mail"  />
        </div>

        <div class="field">
          <struts:textfield key="message.telephone" />
        </div>

        <div class="field">
          <struts:textfield key="message.fax" />
        </div>

        <div class="field">
          <struts:textarea key="message.content" />
        </div>
      </fieldset>
      <struts:submit type="button" value="Absenden" />
      <struts:reset type="button" value="Löschen" />
      <hr>
    </struts:form>

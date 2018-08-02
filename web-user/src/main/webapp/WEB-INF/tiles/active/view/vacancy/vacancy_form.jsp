<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <div id="sitePicture">
    <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
  </div>

  <h1>Bewerbung auf die Stelle <struts:property value="vacancy.getName(getText('language'))" /></h1>


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
          <struts:radio     key="application.gender" list="#{true:'Frau',false:'Herr'}" />
        </div>

        <div class="field">        
          <struts:textfield key="application.firstname"  />
        </div>

        <div class="field">
          <struts:textfield key="application.lastname"  />
        </div>

        <div class="field">
          <struts:textfield key="application.mail"  />
        </div>

        <div class="field">
          <struts:textfield key="application.telephone" />
        </div>

        <div class="field">
          <struts:textfield key="application.mobile" />
        </div>

        <div class="field">
          <struts:textarea key="application.content" />
        </div>
      </fieldset>
      <struts:submit type="button" value="Absenden" />
      <struts:reset type="button" value="Löschen" />
      <hr>
    </struts:form>








    <struts:url var="vacancyLink" action="form" namespace="/vacancy/%{vacancy.uuid}"/>
    <struts:a href="%{vacancyLink}">

      <div class="informations color4">
        <div class="information">
          <h4><struts:property value="vacancy.getName(getText('language'))" /></h4>
          <p><struts:property value="vacancy.workingTime" /></p>
          <p><struts:property value="vacancy.beginning" /></p>
          <p><struts:property value="vacancy.location.getName(getText('language'))" /></p>
        </div>

        <div class="impression">
          <img src="/pictures/FULL/<struts:property value="vacancy.picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
        </div>
        <hr>
      </div>
    
    </struts:a>


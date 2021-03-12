<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

    <struts:form
          cssClass="form" 
          action="sent" 
          namespace="/request" 
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
          enctype="multipart/form-data"
          method="post"
    >
      <fieldset class="fieldset">

        <div class="field">
          <struts:select
            key="requestInput.reason"
            listValue="%{getName(getText('language'))}"
            listKey="uuid"
            multiple="false"
            list="reasonList"
          />
        </div>

        <div class="field">
          <struts:file key="requestInput.uploadFile" parentTheme="xhtml" labelposition="left">
            <struts:param name="required" value="false" />
            <struts:param name="disabled" value="false" />
          </struts:file>
        </div>

        <div class="field">
          <struts:select
            key="requestInput.location"
            listValue="%{getName(getText('language'))}"
            listKey="uuid"
            multiple="false"
            list="locationList"
          />
        </div>

        <div class="field">
          <struts:radio     key="requestInput.gender" list="#{'F':'Frau','M':'Herr','O':'Andere'}" />
        </div>

        <div class="field">        
          <struts:textfield key="requestInput.firstname"  />
        </div>

        <div class="field">
          <struts:textfield key="requestInput.lastname"  />
        </div>

        <div class="field">
          <struts:textfield key="requestInput.birthday" />
        </div>

        <div class="field">
          <struts:textfield key="requestInput.mail"  />
        </div>

        <div class="field">
          <struts:textarea key="requestInput.comment" />
        </div>

        <div class="field">
          <struts:checkbox key="requestInput.privacy" theme="tivpage" />
        </div>

        <div class="field">
          <div id="wwgrp_sent_answer_image" class="wwgrp">
            <div id="wwctrl_sent_answer_image" class="wwctrl">
              <img src="/image/captcha/<struts:property value="captcha.uuid"/>/thumbnail.png"/>
              <struts:hidden name="captcha" value="%{captcha.uuid}" parentTheme="css_xhtml"/>
            </div>
          </div>
        </div>

        <div class="field">
          <struts:textfield key="answer" />
        </div>

      </fieldset>
      <struts:submit type="button" value="Absenden" />
      <struts:reset type="button" value="Löschen" />
      <hr>
    </struts:form>

    <hr>
  </div>
  <!-- Content Ende -->


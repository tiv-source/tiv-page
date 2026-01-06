<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="page.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

    <struts:form
          cssClass="form" 
          action="inscribe" 
          namespace="/newsletter/lockrequest" 
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
    >
      <fieldset class="fieldset">

        <div class="field">
          <struts:textfield key="mailAddress"  />
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


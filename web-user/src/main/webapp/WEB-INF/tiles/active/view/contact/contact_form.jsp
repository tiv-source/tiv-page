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
          action="sent" 
          namespace="/contact" 
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
    >
      <fieldset class="fieldset">
        <div class="field">
          <struts:radio
              key="message.salutation"
              list="salutations"
              listValue="%{getName(getText('language'))}"
              listKey="uuid"
              value="message.salutation.{uuid}"
          />
          <struts:hidden key="message.gender" value="false" />
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
          <struts:textfield
              key="message.telephone" 
              placeholder="+49-172-5555555"
              inputmode="tel"
          />
        </div>

        <div class="field" style="display: none;">
          <struts:textfield
              key="fax" 
              placeholder="+49-172-5555555"
              inputmode="tel"
          />
        </div>

        <struts:hidden key="notEmpty" />
        <struts:hidden key="protectionTimeString" />

        <div class="field">
          <struts:textarea key="message.content" onchange="formatString()"/>
          <script type="text/javascript">
          function formatString() {
        	  document.getElementById("sent_notEmpty").value = "48ac-8a00-9737505b7c82";
          }
          </script>
        </div>

        <div class="field">
          <struts:checkbox key="message.privacy" theme="tivpage"/>
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
      <struts:submit type="button" value="Absenden" onclick="this.disabled=true; this.form.submit();" />
      <struts:reset type="button" value="Löschen" />
      <hr>
    </struts:form>

    <hr>
  </div>
  <!-- Content Ende -->


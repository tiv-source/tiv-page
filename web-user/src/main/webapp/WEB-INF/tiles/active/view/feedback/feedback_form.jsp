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
      namespace="/feedback" 
      tooltipIconPath="/images/info.png" 
      javascriptTooltip="true" 
      tooltipDelay="500"
      theme="css_xhtml"
  >

    <fieldset class="fieldset">
      <div class="field">
        <struts:if test="cashpoint">
          <div id="wwgrp_sent_feedback_cashpoint" class="wwgrp">
            <div id="wwlbl_sent_feedback_cashpoint" class="wwlbl">
              <label for="sent_feedback_cashpoint" class="label">
                <struts:text name="feedback.cashpoint"/>
              </label>
            </div>
            <br>
            <div id="wwctrl_sent_feedback_cashpoint" class="wwctrl">
              <p style="padding-left:21px;">
                <struts:property value="feedback.cashpoint"/>
                <struts:hidden key="feedback.cashpoint"  />
              </p>
            </div>
          </div>
        </struts:if>
        <struts:else>
          <struts:textfield key="feedback.cashpoint"  />
        </struts:else>
      </div>

      <div class="field">
        <struts:if test="voucher">
          <div id="wwgrp_sent_feedback_voucher" class="wwgrp">
            <div id="wwlbl_sent_feedback_voucher" class="wwlbl">
              <label for="sent_feedback_voucher" class="label">
                <struts:text name="feedback.voucher"/>
              </label>
            </div>
            <br>
            <div id="wwctrl_sent_feedback_voucher" class="wwctrl">
              <p style="padding-left:21px;">
                <struts:property value="feedback.voucher"/>
                <struts:hidden key="feedback.voucher"  />
              </p>
            </div>
          </div>
        </struts:if>
        <struts:else>
          <struts:textfield key="feedback.voucher"  />
        </struts:else>
      </div>

    <struts:iterator value="options" status="optionStatus">
      <struts:if test="feedback.answers[mapKey] > 0">
        <struts:set var="score" value="feedback.answers[mapKey]"/>
      </struts:if>
      <struts:else>
        <struts:set var="score" value="0"/>
      </struts:else>

      <struts:set var="hints" value="getHints(getText('language'))"/>

      <div class="field">
        <div id="wwgrp_sent_feedback_${mapKey}" class="wwgrp">
          <struts:set var="myError">feedback.answers['${mapKey}']</struts:set>
          <struts:set var="myErrorCheck">feedback.answers[\\'${mapKey}\\']</struts:set>
          <struts:if test="%{fieldErrors.get('feedback.answers[\\'' + mapKey + '\\']').size() > 0}">
            <div id="wwerr_sent_feedback_${mapKey}" class="wwerr">
              <div errorfor="sent_feedback_${mapKey}" class="errorMessage">
                <struts:fielderror>
                  <struts:param><struts:property value="%{myError}"/></struts:param>
                </struts:fielderror>
              </div>
            </div>
          </struts:if>

          <div id="wwlbl_sent_feedback_${mapKey}" class="wwlbl">
            <label for="sent_feedback_${mapKey}" class="label">
              <struts:property value="getName(getText('language'))" />
            </label>
          </div>
          <div id="wwctrl_sent_feedback_${mapKey}" class="wwctrl">
            <div id="${uuid}" class="feedbackStars"></div>
            <script type="text/javascript">
              $('#' + "${uuid}").raty({
                  scoreName: 'feedback.answers[\'${mapKey}\']',
                  path: '/public/raty/images',
                  number: <struts:property value="maxStars" />,
                  hints: <struts:property value="#hints" />,
                  score: <struts:property value="#score"/>
              });
            </script>

          </div>
        </div>
      </div>
  </struts:iterator>
    
    </fieldset>

    <struts:submit type="button" value="Absenden" />
    <struts:reset type="button" value="Löschen" />

  </struts:form>
  
    <hr>
  </div>
  <!-- Content Ende -->






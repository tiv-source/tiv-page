<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">

        <div id="title">
          <h5><struts:text name="page.restore"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="restore" 
                  namespace="/others/page" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >
            <struts:hidden key="uncheckedPage"/>
            <struts:hidden key="revision"/>

            <fieldset class="fieldset">

              <div class="field">
                <div id="wwgrp_restore_page_technical" class="wwgrp">
                  <div id="wwlbl_restore_page_technical" class="wwlbl">
                    <label for="restore_page_technical" class="label">
                      <struts:text name="page.technical"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_technical" class="wwctrl">
                    <struts:property value="page.technical"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_orderNumber" class="wwgrp">
                  <div id="wwlbl_restore_page_orderNumber" class="wwlbl">
                    <label for="restore_page_orderNumber" class="label">
                      <struts:text name="page.orderNumber"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_orderNumber" class="wwctrl">
                    <struts:property value="page.orderNumber"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_pictureOnPage" class="wwgrp">
                  <div id="wwlbl_restore_page_pictureOnPage" class="wwlbl">
                    <label for="restore_page_pictureOnPage" class="label">
                      <struts:text name="page.pictureOnPage"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_pictureOnPage" class="wwctrl">
                    <struts:property value="page.pictureOnPage"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_cssGroup" class="wwgrp">
                  <div id="wwlbl_restore_page_cssGroup" class="wwlbl">
                    <label for="restore_page_cssGroup" class="label">
                      <struts:text name="page.cssGroup"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_cssGroup" class="wwctrl">
                    <struts:property value="page.cssGroup.name"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_visible" class="wwgrp">
                  <div id="wwlbl_restore_page_visible" class="wwlbl">
                    <label for="restore_page_visible" class="label">
                      <struts:text name="page.visible"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_visible" class="wwctrl">
                    <struts:property value="page.visible"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_special" class="wwgrp">
                  <div id="wwlbl_restore_page_special" class="wwlbl">
                    <label for="restore_page_special" class="label">
                      <struts:text name="page.special"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_special" class="wwctrl">
                    <struts:property value="page.special"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_descriptionMap_DE_name" class="wwgrp">
                  <div id="wwlbl_restore_page_descriptionMap_DE_name" class="wwlbl">
                    <label for="restore_page_descriptionMap_DE_name" class="label">
                      <struts:text name="page.descriptionMap.DE.name"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_descriptionMap_DE_name" class="wwctrl">
                    <struts:property value="page.descriptionMap.DE.name"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_descriptionMap_DE_description" class="wwgrp">
                  <div id="wwlbl_restore_page_descriptionMap_DE_description" class="wwlbl">
                    <label for="restore_page_descriptionMap_DE_description" class="label">
                      <struts:text name="page.descriptionMap.DE.description"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_descriptionMap_DE_description" class="wwctrl">
                    <struts:property value="page.descriptionMap.DE.description"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_descriptionMap_DE_keywords" class="wwgrp">
                  <div id="wwlbl_restore_page_descriptionMap_DE_keywords" class="wwlbl">
                    <label for="restore_page_descriptionMap_DE_keywords" class="label">
                      <struts:text name="page.descriptionMap.DE.keywords"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_descriptionMap_DE_keywords" class="wwctrl">
                    <struts:property value="page.descriptionMap.DE.keywords"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_contentMap_DE_content" class="wwgrp">
                  <div id="wwlbl_restore_page_contentMap_DE_content" class="wwlbl">
                    <label for="restore_page_contentMap_DE_content" class="label">
                      <struts:text name="page.contentMap.DE.content"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_contentMap_DE_content" class="wwctrl">
                    <struts:property value="page.contentMap.DE.content" escapeHtml="false"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_descriptionMap_EN_name" class="wwgrp">
                  <div id="wwlbl_restore_page_descriptionMap_EN_name" class="wwlbl">
                    <label for="restore_page_descriptionMap_EN_name" class="label">
                      <struts:text name="page.descriptionMap.EN.name"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_descriptionMap_EN_name" class="wwctrl">
                    <struts:property value="page.descriptionMap.EN.name"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_descriptionMap_EN_description" class="wwgrp">
                  <div id="wwlbl_restore_page_descriptionMap_EN_description" class="wwlbl">
                    <label for="restore_page_descriptionMap_EN_description" class="label">
                      <struts:text name="page.descriptionMap.EN.description"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_descriptionMap_EN_description" class="wwctrl">
                    <struts:property value="page.descriptionMap.EN.description"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_descriptionMap_EN_keywords" class="wwgrp">
                  <div id="wwlbl_restore_page_descriptionMap_EN_keywords" class="wwlbl">
                    <label for="restore_page_descriptionMap_EN_keywords" class="label">
                      <struts:text name="page.descriptionMap.EN.keywords"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_descriptionMap_EN_keywords" class="wwctrl">
                    <struts:property value="page.descriptionMap.EN.keywords"/>
                  </div>
                </div>
              </div>

              <div class="field">
                <div id="wwgrp_restore_page_contentMap_EN_content" class="wwgrp">
                  <div id="wwlbl_restore_page_contentMap_EN_content" class="wwlbl">
                    <label for="restore_page_contentMap_EN_content" class="label">
                      <struts:text name="page.contentMap.EN.content"/>
                    </label>
                  </div>
                  <br>
                  <div id="wwctrl_restore_page_contentMap_EN_content" class="wwctrl">
                    <struts:property value="page.contentMap.EN.content" escapeHtml="false"/>
                  </div>
                </div>
              </div>

            </fieldset>


            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.restore"/>
              </button>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/others/page">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

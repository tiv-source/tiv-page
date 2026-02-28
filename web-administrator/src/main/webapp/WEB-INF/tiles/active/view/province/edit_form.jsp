<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="province.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/system/province" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >
            <struts:hidden key="province.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:select
					key="province.country"
					listValue="%{getName(getText('language'))}"
					listKey="uuid"
					multiple="false"
					labelposition="left"
                    parentTheme="css_xhtml"
					list="countryList"
					value="province.country.{uuid}"
					/>
              </div>

              <div class="field">
                <struts:textfield 
                    key="province.descriptionMap.de.name"
                    labelposition="left"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="province.descriptionMap.en.name"
                    labelposition="left"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="province.code"
                    labelposition="left"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
                />
              </div>


            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.save"/>
              </button>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/system/province">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->



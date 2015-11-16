<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2>Add OpeningHour</h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="openingHourAdd" 
                  namespace="/location" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >

            <fieldset class="fieldset">

              <div class="field">
                <struts:select
                    key="openingHour.weekday"
                    list="weekdays" 
                    listKey="toString()"  
                    listValue="toString()" 
                    parentTheme="xhtml"
                    labelposition="left"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="openingHour.open.hour"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="openingHour.open.minute"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="openingHour.close.hour"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="openingHour.close.minute"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <struts:hidden key="openingHour.location.uuid" value="%{location.uuid}"/>

            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">Save</button>

              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/location">Close</struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

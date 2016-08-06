<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="openingHour.delete"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="openingHourDelete" 
                  namespace="/location" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden name="locationUuid" value="%{location.uuid}" />
            <struts:hidden key="openingHours" />

            <fieldset class="fieldset">

              <div class="field">
                <label for="location.descriptionMap.DE.name" class="label">Name:</label>
                <struts:property value="location.descriptionMap.DE.name"/>
              </div>

              <div class="field">
                <label for="openingHour.weeday" class="label"><struts:text name="openingHour.weekday"/></label>
                <struts:property value="getText(openingHour.weekday)" />:
              </div>

              <div class="field">
                <label for="openingHour.open" class="label"><struts:text name="openingHour.open"/></label>
                <struts:property value="openingHour.open"/> <struts:text name="clock"/>
              </div>

              <div class="field">
                <label for="openingHour.close" class="label"><struts:text name="openingHour.close"/></label>
                <struts:property value="openingHour.close"/> <struts:text name="clock"/>
              </div>

            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.delete"/>
              </button>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/location">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>

          </struts:form>

        </div>
      </div>
      <!--  Ende MAIN -->
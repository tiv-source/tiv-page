<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2>Delete Location OpeningHour</h2>
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
                <label for="openingHour.weeday" class="label">Wochentag:</label>
                <struts:property value="openingHour.weekday"/>
              </div>

              <div class="field">
                <label for="openingHour.open" class="label">Von:</label>
                <struts:property value="openingHour.open"/>
              </div>

              <div class="field">
                <label for="openingHour.close" class="label">Bis:</label>
                <struts:property value="openingHour.close"/>
              </div>

            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">Löschen</button>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/location">Abbrechen</struts:a>
            </div>

          </struts:form>

        </div>
      </div>
      <!--  Ende MAIN -->
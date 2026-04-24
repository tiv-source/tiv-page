<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="registration.predeclare"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="predeclare" 
                  namespace="/academy/registration" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="registration.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <label for="registration.firstname" class="label">Vorname:</label>
                <struts:hidden id="registration.firstname" key="registration.firstname"/>
                <struts:property value="registration.firstname"/>
              </div>

              <div class="field">
                <label for="registration.lastname" class="label">Nachname:</label>
                <struts:hidden id="registration.lastname" key="registration.lastname"/>
                <struts:property value="registration.lastname"/>
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
                  action="waiting" 
                  namespace="/academy/registration">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

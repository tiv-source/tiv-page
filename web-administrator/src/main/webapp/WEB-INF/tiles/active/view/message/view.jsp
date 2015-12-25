<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2>View Message</h2>
        </div>

        <div id="backend_update_form" class="update">

            <fieldset class="fieldset">

              <div class="field">
                <label for="message.gender" class="label">Geschlecht:</label>
                <struts:property value="message.gender"/>
              </div>

              <div class="field">
                <label for="message.firstname" class="label">Vorname:</label>
                <struts:property value="message.firstname"/>
              </div>

              <div class="field">
                <label for="message.lastname" class="label">Nachname:</label>
                <struts:property value="message.lastname"/>
              </div>

            </fieldset>

            <div class="buttons form_bottom">
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/message">Close</struts:a>
            </div>
          
        </div>


      </div>
      <!--  Ende MAIN -->

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
          <form class="form">

            <fieldset class="fieldset">

              <div class="field">
                <label for="message.gender" class="label">Geschlecht:</label>
                <struts:if test="message.gender">
                  Frau
                </struts:if>
                <struts:else>
                  Herr
                </struts:else>
              </div>

              <div class="field">
                <label for="message.firstname" class="label">Vorname:</label>
                <struts:property value="message.firstname"/>
              </div>

              <div class="field">
                <label for="message.lastname" class="label">Nachname:</label>
                <struts:property value="message.lastname"/>
              </div>

              <div class="field">
                <label for="message.mail" class="label">Mail:</label>
                <struts:property value="message.mail"/>
              </div>

              <div class="field">
                <label for="message.telephone" class="label">Telefon:</label>
                <struts:property value="message.telephone"/>
              </div>

              <div class="field">
                <label for="message.fax" class="label">Fax:</label>
                <struts:property value="message.fax"/>
              </div>

              <div class="field">
                <label for="message.content" class="label">Inhalt:</label>
                <div style="padding-left: 200px;"><struts:property value="message.content" escape="false" /></div>
              </div>

              <div class="field">
                <label for="message.created" class="label">Erstellt am:</label>
                <struts:property value="message.created"/>
              </div>

              <div class="field">
                <label for="message.ip" class="label">IP-Adresse:</label>
                <struts:property value="message.ip"/>
              </div>



            </fieldset>

            <div class="buttons form_bottom">
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/message">Abbrechen</struts:a>
            </div>
          </form>
        </div>


      </div>
      <!--  Ende MAIN -->

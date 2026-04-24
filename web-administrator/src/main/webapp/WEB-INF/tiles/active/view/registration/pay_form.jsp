<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="registration.pay"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="pay" 
                  namespace="/academy/registration" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="registration.uuid"/>

          <div id="overviewContainer">
            <fieldset id="overvew" class="fieldset">

              <div class="field">
                <label for="registration.orderConfirmationNumber" class="label">Austragsnummer:</label>
                <struts:property value="registration.orderConfirmationNumber"/>
              </div>

              <struts:if test="!registration.consumer">
                <div class="field">
                  <label for="registration.firm" class="label">Firma:</label>
                  <struts:property value="registration.firm"/>
                </div>
              </struts:if>
              <struts:else>
                <div class="field">
                  <label for="registration.consumer" class="label">Kundenart:</label>
                  Endkunde
                </div>
              </struts:else>

              <div class="field">
                <label for="registration.customer" class="label">Kunde:</label>
                <struts:property value="registration.salutation.getName('de')"/> <struts:property value="registration.firstname"/> <struts:property value="registration.lastname"/>
              </div>

              <div class="field">
                <label for="registration.course" class="label">Kurs:</label>
                <struts:property value="registration.course.getName('de')"/> (<struts:property value="registration.course.number"/>)
              </div>

              <div class="field">
                <label for="registration.birthday" class="label">Geburtsdatum:</label>
                <struts:property value="registration.birthday"/>
              </div>

              <div class="field">
                <label for="registration.email" class="label">Mail:</label>
                <struts:property value="registration.email"/>
              </div>

              <div class="field">
                <label for="registration.telephone" class="label">Telefonnummer:</label>
                <struts:property value="registration.telephone"/>
              </div>

              <div class="field">
                <label for="registration.material" class="label">Material:</label>
                <struts:if test="registration.listOfMaterials">
                  Der Kunde hat das benötigte Material mitbestellt.
                </struts:if>
                <struts:else>
                  Der Kunde brint das Material selber mit.
                </struts:else>
              </div>

              <div class="field">
                <label for="registration.channel" class="label">Anmeldungskanal:</label>
                <struts:property value="registration.channel"/>
              </div>

            </fieldset>
          </div>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.pay"/>
              </button>

              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="confirmed" 
                  namespace="/academy/registration">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

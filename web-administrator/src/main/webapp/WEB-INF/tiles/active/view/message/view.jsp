<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="message.view"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <form class="form">

            <fieldset class="fieldset">

              <div class="field">
                <label for="message.gender" class="label">
                  <struts:text name="message.gender"/>:
                </label>
                <struts:if test="message.gender">
                  <struts:text name="female"/>
                </struts:if>
                <struts:else>
                  <struts:text name="male"/>
                </struts:else>
              </div>

              <div class="field">
                <label for="message.firstname" class="label">
                  <struts:text name="message.firstname"/>:
                </label>
                <struts:property value="message.firstname"/>
              </div>

              <div class="field">
                <label for="message.lastname" class="label">
                  <struts:text name="message.lastname"/>:
                </label>
                <struts:property value="message.lastname"/>
              </div>

              <div class="field">
                <label for="message.mail" class="label">
                  <struts:text name="message.email"/>:
                </label>
                <struts:property value="message.mail"/>
              </div>

              <div class="field">
                <label for="message.telephone" class="label">
                  <struts:text name="message.telephone"/>:
                </label>
                <struts:property value="message.telephone"/>
              </div>

              <div class="field">
                <label for="message.fax" class="label">
                  <struts:text name="message.fax"/>:
                </label>
                <struts:property value="message.fax"/>
              </div>

              <div class="field">
                <label for="message.content" class="label">
                  <struts:text name="message.content"/>:
                </label>
                <div style="padding-left: 200px;">
                  <struts:property value="message.content" escape="false" />
                </div>
              </div>

              <div class="field">
                <label for="message.created" class="label">
                  <struts:text name="message.created"/>:
                </label>
                <struts:property value="message.created"/>
              </div>

              <div class="field">
                <label for="message.createdAddress" class="label">
                  <struts:text name="message.createdAddress"/>:
                </label>
                <struts:property value="message.createdAddress"/>
              </div>

            </fieldset>

            <div class="buttons form_bottom">
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/message">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </form>
        </div>


      </div>
      <!--  Ende MAIN -->

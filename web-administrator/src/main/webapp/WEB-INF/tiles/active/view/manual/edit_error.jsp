<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="manual.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          
            <fieldset class="fieldset">

              <div class="field">
                <label for="manual.error" class="label"><struts:text name="manual.error" />:</label>
                <struts:text name="manual.error.default" />
              </div>

            </fieldset>
          
        </div>


      </div>
      <!--  Ende MAIN -->



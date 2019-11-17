<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="cssFile.add"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          
            <fieldset class="fieldset">

              <div class="field">
                <label for="cssFile.error" class="label"><struts:text name="cssFile.error" />:</label>
                <struts:text name="cssFile.error.default" />
              </div>

            </fieldset>
          
        </div>


      </div>
      <!--  Ende MAIN -->



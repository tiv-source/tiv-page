<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="property.protect"/></h5>
        </div>

        <div id="form_container_full" class="update">
          
            <fieldset class="fieldset">

              <div class="field">
                <label for="property.error" class="label">
                  <struts:text name="property.error" />:
                </label>
                <struts:text name="property.error.protect.unchangeable" />
              </div>

            </fieldset>
          
        </div>


      </div>
      <!--  Ende MAIN -->

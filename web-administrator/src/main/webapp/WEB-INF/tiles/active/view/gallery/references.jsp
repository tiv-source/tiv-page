<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="gallery.delete"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          
            <fieldset class="fieldset">

              <div class="field">
                <label for="gallery.error" class="label"><struts:text name="gallery.error" />:</label>
                <struts:text name="gallery.error.references" />
              </div>

            </fieldset>
          
        </div>


      </div>
      <!--  Ende MAIN -->



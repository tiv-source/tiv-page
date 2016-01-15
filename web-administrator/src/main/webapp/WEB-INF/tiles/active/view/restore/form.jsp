<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="restoration"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="restore" 
                  method="post"
                  namespace="/restore" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500" 
                  enctype="multipart/form-data"
                  theme="css_xhtml"
          >

            <fieldset class="fieldset">

              <div class="field">

                <struts:file key="restoreFile" theme="css_xhtml">
                  <struts:param name="required" value="true" />
                  <struts:param name="disabled" value="disabledValue" />
                </struts:file>
              </div>

            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="restore"/>
              </button>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

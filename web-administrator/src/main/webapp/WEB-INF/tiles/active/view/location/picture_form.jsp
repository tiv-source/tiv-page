<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

      <!--  Start MAIN -->
      <div class="main">
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="picture.edit"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="picture" 
                  namespace="/location" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  enctype="multipart/form-data"
          >
            <struts:hidden key="location.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <label for="location.descriptionMap.DE.name" class="label">
                  <struts:text name="location.descriptionMap.DE.name"/>
                </label>
                <struts:hidden id="location.descriptionMap.DE.name" key="location.descriptionMap.DE.name"/>
                <struts:property value="location.descriptionMap.DE.name"/>
              </div>

              <div class="field">
                <label for="picture" class="label">
                  <struts:text name="location.picture"/>:
                </label>
                <img src="/uploads/<struts:property value="location.picture"/>" />
              </div>

              <div class="field">
                <struts:file key="picture" parentTheme="xhtml" labelposition="left">
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
                    <struts:text name="form.save"/>
              </button>

              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/location">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

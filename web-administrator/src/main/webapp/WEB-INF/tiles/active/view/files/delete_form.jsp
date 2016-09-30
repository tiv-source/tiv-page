<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>



      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>

        <div id="title">
          <h5><struts:text name="file.delete"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="delete" 
                  namespace="/maintenance/files" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >

            <fieldset class="fieldset">

              <div class="field">
                <label for="file" class="label"><struts:text name="file"/></label>
                <img src="/uploads/<struts:property value="file"/>" style="height: unset; max-height: 200px; width: unset;"/>
                <struts:hidden id="file" key="file"/>
                <struts:property value="file"/>
              </div>

            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.delete"/>
              </button>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/maintenance/files">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

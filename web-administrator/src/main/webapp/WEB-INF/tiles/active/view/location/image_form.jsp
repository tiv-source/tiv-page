<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div id="title">
          <h5><struts:text name="page.image.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="image" 
                  namespace="/locations/location" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  enctype="multipart/form-data"
          >
            <struts:hidden key="location.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <label for="location.descriptionMap.DE.name" class="label">Name:</label>
                <struts:hidden id="location.descriptionMap.DE.name" key="page.descriptionMap.DE.name"/>
                <struts:property value="location.descriptionMap.DE.name"/>
              </div>

              <div class="field">
                <label for="picture" class="label">Aktuelles Bild:</label>
                <img alt="" src="/image/pictureitem/<struts:property value="location.uuid" />/normal.png?cache=false">
              </div>

              <div class="field">
                <struts:file key="file" parentTheme="xhtml" labelposition="left">
                  <struts:param name="required" value="true" />
                  <struts:param name="disabled" value="false" />
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
                  action="editForm" 
                  namespace="/locations/location">
                    <struts:param name="uncheckLocation" value="%{location.uuid}" />
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->
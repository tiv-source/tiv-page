<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="pdf.delete"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="delete" 
                  namespace="/others/pdf" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="pdf.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <label for="pdf.name" class="label">Name:</label>
                <struts:hidden id="pdf.descriptionMap.DE.name" key="pdf.descriptionMap.DE.name"/>
                <struts:property value="pdf.descriptionMap.DE.name"/>
              </div>

              <div class="field">
                <label for="picture" class="label">Bild:</label>
                <img src="/image/pdf/<struts:property value="pdf.image.uuid"/>/normal.png" style="max-width:300px; max-height:300px;"/>
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
                  namespace="/others/pdf">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

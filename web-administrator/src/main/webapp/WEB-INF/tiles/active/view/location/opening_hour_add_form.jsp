<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="openingHour.add"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="openingHourAdd" 
                  namespace="/locations/location" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >

            <fieldset class="fieldset">

              <div class="field">
                <label for="location.descriptionMap.DE.name" class="label" style="display: block; float: left;">Filiale: </label>
                <struts:property value="location.descriptionMap.DE.name"/>
              </div>

              <div class="field">
                <label for="location.address.city" class="label" style="display: block; float: left;">Ort: </label>
                <struts:property value="location.address.city"/>
              </div>

              <div class="field">
                <struts:select
                    key="openingHour.weekday"
                    list="weekdays" 
                    listKey="toString()"
                    listValue="%{getText(toString())}"  
                    parentTheme="xhtml"
                    labelposition="left"
					cssStyle="padding: 0.3em;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="openingHour.open.hour"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="openingHour.open.minute"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="openingHour.close.hour"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="openingHour.close.minute"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
              </div>

              <struts:hidden key="openingHour.location.uuid" value="%{location.uuid}"/>

            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.save"/>
              </button>
              <struts:url var="overviewUrl" action="overview" namespace="/locations/location">
                <struts:param name="locationUuid" value="location.uuid" />
              </struts:url>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  href="%{overviewUrl}">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

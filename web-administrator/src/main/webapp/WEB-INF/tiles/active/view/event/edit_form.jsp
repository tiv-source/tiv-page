<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<struts:url var="englishUrl">
  <struts:param name="event" value="event.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>
<struts:url var="germanUrl">
  <struts:param name="event" value="event.uuid" />
  <struts:param name="lang">DE</struts:param>
</struts:url>

      <!--  Start MAIN -->
      <div class="main">
        <div class="lang_menu" style="border: 1px solid black; float: right; margin-top: 55px; position: absolute; right: 302px; z-index: 900;">
          <div style="padding:0px; margin:5px; height:24px;">
            <struts:a href="%{englishUrl}">
              <img src="https://admin.czastka.de/icon/flags/png/24x16/flags-united_kingdom.png" style="float: left; width: 28px;"/> 
              <p style="padding-left: 35px; padding-top: 1px;">Englische Version</p>
            </struts:a>
          </div>

          <div style="padding:0px; margin:5px; height:20px;">
            <struts:a href="%{germanUrl}">
              <img src="https://admin.czastka.de/icon/flags/png/24x16/flags-germany.png" style="float: left; width: 28px;"/> 
              <p style="padding-left: 35px; padding-top: 1px;">Deutsche Version</p>
            </struts:a>
          </div> 
        </div>

        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2>Edit Event</h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/event" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="event.uuid"/>

            <fieldset class="fieldset">

            
              <div class="field">
                <sj:datepicker
                    key="event.beginning"
                    changeMonth="true"
                    changeYear="true"
                    displayFormat="dd.mm.yy"
                    timepicker="true"
                    timepickerFormat="hh:mm"
                    style="width:100px;"
                    parentTheme="css_xhtml"
                />
              </div>

              <div class="field">
                <sj:datepicker
                    key="event.ending"
                    changeMonth="true"
                    changeYear="true"
                    displayFormat="dd.mm.yy"
                    timepicker="true"
                    timepickerFormat="hh:mm"
                    style="width:100px;"
                    parentTheme="css_xhtml"
                />
              </div>
              
              <div class="field">
                <sj:datepicker
                    key="event.deadline"
                    changeMonth="true"
                    changeYear="true"
                    displayFormat="dd.mm.yy"
                    timepicker="true"
                    timepickerFormat="hh:mm"
                    style="width:100px;"
                    parentTheme="css_xhtml"
                />
              </div>

              <div class="field">
                <sj:spinner
                    key="event.price"
                    min="0.00"
                    step="0.01"
                    numberFormat="C"
                    mouseWheel="true"
                    parentTheme="css_xhtml"
                />
              </div>

              <div class="field">
                <struts:select
                    key="event.location"
                    listValue="%{getName(getText('language'))}"
                    listKey="uuid"
                    multiple="false"
                    value="event.location.{uuid}"
                    list="locationList"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="event.reservation"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="event.visible"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <sj:textfield 
                    key="event.descriptionMap.EN.name"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textarea 
                    key="event.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="xhtml"
                ></sj:textarea>	  
              </div>

              <div class="field">
                <sj:textfield 
                    key="event.descriptionMap.EN.keywords"  
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>
            </struts:if>
            
            <struts:else>
              <struts:hidden key="lang"/>

              <div class="field">
                <sj:textfield 
                    key="event.descriptionMap.DE.name"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textarea 
                    key="event.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="xhtml"
                ></sj:textarea>	  
              </div>

              <div class="field">
                <sj:textfield 
                    key="event.descriptionMap.DE.keywords"  
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>
            </struts:else>




            </fieldset>


            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">Save</button>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/page"
                  >
                  Close
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

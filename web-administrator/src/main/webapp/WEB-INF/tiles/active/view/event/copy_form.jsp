<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="event.add"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/event" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >

            <fieldset class="fieldset">

              <div class="field">
                <struts:hidden id="event_picture" name="event.picture" value="event.picture.uuid" />
                <script type="text/javascript" src="/admin/js/jquery.tivselect.js"></script>
                <struts:select
                    key="event.picture"
                    listValue="pictureUrls.THUMBNAIL.url"
                    listKey="uuid"
                    multiple="false"
                    value="event.picture.{uuid}"
                    list="pictureList" 
                    theme="tivpage"
                />
                <script type="text/javascript">
                $('#add_event_picture').tivselect({
                    onSelected: function(data){
                    	$("#event_picture").val(data.selectedData.value);
                    }   
                });
                </script>
              </div>

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

              <div class="field">
                <sj:textfield 
                    key="event.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textarea 
                    key="event.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></sj:textarea>	  
              </div>

              <div class="field">
                <sj:textfield 
                    key="event.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <struts:hidden key="event.descriptionMap.EN.name" value="No English content available."/>
              <struts:hidden key="event.descriptionMap.EN.description" value="No English content available."/>
              <struts:hidden key="event.descriptionMap.EN.keywords" value="No English content available."/>


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
                  namespace="/event">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

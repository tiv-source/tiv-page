<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

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
              <img src="/admin/icons/80x60_flag-united_kingdom.png" style="float: left; width: 28px;"/> 
              <p style="padding-left: 35px; padding-top: 1px;">Englische Version</p>
            </struts:a>
          </div>

          <div style="padding:0px; margin:5px; height:24px;">
            <struts:a href="%{germanUrl}">
              <img src="/admin/icons/80x60_flag-germany.png" style="float: left; width: 28px;"/> 
              <p style="padding-left: 35px; padding-top: 1px;">Deutsche Version</p>
            </struts:a>
          </div>
        </div>

        <div id="title">
          <h5><struts:text name="event.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/locations/event" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="event.uuid"/>

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
                $('#edit_event_picture').tivselect({
                    onSelected: function(data){
                    	$("#event_picture").val(data.selectedData.value);
                    }   
                });
                </script>
              </div>

            
              <div class="field">
                <struts:textfield
                    key="event.beginning"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_event_beginning').datetimepicker({ 
                	datepicker: true,
                    format: 'd.m.Y H:i',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>
              </div>

              <div class="field">
                <struts:textfield
                    key="event.ending"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_event_ending').datetimepicker({ 
                	datepicker: true,
                    format: 'd.m.Y H:i',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>

              </div>
              
              <div class="field">
                <struts:textfield
                    key="event.deadline"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_event_deadline').datetimepicker({ 
                	datepicker: true,
                    format: 'd.m.Y H:i',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>
              </div>

              <div class="field">
                <struts:textfield
                    key="event.price"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_event_price').spinner();
                </script>
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
                <struts:textfield
                    key="event.piwikGoal"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_event_piwikGoal').spinner();
                </script>
              </div>

              <div class="field">
                <struts:textfield
                    key="event.maxReservations"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_event_maxReservations').spinner();
                </script>
              </div>

              <div class="field">
                <struts:textfield
                    key="event.maxPersons"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_event_maxPersons').spinner();
                </script>
              </div>

              <div class="field">
                <struts:checkbox
                    key="event.timeSelection"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
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
                <struts:checkbox
                    key="event.pictureOnPage"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:select
                    key="event.cssGroup"
                    value="%{event.cssGroup.{uuid}}"
                    list="cssGroupList" 
                    listKey="uuid"  
                    listValue="name" 
                    parentTheme="xhtml"
                    labelposition="left"
					cssStyle="padding: 0.3em; width: 640px;" 
					multiple="false" 
                />
              </div>

            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <struts:textfield 
                    key="event.descriptionMap.EN.name"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="event.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="event.descriptionMap.EN.keywords"  
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>
            </struts:if>
            
            <struts:else>
              <struts:hidden key="lang"/>

              <div class="field">
                <struts:textfield 
                    key="event.descriptionMap.DE.name"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="event.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
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
                  class="save small_green_button button">
                    <struts:text name="form.save"/>
              </button>

              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/locations/event">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

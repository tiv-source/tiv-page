<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="englishUrl">
  <struts:param name="uncheckLocation" value="location.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>
<struts:url var="germanUrl">
  <struts:param name="uncheckLocation" value="location.uuid" />
  <struts:param name="lang">DE</struts:param>
</struts:url>
<struts:url var="imageUrl" namespace="/locations/location" action="imageForm">
  <struts:param name="uncheckLocation" value="location.uuid" />
</struts:url>
<struts:url var="locationOverviewUrl" namespace="/locations/location" action="overview">
  <struts:param name="uncheckLocation" value="location.uuid" />
</struts:url>


      <!--  Start MAIN -->
      <div class="main">
        <struts:if test="actionName!='edit'">
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

            <div style="padding:0px; margin:5px; height:24px;">
              <struts:a href="%{imageUrl}">
                <img src="/admin/icons/80x60_picture.png" style="float: left; width: 28px;"/> 
                <p style="padding-left: 35px; padding-top: 1px;">Bild bearbeiten</p>
              </struts:a>
            </div>

            <div style="padding:0px; margin:5px; height:24px;">
              <struts:a href="%{locationOverviewUrl}">
                <img src="/admin/icons/80x60_clock.png" style="float: left; width: 28px;"/> 
                <p style="padding-left: 35px; padding-top: 1px;">&Ouml;ffnungszeiten</p>
              </struts:a>
            </div>
          </div>
        </struts:if>

        <div id="title">
          <h5><struts:text name="location.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/locations/location" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="location.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:checkbox
                    key="location.pictureOnPage"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:select
                    key="location.cssGroup"
                    value="%{location.cssGroup.{uuid}}"
                    list="cssGroupList" 
                    listKey="uuid"  
                    listValue="name" 
                    parentTheme="xhtml"
                    labelposition="left"
					cssStyle="padding: 0.3em; width: 640px;" 
					multiple="false" 
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="location.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="location.inLocationList"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="location.event"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="location.orderNumber"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_location_orderNumber').spinner();
                </script>
              </div>

            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <struts:textfield 
                    key="location.descriptionMap.EN.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="location.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.descriptionMap.EN.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

            </struts:if>
            
            <struts:else>
              <struts:hidden key="lang"/>

              <div class="field">
                <struts:textfield 
                    key="location.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="location.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

            </struts:else>


<!-- Address -->

              <div class="field">
                <struts:textfield 
                    key="location.address.street"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.address.zip"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.address.city"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.address.country"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

<!-- ContactDetails -->

              <div class="field">
                <struts:textfield 
                    key="location.contactDetails.mobile"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.contactDetails.telephone"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.contactDetails.fax"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.contactDetails.email"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.contactDetails.homepage"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

<!-- Coordinates -->

              <div class="field">
                <struts:textfield 
                    key="location.longitude"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="location.latitude"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
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
                  namespace="/locations/location">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

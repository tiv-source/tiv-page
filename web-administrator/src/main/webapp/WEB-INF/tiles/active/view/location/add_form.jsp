<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="location.add"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/locations/location" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  enctype="multipart/form-data"
          >

            <fieldset class="fieldset">

              <div class="field">
                <struts:hidden id="location_picture" name="location.picture" value="location.picture.uuid" />
                <script type="text/javascript" src="/admin/js/jquery.tivselect.js"></script>
                <struts:select
                    key="location.picture"
                    listValue="pictureUrls.THUMBNAIL.url"
                    listKey="uuid"
                    multiple="false"
                    value="location.picture.{uuid}"
                    list="pictureList" 
                    theme="tivpage"
                />
                <script type="text/javascript">
                $('#add_location_picture').tivselect({
                    onSelected: function(data){
                    	$("#location_picture").val(data.selectedData.value);
                    }   
                });
                </script>
              </div>

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
                    key="location.order"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_location_order').spinner();
                </script>
              </div>

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

              <struts:hidden key="location.descriptionMap.EN.name" value="No English content available."/>
              <struts:hidden key="location.descriptionMap.EN.description" value="No English content available."/>
              <struts:hidden key="location.descriptionMap.EN.keywords" value="No English content available."/>

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

      

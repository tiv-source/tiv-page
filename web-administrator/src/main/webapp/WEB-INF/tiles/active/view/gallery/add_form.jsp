<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="gallery.add"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/gallery" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >

            <fieldset class="fieldset">

              <div class="field">
                <sj:textfield 
                    key="gallery.technical"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="gallery.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <sj:spinner
                    key="gallery.orderNumber"
                    min="0"
                    max="200"
                    step="1"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width: 56px; min-width: 56px;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="gallery.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textarea 
                    key="gallery.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></sj:textarea>	  
              </div>

              <div class="field">
                <sj:textfield 
                    key="gallery.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <struts:hidden key="gallery.descriptionMap.EN.name" value="No English content available."/>
              <struts:hidden key="gallery.descriptionMap.EN.description" value="No English content available."/>
              <struts:hidden key="gallery.descriptionMap.EN.keywords" value="No English content available."/>

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
                  namespace="/gallery">
                   <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

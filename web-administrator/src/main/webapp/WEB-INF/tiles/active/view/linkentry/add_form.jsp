<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="linkEntry.add"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/others/linkentry" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml" 
                  enctype="multipart/form-data"
          >

            <fieldset class="fieldset">

              <div class="field">
                <struts:textfield 
                    key="linkEntry.url"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="linkEntry.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="linkEntry.topNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="linkEntry.topNavigationOrder"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_linkEntry_topNavigationOrder').spinner();
                </script>
              </div>

              <div class="field">
                <struts:checkbox
                    key="linkEntry.navigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="linkEntry.navigationOrder"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_linkEntry_navigationOrder').spinner();
                </script>
              </div>

              <div class="field">
                <struts:checkbox
                    key="linkEntry.bottomNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="linkEntry.bottomNavigationOrder"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_linkEntry_bottomNavigationOrder').spinner();
                </script>
              </div>

              <div class="field">
                <struts:checkbox
                    key="linkEntry.responsiveNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="linkEntry.responsiveNavigationOrder"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_linkEntry_responsiveNavigationOrder').spinner();
                </script>
              </div>

              <div class="field">
                <struts:textfield 
                    key="linkEntry.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="linkEntry.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="linkEntry.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <struts:hidden key="linkEntry.descriptionMap.EN.name" value="Initial"/>
              <struts:hidden key="linkEntry.descriptionMap.EN.description" value="Initial"/>
              <struts:hidden key="linkEntry.descriptionMap.EN.keywords" value="Initial"/>
              

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
                  namespace="/others/linkentry">
                   <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

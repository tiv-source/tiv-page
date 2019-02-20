<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="contentEntry.add"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/others/contententry" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml" 
                  enctype="multipart/form-data"
          >

            <fieldset class="fieldset">

              <div class="field">
                <struts:select
					key="contentEntry.contentItem"
					listValue="%{getName(getText('language'))}"
					listKey="uuid"
					multiple="false"
					list="contentItems"
					/>
              </div>

              <div class="field">
                <struts:checkbox
                    key="contentEntry.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="contentEntry.topNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="contentEntry.topNavigationOrder"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_contentEntry_topNavigationOrder').spinner();
                </script>
              </div>

              <div class="field">
                <struts:checkbox
                    key="contentEntry.navigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="contentEntry.navigationOrder"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_contentEntry_navigationOrder').spinner();
                </script>
              </div>

              <div class="field">
                <struts:checkbox
                    key="contentEntry.bottomNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="contentEntry.bottomNavigationOrder"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_contentEntry_bottomNavigationOrder').spinner();
                </script>
              </div>

              <div class="field">
                <struts:checkbox
                    key="contentEntry.responsiveNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="contentEntry.responsiveNavigationOrder"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#add_contentEntry_responsiveNavigationOrder').spinner();
                </script>
              </div>

              <struts:hidden key="contentEntry.descriptionMap.DE.name" value="Initial"/>
              <struts:hidden key="contentEntry.descriptionMap.DE.description" value="Initial"/>
              <struts:hidden key="contentEntry.descriptionMap.DE.keywords" value="Initial"/>

              <struts:hidden key="contentEntry.descriptionMap.EN.name" value="Initial"/>
              <struts:hidden key="contentEntry.descriptionMap.EN.description" value="Initial"/>
              <struts:hidden key="contentEntry.descriptionMap.EN.keywords" value="Initial"/>
              

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
                  namespace="/others/contententry">
                   <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

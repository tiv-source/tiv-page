<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="page.add"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/page" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >

            <fieldset class="fieldset">

              <div class="field">
                <sj:textfield 
                    key="page.technical"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="page.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="page.special"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="page.topNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <sj:spinner
                    key="page.topNavigationOrder"
                    min="0"
                    max="200"
                    step="1"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width: 56px; min-width: 56px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="page.navigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <sj:spinner
                    key="page.navigationOrder"
                    min="0"
                    max="200"
                    step="1"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width: 56px; min-width: 56px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="page.bottomNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <sj:spinner
                    key="page.bottomNavigationOrder"
                    min="0"
                    max="200"
                    step="1"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width: 56px; min-width: 56px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="page.responsiveNavigation"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <sj:spinner
                    key="page.responsiveNavigationOrder"
                    min="0"
                    max="200"
                    step="1"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width: 56px; min-width: 56px;"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="page.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textarea 
                    key="page.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></sj:textarea>	  
              </div>

              <div class="field">
                <sj:textfield 
                    key="page.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sjr:ckeditor
                    id="editor"
                    key="page.contentMap.DE.content" 
					rows="10" 
					cols="80" 
					width="840"
					height="250"
					uploads="true" 
					uploadHref="/admin/page/upload.html" 
					onFocusTopics="focusRichtext"
					onBlurTopics="blurRichtext"
					onChangeTopics="highlightRichtext"
					escape="false"
					cssStyle="padding-left:202px;"
				/>
              </div>

              <struts:hidden key="page.descriptionMap.EN.name" value="No English content available."/>
              <struts:hidden key="page.descriptionMap.EN.description" value="No English content available."/>
              <struts:hidden key="page.descriptionMap.EN.keywords" value="No English content available."/>
              <struts:hidden key="page.contentMap.EN.content" value="No English content available."/>

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
                  namespace="/page">
                   <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

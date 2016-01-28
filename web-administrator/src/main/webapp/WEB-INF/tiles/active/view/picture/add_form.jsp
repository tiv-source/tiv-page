<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="picture.add"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/picture" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
                  enctype="multipart/form-data"
                  method="post"
          >

            <fieldset class="fieldset">

              <div class="field">
                <struts:file key="file" parentTheme="xhtml" labelposition="left">
                  <struts:param name="required" value="true" />
                  <struts:param name="disabled" value="disabledValue" />
                </struts:file>
              </div>

              <div class="field">
                <sj:autocompleter
					key="picture.gallery"
					list="galleryList"
					listValue="%{getName(getText('language'))}"
					listKey="uuid"
					selectBox="true"
					selectBoxIcon="true"
					onChangeTopics="autocompleteChange"
					onFocusTopics="autocompleteFocus"
					onSelectTopics="autocompleteSelect"
					parentTheme="css_xhtml"
					cssStyle="padding: 0.3em; height:14px;"
					/>
              </div>

              <div class="field">
                <struts:checkbox
                    key="picture.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="picture.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textarea 
                    key="picture.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></sj:textarea>	  
              </div>

              <div class="field">
                <sj:textfield 
                    key="picture.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <struts:hidden key="picture.descriptionMap.EN.name" value="No English content available."/>
              <struts:hidden key="picture.descriptionMap.EN.description" value="No English content available."/>
              <struts:hidden key="picture.descriptionMap.EN.keywords" value="No English content available."/>
              <struts:hidden key="picture.contentMap.EN.content" value="No English content available."/>

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
                  namespace="/picture">
                   <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

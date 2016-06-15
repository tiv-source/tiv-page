<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjr" uri="/struts-jquery-richtext-tags"%>

<struts:url var="englishUrl">
  <struts:param name="page" value="page.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>
<struts:url var="germanUrl">
  <struts:param name="page" value="page.uuid" />
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

        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="page.edit"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/page" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >
            <struts:hidden key="page.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:hidden id="page_picture" name="page.picture" value="page.picture.uuid" />
                <script type="text/javascript" src="/admin/js/jquery.tivselect.js"></script>
                <struts:select
                    key="page.picture"
                    listValue="pictureUrls.THUMBNAIL.url"
                    listKey="uuid"
                    multiple="false"
                    value="page.picture.{uuid}"
                    list="pictureList" 
                    theme="tivpage"
                />
                <script type="text/javascript">
                $('#edit_page_picture').tivselect({
                    onSelected: function(data){
                    	$("#page_picture").val(data.selectedData.value);
                    }   
                });
                </script>
              </div>

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

            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <sj:textfield 
                    key="page.descriptionMap.EN.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textarea 
                    key="page.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></sj:textarea>	  
              </div>

              <div class="field">
                <sj:textfield 
                    key="page.descriptionMap.EN.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sjr:ckeditor
                    id="editor"
                    key="page.contentMap.EN.content" 
					rows="10" 
					cols="80" 
					width="840"
					height="250"
					uploads="true"
					onFocusTopics="focusRichtext"
					onBlurTopics="blurRichtext"
					onChangeTopics="highlightRichtext"
					escape="false"
					cssStyle="padding-left:202px;"
					customConfig="/admin/js/ckeditor.config.js"
				/>
              </div>
            </struts:if>
            
            <struts:else>
              <struts:hidden key="lang"/>

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
					customConfig="/admin/js/ckeditor.config.js"
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
                  namespace="/page">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

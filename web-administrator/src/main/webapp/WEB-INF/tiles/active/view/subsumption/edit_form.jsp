<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="englishUrl">
  <struts:param name="page" value="subsumption.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>
<struts:url var="germanUrl">
  <struts:param name="page" value="subsumption.uuid" />
  <struts:param name="lang">DE</struts:param>
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
          </div>
        </struts:if>

        <div id="title">
          <h5><struts:text name="subsumption.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/others/subsumption" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
                  enctype="multipart/form-data"
                  method="post"
          >
            <struts:hidden key="subsumption.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:hidden id="subsumption_picture" name="subsumption.picture" value="subsumption.picture.uuid" />
                <script type="text/javascript" src="/admin/js/jquery.tivselect.js"></script>
                <struts:select
                    key="subsumption.picture"
                    listValue="pictureUrls.THUMBNAIL.url"
                    listKey="uuid"
                    multiple="false"
                    value="subsumption.picture.{uuid}"
                    list="pictureList" 
                    theme="tivpage"
                />
                <script type="text/javascript">
                $('#edit_subsumption_picture').tivselect({
                    onSelected: function(data){
                    	$("#subsumption_picture").val(data.selectedData.value);
                    }   
                });
                </script>
              </div>

              <div class="field">
                <struts:textfield 
                    key="subsumption.technical"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="subsumption.pictureOnPage"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="subsumption.showTitles"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="subsumption.showDescriptions"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="subsumption.showDates"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="subsumption.showPictures"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:select
                    key="subsumption.cssGroup"
                    value="%{page.cssGroup.{uuid}}"
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
                    key="subsumption.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>


              <div class="field">
                <struts:select
                    key="subsumption.contentItems"
                    value="%{subsumption.contentItems.{uuid}}"
                    list="contentItems" 
                    listKey="uuid"  
                    listValue="descriptionMap.DE.name" 
                    parentTheme="xhtml"
                    labelposition="left"
					cssStyle="padding: 0.3em; width: 640px;" 
					multiple="true" 
                />
                <script type="text/javascript">
                $("#edit_subsumption_contentItems").chosen()
                </script>
                
              </div>


            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <struts:textfield 
                    key="subsumption.descriptionMap.EN.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="subsumption.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="subsumption.descriptionMap.EN.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="subsumption.contentMap.EN.content" 
					rows="10" 
					cols="80" 
					width="840"
					height="250"
					escape="false"
					cssStyle="padding-left:202px;"
				/>
                <script type="text/javascript">
        		CKEDITOR.replace('editor', {
        			filebrowserImageUploadUrl: '/admin/others/page/upload.html',
        			customConfig: '/admin/js/ckeditor.config.js'
        		});
                </script>
              </div>
            </struts:if>
            
            <struts:else>
              <struts:hidden key="lang"/>

              <div class="field">
                <struts:textfield 
                    key="subsumption.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="subsumption.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="subsumption.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="subsumption.contentMap.DE.content" 
					rows="10" 
					cols="80" 
					width="840"
					height="250"
					escape="false"
					cssStyle="padding-left:202px;"
				/>
                <script type="text/javascript">
        		CKEDITOR.replace('editor', {
        			filebrowserImageUploadUrl: '/admin/others/page/upload.html',
        			customConfig: '/admin/js/ckeditor.config.js'
        		});
                </script>
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
                  namespace="/others/subsumption">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2><struts:text name="news.add"/></h2>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/news" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >

            <fieldset class="fieldset">

              <div class="field">
                <struts:hidden id="news_picture" name="news.picture" value="news.picture.uuid" />
                <script type="text/javascript" src="/admin/js/jquery.tivselect.js"></script>
                <struts:select
                    key="news.picture"
                    listValue="pictureUrls.THUMBNAIL.url"
                    listKey="uuid"
                    multiple="false"
                    value="news.picture.{uuid}"
                    list="pictureList" 
                    theme="tivpage"
                />
                <script type="text/javascript">
                $('#add_news_picture').tivselect({
                    onSelected: function(data){
                    	$("#news_picture").val(data.selectedData.value);
                    }   
                });
                </script>
              </div>

              <div class="field">
                <sj:datepicker
                    key="news.releaseDate"
                    changeMonth="true"
                    changeYear="true"
                    displayFormat="dd.mm.yy"
                    timepicker="true"
                    timepickerFormat="hh:mm"
                    style="width:100px;"
                    parentTheme="css_xhtml"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="news.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <sj:textfield 
                    key="news.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sj:textarea 
                    key="news.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></sj:textarea>	  
              </div>

              <div class="field">
                <sj:textfield 
                    key="news.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <sjr:ckeditor
                    id="editor"
                    key="news.contentMap.DE.content" 
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

              <struts:hidden key="news.descriptionMap.EN.name" value="No English content available."/>
              <struts:hidden key="news.descriptionMap.EN.description" value="No English content available."/>
              <struts:hidden key="news.descriptionMap.EN.keywords" value="No English content available."/>
              <struts:hidden key="news.contentMap.EN.content" value="No English content available."/>

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
                  namespace="/news">
                   <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

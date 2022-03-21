<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="englishUrl">
  <struts:param name="exhibition" value="exhibition.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>

<struts:url var="germanUrl">
  <struts:param name="exhibition" value="exhibition.uuid" />
  <struts:param name="lang">DE</struts:param>
</struts:url>

<struts:url var="imageUrl" namespace="/others/exhibition/image" action="index">
  <struts:param name="exhibition" value="exhibition.uuid" />
</struts:url>

      <!--  Start MAIN -->
      <div class="main">

        <!-- Beginn Language Menu -->
        <div class="lang_menu" style="border: 1px solid black; float: right; margin-top: 55px; position: absolute; right: 302px; z-index: 900;">
          <struts:if test="actionName!='edit'">
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
          </struts:if>

          <div style="padding:0px; margin:5px; height:32px;">
            <img src="/admin/icons/64x64_view-calendar-day.png" style="float: left; width: 28px;" alt="Erstellt" title="Erstellt"/>
            <p style="padding-left: 35px; padding-top: 7px;"><struts:property value="exhibition.created" /></p>
          </div>

          <div style="padding:0px; margin:5px; height:32px;">
            <img src="/admin/icons/64x64_view-calendar-day.png" style="float: left; width: 28px;" alt="Geändert" title="Geändert"/>
            <p style="padding-left: 35px; padding-top: 7px;"><struts:property value="exhibition.modified" /></p>
          </div>

          <div style="padding:0px; margin:5px; height:24px;">
            <struts:a href="%{imageUrl}">
              <img src="/admin/icons/80x60_picture.png" style="float: left; width: 28px;"/> 
              <p style="padding-left: 35px; padding-top: 1px;">Bild austauschen</p>
            </struts:a>
          </div>

        </div>
        <!-- Ende Language Menu -->

        <div id="title">
          <h5><struts:text name="exhibition.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/others/exhibition" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >
            <struts:hidden key="exhibition.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:select
                    key="exhibition.cssGroup"
                    value="%{exhibition.cssGroup.{uuid}}"
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
                <struts:textfield
                    key="exhibition.orderNumber"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_exhibition_orderNumber').spinner();
                </script>
              </div>

              <div class="field">
                <struts:textfield 
                    key="exhibition.technical"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="exhibition.moment"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_exhibition_moment').datetimepicker({ 
                	datepicker: true,
                    format: 'd.m.Y H:i',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>
              </div>

              <div class="field">
                <struts:textfield
                    key="exhibition.start"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_exhibition_start').datetimepicker({ 
                	datepicker: true,
                	timepicker:false,
                    format: 'd.m.Y',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>
              </div>

              <div class="field">
                <struts:textfield
                    key="exhibition.end"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_exhibition_end').datetimepicker({ 
                	datepicker: true,
                	timepicker:false,
                    format: 'd.m.Y',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>
              </div>

              <div class="field">
                <struts:select
					key="exhibition.place"
					listValue="%{toString()}"
					multiple="false"
	                headerKey=""
	                headerValue="Bitte auswählen"
	                list="cityList"
					/>
              </div>

              <div class="field">
                <struts:select
					key="exhibition.country"
					listValue="%{toString()}"
					multiple="false"
	                headerKey=""
	                headerValue="Bitte auswählen"
	                list="countryList"
					/>
              </div>

              <div class="field">
                <struts:checkbox
                    key="exhibition.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="exhibition.visibleFrom"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_exhibition_visibleFrom').datetimepicker({ 
                	datepicker: true,
                    format: 'd.m.Y H:i',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>
              </div>

            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <struts:textfield 
                    key="exhibition.descriptionMap.EN.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="exhibition.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="exhibition.descriptionMap.EN.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="exhibition.contentMap.EN.content" 
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
                    key="exhibition.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="exhibition.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="exhibition.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="exhibition.contentMap.DE.content" 
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
                  namespace="/others/exhibition">
                   <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      





	  




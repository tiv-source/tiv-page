<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="englishUrl">
  <struts:param name="uncheckCourse" value="course.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>
<struts:url var="germanUrl">
  <struts:param name="uncheckCourse" value="course.uuid" />
  <struts:param name="lang">DE</struts:param>
</struts:url>
<struts:url var="imageUrl" namespace="/academy/course" action="imageForm">
  <struts:param name="uncheckCourse" value="course.uuid" />
</struts:url>
<struts:url var="unitUrl" namespace="/academy/course" action="courseOverview">
  <struts:param name="uncheckCourse" value="course.uuid" />
</struts:url>

      <!--  Start MAIN -->
      <div class="main">
        <struts:if test="actionName!='edit'">
          <div class="lang_menu" style="border: 1px solid black; float: right; margin-top: 100px; position: absolute; right: 165px; z-index: 900;">
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

            <div style="padding:0px; margin:5px; height:24px;">
              <struts:a href="%{imageUrl}">
                <img src="/admin/icons/80x60_picture.png" style="float: left; width: 28px;"/> 
                <p style="padding-left: 35px; padding-top: 1px;">Bild bearbeiten</p>
              </struts:a>
            </div>

            <div style="padding:0px; margin:5px; height:24px;">
              <struts:a href="%{unitUrl}">
                <img src="/admin/icons/80x60_picture.png" style="float: left; width: 28px;"/> 
                <p style="padding-left: 35px; padding-top: 1px;">Einheiten bearbeiten</p>
              </struts:a>
            </div>
          </div>
        </struts:if>

        <div id="title">
          <h5><struts:text name="course.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/academy/course" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="course.uuid"/>

          <div id="course">
            <fieldset class="fieldset">

              <p class="headline">Kursdaten:</p>

              <div class="field">
                <struts:select
                    key="course.location"
                    listValue="%{getName(getText('language'))}"
                    listKey="uuid"
                    multiple="false"
                    value="event.location.{uuid}"
                    list="locationList"
                    cssStyle="padding: 0.3em; width: 99.75%"
                />
              </div>

              <div class="field">
                <struts:select
                    key="course.lecturers"
                    listValue="%{getName(getText('language'))}"
                    listKey="uuid"
                    multiple="true"
                    labelposition="left"
                    parentTheme="xhtml"
                    list="lecturerList"
                    value="course.lecturers.{uuid}"
                    />
              </div>

              <div class="field">
                <struts:textfield
                    key="course.startDate"
                    value="%{getFormatted('format.dateTime','course.startDate')}"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                    readonly="%{course.registrations.(size) > 0}"
                />
                <script type="text/javascript">
                $('#edit_course_startDate').datetimepicker({ 
                	datepicker: true,
                    format: 'd.m.Y H:i',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>
              </div>

              <div class="field">
                <struts:textfield 
                    key="course.number"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:98%;"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="course.maxStudents"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    type="number"
                    min="1"
                    step="1"
                    placeholder="0"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="course.minimumAge"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    type="number"
                    min="1"
                    step="1"
                    placeholder="0"
                />
              </div>              
            </fieldset>

          </div>


          <div id="settings">
            <fieldset class="fieldset">
            
              <p class="headline">Verwaltungseinstellungen:</p>

              <div class="field">
                <struts:select
                    key="course.cssGroup"
                    headerValue="---"
                    headerKey="null"
                    list="cssGroupList" 
                    listKey="uuid"  
                    listValue="name" 
                    parentTheme="xhtml"
                    labelposition="left"
                    cssStyle="padding: 0.3em; width: 490px;"
                    multiple="false"
                    value="course.cssGroup.{uuid}"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="course.pictureOnPage"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>


              <div class="field">
                <struts:textfield
                    key="course.orderNumber"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    type="number"
                    step="1"
                    placeholder="100"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="course.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>
            </fieldset>
          </div>

          <div id="articledata">
            <fieldset class="fieldset">

              <p class="headline">Artikeldaten:</p>

              <div id="articledata_left">
	              <div class="field">
	                <struts:textfield 
	                    key="course.courseDocumentDescription"
	                    parentTheme="css_xhtml"
	                    cssStyle="padding: 0.3em;"
	                />
	              </div>
	
	              <div class="field">
	                <struts:textfield 
	                    key="course.price"
	                    parentTheme="css_xhtml"
	                    cssStyle="padding: 0.3em;"
	                    min="0"
	                    inputmode="decimal"
	                    placeholder="100,00"
	                />
	              </div>
	
	              <div class="field">
	                <struts:textfield
	                    key="course.priceTax"
	                    parentTheme="css_xhtml"
	                    cssStyle="padding: 0.3em;"
	                    min="0"
	                    inputmode="decimal"
	                    placeholder="19,00"
	                />
	              </div>
	          </div>

	          <div id="articledata_right">
	              <div class="field">
	                <struts:checkbox
	                    key="course.material"
	                    parentTheme="xhtml"
						cssStyle="padding: 0.3em;"
						labelposition="left"
	                />
	              </div>
	
	              <div class="field">
	                <struts:textfield 
	                    key="course.materialDocumentDescription"
	                    parentTheme="css_xhtml"
	                    cssStyle="padding: 0.3em;"
	                />
	              </div>
	
	              <div class="field">
	                <struts:textfield 
	                    key="course.materialPrice"
	                    parentTheme="css_xhtml"
	                    cssStyle="padding: 0.3em;"
	                    min="0"
	                    inputmode="decimal"
	                    placeholder="100,00"
	                />
	              </div>
	
	              <div class="field">
	                <struts:textfield
	                    key="course.materialTax"
	                    parentTheme="css_xhtml"
	                    cssStyle="padding: 0.3em;"
	                    min="0"
	                    inputmode="decimal"
	                    placeholder="19,00"
	                />
	              </div>
	          </div>

          </fieldset>
        </div>

        <div id="description">
          <fieldset class="fieldset">
            <p class="headline">Beschreibung:</p>

            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <struts:textfield 
                    key="course.descriptionMap.EN.name"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:1558px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="course.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8"
                    width="840" 
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:1558px;"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="course.descriptionMap.EN.keywords"  
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:1558px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="course.contentMap.EN.content" 
					rows="10" 
					cols="80" 
					width="804"
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
                    key="course.descriptionMap.DE.name"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:1558px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="course.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    width="840"
                    cssStyle="padding: 0.3em; width:1558px;"
                    parentTheme="xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="course.descriptionMap.DE.keywords"  
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:1558px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="course.contentMap.DE.content" 
					rows="10" 
					cols="80" 
					width="804"
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
        </div>


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
                  namespace="/academy/course">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

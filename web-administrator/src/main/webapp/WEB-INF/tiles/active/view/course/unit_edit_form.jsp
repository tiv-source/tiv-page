<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="englishUrl">
  <struts:param name="uncheckCourse" value="course.uuid" />
  <struts:param name="uncheckedCourseUnit" value="courseUnit.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>
<struts:url var="germanUrl">
  <struts:param name="uncheckCourse" value="course.uuid" />
  <struts:param name="uncheckedCourseUnit" value="courseUnit.uuid" />
  <struts:param name="lang">DE</struts:param>
</struts:url>
<struts:url var="imageUrl" namespace="/academy/course" action="unitImageForm">
  <struts:param name="uncheckCourse" value="course.uuid" />
  <struts:param name="uncheckedCourseUnit" value="courseUnit.uuid" />
</struts:url>

      <!--  Start MAIN -->
      <div class="main">
        <struts:if test="actionName!='unitEdit'">
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

            <div style="padding:0px; margin:5px; height:24px;">
              <struts:a href="%{imageUrl}">
                <img src="/admin/icons/80x60_picture.png" style="float: left; width: 28px;"/> 
                <p style="padding-left: 35px; padding-top: 1px;">Bild bearbeiten</p>
              </struts:a>
            </div>
          </div>
        </struts:if>

        <div id="title">
          <h5><struts:text name="courseUnit.edit"/> (<struts:property value="course.descriptionMap.DE.name"/>)</h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="unitEdit" 
                  namespace="/academy/course" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
                  enctype="multipart/form-data"
                  method="post"
          >
            <struts:hidden key="course.uuid"/>
            <struts:hidden key="courseUnit.uuid"/>
            <struts:hidden key="lang"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:select
                    key="courseUnit.cssGroup"
                    headerValue="---"
                    headerKey="null"
                    list="cssGroupList" 
                    listKey="uuid"  
                    listValue="name" 
                    parentTheme="xhtml"
                    labelposition="left"
					cssStyle="padding: 0.3em; width: 640px;" 
					multiple="false" 
					value="courseUnit.cssGroup.{uuid}"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="courseUnit.pointInTime"
                    value="%{getFormatted('format.dateTime','courseUnit.pointInTime')}"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#unitEdit_courseUnit_pointInTime').datetimepicker({ 
                	datepicker: true,
                    format: 'd.m.Y H:i',
                	lang: 'de',
                	defaultDate: ''
                });
                </script>
              </div>

              <div class="field">
                <struts:textfield 
                    key="courseUnit.duration"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#unitEdit_courseUnit_duration').spinner();
                </script>
              </div>

            <struts:if test="lang=='EN'">

              <div class="field">
                <struts:textfield 
                    key="courseUnit.descriptionMap.EN.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="courseUnit.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="courseUnit.descriptionMap.EN.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="courseUnit.contentMap.EN.content" 
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

              <div class="field">
                <struts:textfield 
                    key="courseUnit.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="courseUnit.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="courseUnit.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="courseUnit.contentMap.DE.content" 
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
                  action="courseOverview" 
                  namespace="/academy/course">
                    <struts:param name="uncheckCourse" value="%{course.uuid}" />
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

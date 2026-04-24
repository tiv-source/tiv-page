<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="courseUnit.add"/> (<struts:property value="course.descriptionMap.DE.name"/>)</h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="unitAdd" 
                  namespace="/academy/course" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
                  enctype="multipart/form-data"
                  method="post"
          >
            <struts:hidden key="course.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:select
                    key="courseUnit.cssGroup"
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
                <struts:fielderror fieldName="courseUnit.image.uploadFileContentType" />
                <struts:file key="courseUnit.image" parentTheme="xhtml" labelposition="left" accept=".png">
                  <struts:param name="required" value="true" />
                  <struts:param name="disabled" value="false" />
                </struts:file>
              </div>

              <div class="field">
                <struts:textfield
                    key="courseUnit.pointInTime"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#unitAdd_courseUnit_pointInTime').datetimepicker({ 
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
                $('#unitAdd_courseUnit_duration').spinner();
                </script>
              </div>

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

              <struts:hidden key="courseUnit.descriptionMap.EN.name" value="No English content available."/>
              <struts:hidden key="courseUnit.descriptionMap.EN.description" value="No English content available."/>
              <struts:hidden key="courseUnit.descriptionMap.EN.keywords" value="No English content available."/>
              <struts:hidden key="courseUnit.contentMap.EN.content" value="No English content available."/>

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

      

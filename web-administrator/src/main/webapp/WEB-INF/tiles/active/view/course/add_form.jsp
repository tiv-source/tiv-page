<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="course.add"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/academy/course" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
                  enctype="multipart/form-data"
                  method="post"
          >

          <div id="course">
            <fieldset class="fieldset">

              <p class="headline">Kursdaten:</p>

              <div class="field">
                <struts:fielderror fieldName="course.image.uploadFileContentType" />
                <struts:file key="course.image" parentTheme="xhtml" labelposition="left" accept=".png">
                  <struts:param name="required" value="true" />
                  <struts:param name="disabled" value="false" />
                </struts:file>
              </div>

              <div class="field">
                <struts:select
                    key="course.location"
                    listValue="%{getName(getText('language'))}"
                    listKey="uuid"
                    multiple="false"
                    value="event.location.{uuid}"
                    list="locationList"
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
                />
                <script type="text/javascript">
                $('#add_course_startDate').datetimepicker({ 
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
                <struts:checkbox
                    key="course.pictureOnPage"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
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
                    cssStyle="padding: 0.3em; width: 640px;"
                    multiple="false"
                    value="course.cssGroup.{uuid}"
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
              <div class="field">
                <struts:textfield 
                    key="course.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:1564px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="course.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    cssStyle="padding: 0.3em; width:1564px;"
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="course.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:1564px;"
                />
              </div>

              <div class="field">
                <struts:textarea
                    id="editor"
                    key="course.contentMap.DE.content" 
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

            <struts:hidden key="course.descriptionMap.EN.name" value="No English content available."/>
            <struts:hidden key="course.descriptionMap.EN.description" value="No English content available."/>
            <struts:hidden key="course.descriptionMap.EN.keywords" value="No English content available."/>
            <struts:hidden key="course.contentMap.EN.content" value="No English content available."/>
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

      

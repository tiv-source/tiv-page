<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <struts:if test="actionName!='edit'">
          <div class="revision_menu" style="border: 1px solid black; float: right; margin-top: 70px; position: absolute; right: 164px; z-index: 900; width: 350px; overflow: scroll; height: 350px;">
                <div style="padding:0px; margin:5px; height:24px;">
                  <p style="width: 70px; padding-left: 7px; padding-top: 1px; float:left; text-align:left;">Benutzer</p>
                  <p style="width: 49px; padding-left: 7px; padding-top: 1px; float:left; text-align:right;">Revision</p>
                  <p style="width: 42px; padding-left: 14px; padding-top: 1px; float:left; text-align:left;">Type</p>
                  <p style="padding-left: 7px; padding-top: 1px; text-align:left;">Uhrzeit/Datum</p>
                </div>
            <struts:iterator value="revisions" status="revisionsStatus" var="rev">
              <struts:url var="revUrl" action="restoreForm" namespace="/maintenance/cssfile">
                 <struts:param name="cssFile" value="cssFile.uuid" />
                 <struts:param name="revision" value="#rev[1].getId()" />
              </struts:url>
              <struts:a href="%{revUrl}">
                <div style="padding:0px; margin:5px; height:24px;">
                  <p style="width: 70px; padding-left: 7px; padding-top: 1px; float:left; text-align:left;"><struts:property value="%{#rev[0].getModifiedBy()}"/></p>
                  <p style="width: 49px; padding-left: 7px; padding-top: 1px; float:left; text-align:right;"><struts:property value="%{#rev[1].getId()}"/></p>
                  <p style="width: 42px; padding-left: 14px; padding-top: 1px; float:left; text-align:left;"><struts:property value="%{#rev[2]}"/></p>
                  <p style="padding-left: 7px; padding-top: 1px; text-align:left;"><struts:date name="%{#rev[1].getRevisionDate()}" format="HH:mm:ss dd.MM.yyyy"/></p>
                </div>
              </struts:a>
            </struts:iterator>
          </div>
        </struts:if>

        <div id="title">
          <h5><struts:text name="cssFile.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/maintenance/cssfile" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  enctype="multipart/form-data"
                  method="post"
          >
            <struts:hidden key="cssFile.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:file key="cssFile.uploadFile" parentTheme="xhtml" labelposition="left">
                  <struts:param name="required" value="true" />
                  <struts:param name="disabled" value="false" />
                </struts:file>
              </div>

              <div class="field">
                <struts:textfield 
                    key="cssFile.name"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="cssFile.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield
                    key="cssFile.priority"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_cssFile_orderNumber').spinner();
                </script>
              </div>

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
                  namespace="/maintenance/cssfile">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

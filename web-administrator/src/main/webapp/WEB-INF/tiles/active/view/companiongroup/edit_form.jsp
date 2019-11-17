<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="englishUrl">
  <struts:param name="companionGroup" value="companionGroup.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>
<struts:url var="germanUrl">
  <struts:param name="companionGroup" value="companionGroup.uuid" />
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

        <div id="title">
          <h5><struts:text name="companionGroup.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/others/companiongroup" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
          >
            <struts:hidden key="companionGroup.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:hidden id="companionGroup_picture" name="companionGroup.picture" value="companionGroup.picture.uuid" />
                <script type="text/javascript" src="/admin/js/jquery.tivselect.js"></script>
                <struts:select
                    key="companionGroup.picture"
                    listValue="pictureUrls.THUMBNAIL.url"
                    listKey="uuid"
                    multiple="false"
                    value="companionGroup.picture.{uuid}"
                    list="pictureList" 
                    theme="tivpage"
                />
                <script type="text/javascript">
                $('#edit_companionGroup_picture').tivselect({
                    onSelected: function(data){
                    	$("#companionGroup_picture").val(data.selectedData.value);
                    }   
                });
                </script>
              </div>

              <div class="field">
                <struts:textfield 
                    key="companionGroup.technical"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="companionGroup.pictureOnPage"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:select
                    key="companionGroup.cssGroup"
                    value="%{companionGroup.cssGroup.{uuid}}"
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
                    key="companionGroup.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="companionGroup.orderNumber"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_companionGroup_orderNumber').spinner();
                </script>
              </div>

            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <struts:textfield 
                    key="companionGroup.descriptionMap.EN.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="companionGroup.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="companionGroup.descriptionMap.EN.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>
            </struts:if>
            
            <struts:else>
              <struts:hidden key="lang"/>

              <div class="field">
                <struts:textfield 
                    key="companionGroup.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="companionGroup.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="companionGroup.descriptionMap.DE.keywords"  
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
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
                  namespace="/others/companiongroup">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

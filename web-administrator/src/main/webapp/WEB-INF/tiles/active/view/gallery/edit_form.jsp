<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="englishUrl">
  <struts:param name="uncheckGallery" value="gallery.uuid" />
  <struts:param name="lang">EN</struts:param>
</struts:url>
<struts:url var="germanUrl">
  <struts:param name="uncheckGallery" value="gallery.uuid" />
  <struts:param name="lang">DE</struts:param>
</struts:url>
<struts:url var="imageUrl" namespace="/others/gallery" action="imageForm">
  <struts:param name="uncheckGallery" value="gallery.uuid" />
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

            <div style="padding:0px; margin:5px; height:24px;">
              <struts:a href="%{imageUrl}">
                <img src="/admin/icons/80x60_picture.png" style="float: left; width: 28px;"/> 
                <p style="padding-left: 35px; padding-top: 1px;">Bild bearbeiten</p>
              </struts:a>
            </div>
          </div>
        </struts:if>

        <div id="title">
          <h5><struts:text name="gallery.edit"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/others/gallery" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="gallery.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:select
                    key="gallery.type"
                    listValue="%{toString()}"
                    multiple="false"
                    list="galleryTypeList"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="gallery.technical"
                    parentTheme="css_xhtml"
					cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="gallery.visible"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="gallery.pictureOnPage"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:select
                    key="gallery.cssGroup"
                    value="%{gallery.cssGroup.{uuid}}"
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
                    key="gallery.orderNumber"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    labelposition="left"
                />
                <script type="text/javascript">
                $('#edit_gallery_orderNumber').spinner();
                </script>
              </div>

            <struts:if test="lang=='EN'">
              <struts:hidden key="lang"/>
              
              <div class="field">
                <struts:textfield 
                    key="gallery.descriptionMap.EN.name"
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="gallery.descriptionMap.EN.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="gallery.descriptionMap.EN.keywords"  
                    parentTheme="xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>
            </struts:if>
            
            <struts:else>
              <struts:hidden key="lang"/>

              <div class="field">
                <struts:textfield 
                    key="gallery.descriptionMap.DE.name"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:827px;"
                />
              </div>

              <div class="field">
                <struts:textarea 
                    key="gallery.descriptionMap.DE.description" 
                    cols="115" 
                    rows="8" 
                    parentTheme="css_xhtml"
                ></struts:textarea>	  
              </div>

              <div class="field">
                <struts:textfield 
                    key="gallery.descriptionMap.DE.keywords"  
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
                  namespace="/others/gallery">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

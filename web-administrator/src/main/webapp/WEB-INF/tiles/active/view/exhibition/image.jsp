<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="exhibition.image.edit"/></h5>
        </div>

        <!-- Anfang Exhibition Image -->
        <div id="exhibitionImageSection">

          <div id="exhibitionNameTitel"><struts:text name="exhibition.name"/></div>
          <div id="exhibitionName"><struts:property value="exhibition.getName(getText('language'))" /></div>

          <div class="exhibitionImageBoxHeader"><struts:text name="exhibition.image"/></div>
          
            <div id="exhibitionImageBox">

                <div class="exhibitionImageBoxRow">
                  <div class="exhibitionImageBoxQuantity">
                    <img src="/image/pictureitem/<struts:property value="%{exhibition.uuid}"/>/thumbnail.png?cache=false" style="width:210px;" />
                  </div>
                </div>
              
            </div>
          

          <div class="pageImageForm">
            <struts:form
                cssClass="form"
                action="addImage"
                namespace="/others/exhibition/image"
                tooltipIconPath="/images/info.png"
                javascriptTooltip="true"
                tooltipDelay="500"
                theme="css_xhtml"
                enctype="multipart/form-data"
                method="post"
            >
              <struts:hidden name="uuid" value="%{exhibition.uuid}"/>
              <fieldset class="fieldset">
                <div class="field">
                  <struts:file key="exhibitionImage.uploadFile" parentTheme="xhtml" labelposition="left">
                    <struts:param name="required" value="true" />
                    <struts:param name="disabled" value="false" />
                  </struts:file>
                </div>
              </fieldset>
              <button
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.upload"/>
              </button>
            </struts:form>
          </div>

          <hr>
        </div>
        <!-- Ende Exhibition Image -->



      </div>
      <!--  Ende MAIN -->



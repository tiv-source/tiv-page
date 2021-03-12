<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="subsumption.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="subsumption.picture.pictureUrls.FULL.url" />">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="subsumption.getContent(getText('language'))" />

    <p class=" bc_bottom">	&nbsp;</p>

    <struts:iterator value="subsumption.contentItems" status="contentItemsStatus">
      <struts:if test="visible">
        <struts:url var="contentItemLink" value="%{url}"/>
        <struts:a href="%{contentItemLink}">
          <div class="informations">
            <div class="information">
              <struts:if test="subsumption.showTitles">
                <h4><struts:property value="getName(getText('language'))" /></h4>
              </struts:if>
              <struts:if test="subsumption.showDates">
                <h6><struts:date name="created" format="dd.MM.yyyy" /></h6>
              </struts:if>
              <struts:if test="subsumption.showDescriptions">
                <p><struts:property value="getDescription(getText('language'))" /></p>
              </struts:if>
            </div>

            <struts:if test="subsumption.showPictures">
              <div class="impression">
                <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
              </div>
            </struts:if>
            <hr>
          </div>
        </struts:a>
      </struts:if>
    </struts:iterator>

    <hr>
  </div>
  <!-- Content Ende -->

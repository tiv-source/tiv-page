<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

    <div class="galleries">

    
  <struts:iterator value="gallery" status="locationStatus">
    <struts:url var="galleryLink" action="index" namespace="/gallery/%{uuid}"/>
    <div class="boxed">
      <div class="vcenter">
        <struts:a href="%{galleryLink}">
          <div class="clickable">
            <div class="holder">
              <div class="frame">
                <img src="/pictures/FULL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
              </div>
            </div>
            <div class="propertie">
              <p><struts:property value="getName(getText('language'))" /></p>
            </div>
          </div>
        </struts:a>
      </div>
    </div> 
  </struts:iterator>

    <hr>
    <struts:if test="previous != null">
      <struts:url var="previousUrl" escapeAmp="false">
        <struts:param name="page" value="%{previous}"/>
      </struts:url>
      <struts:a href="%{previousUrl}">
        <div class="pagination_left">
          <img src="/public/icons/pagination_left_orange.png" alt="">
          <p><struts:text name="pagination.previous" /></p>
        </div>
      </struts:a>
    </struts:if>

    <struts:if test="next != null">
      <struts:url var="nextUrl" escapeAmp="false">
        <struts:param name="page" value="%{next}"/>
      </struts:url>
      <struts:a href="%{nextUrl}">
        <div class="pagination_right">
          <img src="/public/icons/pagination_right_orange.png" alt="">
          <p><struts:text name="pagination.next" /></p>
        </div>
      </struts:a>
    </struts:if>

    </div>
    <hr>


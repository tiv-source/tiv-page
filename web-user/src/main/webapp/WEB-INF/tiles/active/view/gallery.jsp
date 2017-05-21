<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>


    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="gallery.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>

    <h1><struts:property value="gallery.getName(getText('language'))" /></h1>
    <p><struts:property value="gallery.getDescription(getText('language'))" /></p>
    <p class="bc_bottom">&nbsp;</p>

    <div class="gallery">
    
  <struts:iterator value="pictures" status="pictureStatus">
    <struts:url var="galleryLink" action="index" namespace="/gallery/%{gallery.uuid}/%{current}/%{#pictureStatus.count}"/>
    <div class="boxed">
      <div class="vcenter">
        <struts:a href="%{galleryLink}">
          <div class="clickable">
            <div class="holder">
              <div class="frame">
                <img src="/pictures/THUMBNAIL/<struts:property value="pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
              </div>
              <div class="propertie">
                <p><struts:property value="getName(getText('language'))" /></p>
              </div>
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


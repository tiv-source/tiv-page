<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="gallery.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="gallery.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>
  
    <div id="galleryNavigation">
      <struts:url var="aboveUrl" action="index" namespace="/gallery" />
      <struts:a href="%{aboveUrl}">
        <div class="galleryNavigationAbove">
          <img src="<struts:property value="getProperty('return.to.summary.icon')" />" alt="">
          <p><struts:text name="gallery.above" /></p>
        </div>
      </struts:a>
    </div>
    <h1><struts:property value="gallery.getName(getText('language'))" /></h1>
    <p><struts:property value="gallery.getDescription(getText('language'))" /></p>

    <div class="gallery">
    
  <struts:iterator value="pictures" status="pictureStatus">
    <struts:url var="galleryLink" action="index" namespace="/gallery/%{gallery.uuid}/%{current}/%{#pictureStatus.count}"/>
    <div class="boxed">
      <div class="vcenter">
        <struts:a href="%{galleryLink}">
          <div class="clickable">
            <div class="holder">
              <div class="frame">
                <img src="/image/picture/<struts:property value="uuid" />/thumbnail.png" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
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
        <struts:param name="pagination" value="%{previous}"/>
      </struts:url>
      <struts:a href="%{previousUrl}">
        <div class="pagination_left">
          <img src="<struts:property value="getProperty('pagination.icon.left')" />" alt="">
          <p><struts:text name="pagination.previous" /></p>
        </div>
      </struts:a>
    </struts:if>

    <struts:if test="next != null">
      <struts:url var="nextUrl" escapeAmp="false">
        <struts:param name="pagination" value="%{next}"/>
      </struts:url>
      <struts:a href="%{nextUrl}">
        <div class="pagination_right">
          <img src="<struts:property value="getProperty('pagination.icon.right')" />" alt="">
          <p><struts:text name="pagination.next" /></p>
        </div>
      </struts:a>
    </struts:if>
    
    </div>


    <hr>
  </div>
  <!-- Content Ende -->

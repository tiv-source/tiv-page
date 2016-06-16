<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>


    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>

    <struts:property escape="false" value="page.getContent(getText('language'))" />
    
  <struts:iterator value="gallery" status="locationStatus">
    <struts:url var="locationLink" action="index" namespace="/reservation/%{uuid}"/>
    <struts:a href="%{locationLink}">

      <div class="informations <struts:if test="#locationStatus.odd == true ">color3</struts:if><struts:else>color4</struts:else>">
        <div class="information">
         <h4><struts:property value="getName(getText('language'))" /></h4>
         <p><struts:property value="getDescription(getText('language'))" /></p>
        </div>
        
        <div class="impression">
          <img src="/pictures/FULL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
        </div>
        <hr>
      </div>
    
    </struts:a>
  </struts:iterator>

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


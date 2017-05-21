<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>


    <h1><struts:property value="picture.getName(getText('language'))" /></h1>
    <p><struts:property value="picture.getDescription(getText('language'))" /></p>
    <p class="bc_bottom">&nbsp;</p>

    <div class="picture_01">
      <img alt="" src="/pictures/FULL/<struts:property value="picture.pictureUrls.FULL.url" />">

    <hr>
    <struts:if test="previous != null">
      <struts:url var="previousUrl" action="index" namespace="/gallery/%{gallery.uuid}/%{current}/%{#pictureStatus.count}"/>
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



<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />
    <p class="bc_bottom">&nbsp;</p>

    <struts:iterator value="news" status="newsStatus">
      <struts:url var="newsLink" action="index" namespace="/news/%{uuid}"/>

      <struts:a href="%{newsLink}">
        <div class="informations <struts:if test="#eventsStatus.odd == true ">color3</struts:if><struts:else>color4</struts:else>">
          <div class="information">
            <h4><struts:property value="getName(getText('language'))" /></h4>
            <h6><struts:date name="releaseDate" format="dd.MM.yyyy" /></h6>
            <p><struts:property value="getDescription(getText('language'))" /></p>
          </div>
        
          <div class="impression">
            <img src="/pictures/FULL/<struts:property value="picture.pictureUrls.FULL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
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

    
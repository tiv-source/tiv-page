<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

    <p class=" bc_bottom">	&nbsp;</p>

    <struts:iterator value="appointments" status="manualStatus">
      <struts:url var="appointmentLink" action="index" namespace="/appointment/%{uuid}"/>
    
      <div class="informations">
        <struts:a href="%{appointmentLink}">
          <div class="information">
            <h4><struts:property value="getName(getText('language'))" /></h4>
            <h6><struts:date name="pointInTime" nice="true" /></h6>
            <p><struts:property value="getDescription(getText('language'))" /></p>
            <h6>Beginn um <struts:date name="pointInTime" format="HH:mm" /> am <struts:date name="pointInTime" format="dd.MM.yyyy" /></h6>
          </div>
        </struts:a>

        <struts:a href="%{appointmentLink}">
          <div class="impression">
            <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.FULL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
          </div>
        </struts:a>

        <struts:if test="booking">
          <a href="<struts:property escapeHtml="false" value="bookingUrl" />" target="_blank">
            <div class="booking">
              Ticket kaufen
            </div>
          </a>
        </struts:if>
        <hr>
      </div>
      

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

    
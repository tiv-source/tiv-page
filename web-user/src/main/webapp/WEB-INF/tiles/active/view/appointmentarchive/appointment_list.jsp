<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

    <p class=" bc_bottom">	&nbsp;</p>

    <struts:iterator value="appointments" status="manualStatus">
      <struts:url var="appointmentLink" action="index" namespace="/appointmentarchive/%{uuid}"/>
    
      <div class="informations">
        <struts:a href="%{appointmentLink}">
          <div class="information">
            <h4><struts:property value="getName(getText('language'))" /></h4>
            <struts:if test="getProperty('appointment.archive.list.pointInTime.nice') == 'true'">
              <h6><struts:date name="pointInTime" nice="true" /></h6>
            </struts:if>
            <struts:if test="getProperty('appointment.archive.list.description') == 'true'">
              <p><struts:property value="getDescription(getText('language'))" /></p>
            </struts:if>
            <h6>
              <struts:if test="getProperty('appointment.archive.list.pointInTime.time') == 'true'">
                Beginn um <struts:date name="pointInTime" format="HH:mm" /> 
              </struts:if>
              <struts:if test="getProperty('appointment.archive.list.pointInTime.date') == 'true'">
                <struts:if test="getProperty('appointment.archive.list.pointInTime.date.preposition') == 'true'">
                am 
                </struts:if>
                <struts:date name="pointInTime" format="dd.MM.yyyy" /> 
              </struts:if>
              <struts:if test="hasVenue">
                - <struts:property value="venue" />
              </struts:if>
            </h6>
          </div>
        </struts:a>

        <struts:a href="%{appointmentLink}">
          <div class="impression">
            <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.FULL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
          </div>
        </struts:a>

        <hr>
      </div>
      

    </struts:iterator>

    <struts:if test="previous != null">
      <struts:url var="previousUrl" escapeAmp="false">
        <struts:param name="page" value="%{previous}"/>
      </struts:url>
      <struts:a href="%{previousUrl}">
        <div class="pagination_left">
          <img src="<struts:property value="getProperty('pagination.icon.left')" />" alt="<struts:text name="pagination.previous" />">
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
          <img src="<struts:property value="getProperty('pagination.icon.right')" />" alt="<struts:text name="pagination.next" />">
          <p><struts:text name="pagination.next" /></p>
        </div>
      </struts:a>
    </struts:if>

    <hr>
  </div>
  <!-- Content Ende -->


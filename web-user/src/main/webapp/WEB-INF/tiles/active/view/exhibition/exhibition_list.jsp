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

    <p class=" bc_bottom">&nbsp;</p>

  <struts:if test="exhibitions.size() > 0">
    <struts:iterator value="exhibitions" status="exhibitionStatus">
      <struts:url var="exhibitionLink" action="index" namespace="/exhibition/%{technical}"/>
    
      <div class="informations">
        <struts:a href="%{exhibitionLink}">
          <div class="information">
            <h4><struts:property value="getName(getText('language'))" /></h4>
            <struts:if test="getProperty('exhibition.list.moment.nice') == 'true'">
              <h6><struts:date name="moment" nice="true" /></h6>
            </struts:if>
            <struts:if test="getProperty('exhibition.list.description') == 'true'">
              <p><struts:property value="getDescription(getText('language'))" /></p>
            </struts:if>
            <h6>
              <struts:if test="getProperty('exhibition.list.start') == 'true'">
                Die Ausstellung läuft vom <struts:date name="start" format="dd.MM.yyyy" /> 
              </struts:if>
              <struts:if test="getProperty('exhibition.list.end') == 'true'">
                bis zum <struts:date name="end" format="dd.MM.yyyy" /> 
              </struts:if>
            </h6>
          </div>
        </struts:a>

        <struts:a href="%{exhibitionLink}">
          <div class="impression">
            <img src="/image/pictureitem/<struts:property value="%{uuid}"/>/<struts:property value="getProperty('exhibition.list.image')"/>" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />" />
          </div>
        </struts:a>

        <hr>
      </div>
      

    </struts:iterator>
  </struts:if>
  <struts:else>
    <div class="noAppointemnts">Zur Zeit liegen keine Ausstellungen vor.</div>
  </struts:else>

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

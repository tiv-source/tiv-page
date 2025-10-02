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

    <struts:iterator value="manuals" status="manualStatus">
      <struts:url var="manualLink" action="index" namespace="/manual/%{technical}"/>
    
      <struts:a href="%{manualLink}">
        <div class="informations">
          <div class="information">
            <h4><struts:property value="getName(getText('language'))" /></h4>
            <struts:if test="getProperty('manual.list.show.date') == 'true'">
              <h6><struts:date name="created" format="dd.MM.yyyy" /></h6>
            </struts:if>
            <p><struts:property value="getDescription(getText('language'))" /></p>
          </div>
        
          <div class="impression">
            <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
          </div>
          <hr>
        </div>
      </struts:a>

    <struts:if test="getProperty('manual.show.ads') == 'true'">
      <struts:if test="#manualStatus.index == 0">
        <div class="informations">
          <div class="ads">
            <script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
            <!-- Comarchiv Start -->
            <ins class="adsbygoogle" style="display:block" data-ad-client="ca-pub-0384480293276302" data-ad-slot="9662011858" data-ad-format="auto"></ins>
            <script>
              (adsbygoogle = window.adsbygoogle || []).push({});
            </script>
            <!-- Comarchiv Ende -->
          </div>
		</div>
      </struts:if>
    </struts:if>

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

    <hr>
  </div>
  <!-- Content Ende -->

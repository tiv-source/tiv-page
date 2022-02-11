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

    <struts:iterator value="pdfs" status="manualStatus">
      <struts:url var="pdfLink" value="/pdf/%{uuid}/document.pdf" includeContext="false"/>
    
      <struts:a href="%{pdfLink}">
        <div class="newspaperArticle">
          <div class="articleInformation">
            <h4><struts:property value="getName(getText('language'))" /></h4>

            <struts:if test="getProperty('pdf.list.show.edition') == 'true'">
              <div class="edition">
                <struts:if test="getProperty('pdf.list.show.edition.description') == 'true'">
                  <struts:text name="pdf.edition" /> 
                </struts:if>
                <struts:property value="edition" />
              </div>
            </struts:if>

            <struts:if test="getProperty('pdf.list.show.dateOfPublication') == 'true'">
              <div class="dateOfPublication">
                <struts:if test="getProperty('pdf.list.show.dateOfPublication.description') == 'true'">
                  <struts:if test="getProperty('pdf.list.show.dateOfPublication.description.nice') == 'true'">
                    <struts:text name="pdf.dateOfPublication" /> 
                  </struts:if>
                  <struts:else>
                    <struts:text name="pdf.dateOfPublication.on" /> 
                  </struts:else>
                </struts:if>
                <struts:if test="getProperty('pdf.list.show.dateOfPublication.nice') == 'true'">
                  <struts:date name="dateOfPublication" nice="true" />
                </struts:if>
                <struts:else>
                  <struts:date name="dateOfPublication" format="dd.MM.yyyy" />
                </struts:else>
              </div>
            </struts:if>

            <struts:if test="getProperty('pdf.list.show.description') == 'true'">
              <div class="description">
                <struts:if test="getProperty('pdf.list.show.description.title') == 'true'">
                  <struts:text name="pdf.description" />
                </struts:if>
                <br/>
                <struts:property value="getDescription(getText('language'))" />
              </div>
            </struts:if>
          </div>
          
          <div class="articleMetadata">

            <struts:if test="getProperty('pdf.list.show.downloadCounter') == 'true'">
              <div class="downloadCounter">
               <struts:property value="downloadCounter" /> 
                <struts:if test="getProperty('pdf.list.show.downloadCounter.description') == 'true'">
                  <struts:text name="pdf.downloadCounter" />
                </struts:if>
              </div>
            </struts:if>

            <struts:if test="getProperty('pdf.list.show.created') == 'true'">
              <div class="created">
                <struts:if test="getProperty('pdf.list.show.created.description') == 'true'">
                  <struts:if test="getProperty('pdf.list.show.created.description.nice') == 'true'">
                    <struts:text name="pdf.uploaded" /> 
                  </struts:if>
                  <struts:else>
                    <struts:text name="pdf.uploaded.on" /> 
                  </struts:else>
                </struts:if>
                <struts:if test="getProperty('pdf.list.show.created.nice') == 'true'">
                  <struts:date name="created" nice="true" />
                </struts:if>
                <struts:else>
                  <struts:date name="created" format="dd.MM.yyyy" />
                </struts:else>
              </div>
            </struts:if>

          </div>

          <div class="articleImpression">
            <img src="/image/pdf/<struts:property value="image.uuid"/>/<struts:property value="getProperty('pdf.list.image.size')"/>"  alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />"/>
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

    <hr>
  </div>
  <!-- Content Ende -->

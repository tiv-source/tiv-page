<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="location.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="location.picture.pictureUrls.FULL.url" />">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <h1><struts:property value="location.getName(getText('language'))" /></h1>
    <struts:if test="getProperty('location.show.description') == 'true'">
      <p><struts:property value="location.getDescription(getText('language'))" /></p>
    </struts:if>
    <p class=" bc_bottom">	&nbsp;</p>

    <struts:iterator value="events" status="eventsStatus">
      <struts:url var="eventLink" action="index" namespace="/event/%{uuid}"/>

      <struts:a href="%{eventLink}">
        <div class="informations <struts:if test="#eventsStatus.odd == true ">color3</struts:if><struts:else>color4</struts:else>">
          <div class="information">
            <h4><struts:property value="getName(getText('language'))" /> am <struts:date name="beginning" format="dd.MM.yyyy" /></h4>
            <p>Preis pro Person: <struts:text name="format.money"><struts:param name="value" value="price"/></struts:text></p>
            <p>von <struts:date name="beginning" format="HH:mm" /> Uhr bis <struts:date name="ending" format="HH:mm" /> Uhr</p>
            <struts:if test="reservation">
              <p>Online Reservierung m&ouml;glich bis zum <struts:date name="deadline" format="dd.MM.yyyy" /> um <struts:date name="deadline" format="HH:mm" /> Uhr</p>
            </struts:if>
            <struts:else>
              <p>Leider ist eine Online Reservierung nicht mehr m&ouml;glich, da wir an diesem Termin ausgebucht sind.</p>
            </struts:else>
          </div>
        
          <div class="impression">
            <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
          </div>
        
          <hr>
        </div>
      </struts:a>

<!-- Microdata Anfang -->
<script type="application/ld+json">
{
  "@context": "http://schema.org",
  "@type": "FoodEvent",
  "name": "<struts:property value="getName(getText('language'))" /> - <struts:property value="location.getName(getText('language'))" />",
  "startDate" : "<struts:date name="beginning" format="yyyy-MM-dd" />T<struts:date name="beginning" format="HH:mm" />",
  "location" : {
    "@type" : "Place",
    "name" : "<struts:property value="location.getName(getText('language'))" />",
    "address" : {
      "@type" : "PostalAddress",
      "name" : "<struts:property value="location.getName(getText('language'))" />",
      "addressCountry" : "DE",
      "addressLocality" : "<struts:property value="location.address.city" />",
      "addressRegion" : "NRW",
      "postalCode" : "<struts:property value="location.address.zip" />",
      "streetAddress" : "<struts:property value="location.address.street" />"
    }
  },
  "offers" : {
    "@type" : "Offer",
    "price" : "<struts:property value="price" />",
    "priceCurrency" : "EUR",
    "url" : "<struts:property value="getProperty('server.base.url')" /><struts:property value="%{eventLink}" />"
  }    
}


</script>
<!-- Microdata Ende -->

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

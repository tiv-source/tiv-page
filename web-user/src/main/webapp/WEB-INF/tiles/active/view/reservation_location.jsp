<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div>
      <img alt="" src="/pictures/FULL/<struts:property value="location.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>

    <h1><struts:property value="location.getName(getText('language'))" /></h1>

    <struts:iterator value="events" status="eventsStatus">
      <struts:url var="eventLink" action="index" namespace="/event/%{uuid}"/>

      <struts:a href="%{eventLink}">
        <div class="informations <struts:if test="#eventsStatus.odd == true ">color3</struts:if><struts:else>color4</struts:else>">
          <div class="information">
            <h4><struts:property value="getName(getText('language'))" /> am <struts:date name="beginning" format="dd.MM.yyyy" /></h4>
            <p>Preis pro Person: <struts:text name="format.money"><struts:param name="value" value="price"/></struts:text></p>
            <p>von <struts:date name="beginning" format="HH:mm" /> Uhr bis <struts:date name="ending" format="HH:mm" /> Uhr</p>
            <p>Online Reservierung möglich bis zum <struts:date name="deadline" format="dd.MM.yyyy" /> um <struts:date name="deadline" format="HH:mm" /> Uhr</p>
          </div>
        
          <div class="impression">
            <img src="/pictures/FULL/<struts:property value="picture.pictureUrls.FULL.url" />" alt="<struts:property value="getName(getText('language'))" />" title="<struts:property value="getName(getText('language'))" />">
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
    "url" : "<struts:text name="server.base.url" /><struts:property value="%{eventLink}" />"
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

    
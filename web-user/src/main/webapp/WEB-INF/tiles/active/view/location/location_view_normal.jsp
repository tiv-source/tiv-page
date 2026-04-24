<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <h1><struts:property value="location.getName(getText('language'))" /></h1>
    
    <div class="location1_1">
      
      <div class="adress">
        <h5>Anschrift:</h5>
        <p><struts:property value="location.address.street" /></p>
        <p><struts:property value="location.address.zip" /> <struts:property value="location.address.city" /></p>
      </div>
      
      <div class="location_map">
        <a href="http://www.openstreetmap.org/?mlat=<struts:property value="location.latitude" />&mlon=<struts:property value="location.longitude" />#map=19/<struts:property value="location.latitude" />/<struts:property value="location.longitude" />">
          <img alt="Auf Openstreetmap ansehen" src="/image/osm/<struts:property value="location.uuid" />/normal.png">
        </a>
      </div>
		
        
		<h5 class="distance_top3">Kontakt:</h5>
        
		<p>
		  <img src="<struts:property value="getProperty('project.icon.path')"/>telephone.svg"
               alt="Telefon"
               align="absmiddle" 
               style="width: 20px; height: 20px; float: left; margin-bottom: 2px;">
		    <struts:a href="%{location.contactDetails.telephoneAsLink}">
		      &nbsp;&nbsp; <struts:property value="location.contactDetails.telephone" />
		    </struts:a>
		</p>

		<p>
          <img src="<struts:property value="getProperty('project.icon.path')"/>mail.svg" 
               alt="E-Mail" 
               align="absmiddle"
               style="width: 20px; height: 20px; float: left;">
		  &nbsp;&nbsp; <struts:property value="location.contactDetails.email" />
		</p>
    		
		<p>
          <img src="<struts:property value="getProperty('project.icon.path')"/>world.svg" 
               alt="Website" 
               align="absmiddle"
               style="width: 20px; height: 20px; float: left;">
		  &nbsp;&nbsp; <struts:property value="location.contactDetails.homepage" />
		</p>
    			 

    	<div class="location_opening">
    	    
          <h5>Öffnungszeiten:</h5>

          <table>
            <colgroup>
              <col width="110">
              <col width="">
            </colgroup>
            <tbody>
            <struts:iterator value="location.openingHours" status="openingHoursStatus">
            <tr>
              <td><struts:property value="getText(weekday)" />:</td>
              <td><struts:property value="openHourToString()" />-<struts:property value="closeHourToString()" /></td>
            </tr>
            </struts:iterator>
            </tbody>
          </table>

		</div>
        
    </div>
     
     <hr>

<struts:url var="canonicalUrl" />
<!-- Microdata Anfang -->
<script type="application/ld+json">
{
  "@context": "http://schema.org",
  "@type": "Restaurant",
  "address": {
    "@type": "PostalAddress",
    "addressCountry" : "DE",
    "addressLocality" : "<struts:property value="location.address.city" />",
    "addressRegion" : "NRW",
    "postalCode" : "<struts:property value="location.address.zip" />",
    "streetAddress" : "<struts:property value="location.address.street" />"
  },
  "name": "<struts:property value="location.getName(getText('language'))" />",
  "openingHours": [
    <struts:iterator value="location.openingHours" status="openingHoursStatus">"<struts:property value="(weekday.toString()).substring(0, 1)" /><struts:property value="(weekday.toString()).substring(1, 2).toLowerCase()" /> <struts:property value="open" />-<struts:property value="close" />"<struts:if test="#openingHoursStatus.isLast() != true">,</struts:if>
    </struts:iterator>
  ],
  "telephone": "<struts:property value="location.contactDetails.telephone" />",
  "url": "<struts:property value="getProperty('server.base.url')" /><struts:property value="canonicalUrl" />"
}
</script>
<!-- Microdata Ende -->

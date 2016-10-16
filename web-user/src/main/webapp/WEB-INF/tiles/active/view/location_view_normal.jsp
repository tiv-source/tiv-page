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
        <picture>
				<source media="(min-width:1701px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1701.png">
				<source media="(min-width:1501px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1501.png">
				<source media="(min-width:1401px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1401.png">
				<source media="(min-width:1321px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1321.png">
				<source media="(min-width:1291px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1291.png">
				<source media="(min-width:1101px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1101.png">
				<source media="(min-width:1001px)" srcset="/osmcache/<struts:property value="location.uuid" />_w1001.png">
				<source media="(min-width: 951px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0951.png">
				<source media="(min-width: 901px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0901.png">
				<source media="(min-width: 701px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0701.png">
				<source media="(min-width: 641px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0641.png">
				<source media="(min-width: 619px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0619.png">	
				<source media="(min-width: 581px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0581.png">
				<source media="(min-width: 201px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0201.png">

				<!-- Fallback -->
				<img src="/osmcache/<struts:property value="location.uuid" />_w0201.png" 
					srcset="/osmcache/<struts:property value="location.uuid" />_w0201.png">
			</picture>
		</div>
        
		<h5 class="distance_top3">Kontakt:</h5>
        
		<p>
		  <img src="<struts:property value="getProperty('project.icon.path')"/>telephone.png"
               alt="Telefon"
               align="absmiddle" 
               style="width: 20px; height: 20px; float: left; margin-bottom: 2px;">
		  &nbsp;&nbsp; <struts:property value="location.contactDetails.telephone" />
		</p>

		<p>
		  <img src="<struts:property value="getProperty('project.icon.path')"/>fax.png" 
               alt="Fax" 
               align="absmiddle"
               style="width: 20px; height: 20px; float: left;">
		  &nbsp;&nbsp; <struts:property value="location.contactDetails.fax" />
		</p>
    		
		<p>
          <img src="<struts:property value="getProperty('project.icon.path')"/>email.png" 
               alt="E-Mail" 
               align="absmiddle"
               style="width: 20px; height: 20px; float: left;">
		  &nbsp;&nbsp; <struts:property value="location.contactDetails.email" />
		</p>
    		
		<p>
          <img src="<struts:property value="getProperty('project.icon.path')"/>world.png" 
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
              <td><struts:property value="open" />-<struts:property value="close" /></td>
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

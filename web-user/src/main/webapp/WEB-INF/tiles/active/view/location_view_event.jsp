<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    
		<h1><struts:property value="location.getName(getText('language'))" /></h1>
	
	
	 	<div class="local_current">
		
			<div class="local_content">
			
            <h4>Nächste Termine:</h4>

    	<p class=" bc_bottom2">	&nbsp;</p>

    	  <struts:iterator value="events" status="eventsStatus">
            <struts:url var="eventLink" action="index" namespace="/event/%{uuid}"/>
            <struts:a href="%{eventLink}">
          		<div class="info_location2 bc_bottom3">
                  <h4><struts:property value="getName(getText('language'))" /> am <struts:date name="beginning" format="dd.MM.yyyy" /></h4>
                  <p>Preis pro Person: <struts:text name="format.money"><struts:param name="value" value="price"/></struts:text></p>
                  <p>von <struts:date name="beginning" format="HH:mm" /> Uhr bis <struts:date name="ending" format="HH:mm" /> Uhr</p>
                  <p>Online Reservierung möglich bis zum <struts:date name="deadline" format="dd.MM.yyyy" /> um <struts:date name="deadline" format="HH:mm" /> Uhr</p>
          		</div>
            </struts:a>
    	  </struts:iterator>

		
			</div>
	
		</div>

		<div class="location2 distance_top">
    
		<div class="adress2">	 
    
          <h5>Anschrift:</h5>
          <p><struts:property value="location.address.street" /></p>
          <p><struts:property value="location.address.zip" /> <struts:property value="location.address.city" /></p>
          
      </div>

      <a href="http://www.openstreetmap.org/?mlat=<struts:property value="location.latitude" />&mlon=<struts:property value="location.longitude" />#map=19/<struts:property value="location.latitude" />/<struts:property value="location.longitude" />">
		<div class="location_map">  
			<picture>
				<source media="(min-width:1401px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0641.png">
				<source media="(min-width:1101px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0641.png">
				<source media="(min-width:1001px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0581.png">	
				<source media="(min-width: 201px)" srcset="/osmcache/<struts:property value="location.uuid" />_w0201.png">

				<!-- Fallback -->
				<img src="/osmcache/<struts:property value="location.uuid" />_w0201.png" 
					srcset="/osmcache/<struts:property value="location.uuid" />_w0201.png">
			</picture>
        </div>
      </a>
        
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

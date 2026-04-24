<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="page.uuid" />/large.png">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />
    
  <struts:iterator value="list" status="locationStatus">
    <struts:url var="locationLink" action="index" namespace="/location/%{uuid}"/>
    <div class="locations <struts:if test="#locationStatus.odd == true ">color1</struts:if><struts:else>color2</struts:else>">
      <div class="location">
        <struts:a href="%{locationLink}">
          <h2><struts:property value="getName(getText('language'))" /></h2>
          <p><struts:property value="address.street" /></p>
          <p><struts:property value="address.zip" /> <struts:property value="address.city" /></p>
        </struts:a>
      </div>

      <div class="map">
        <a href="http://www.openstreetmap.org/?mlat=<struts:property value="latitude" />&mlon=<struts:property value="longitude" />#map=19/<struts:property value="latitude" />/<struts:property value="longitude" />">
          <img alt="Auf Openstreetmap ansehen" src="/image/osm/<struts:property value="uuid" />/normal.png">
        </a>
      </div>

      <div class="open">
        <struts:a href="%{locationLink}">
          <h3>Öffnungszeiten:</h3>
          <struts:iterator value="openingHours" status="openingHoursStatus">
            <section class="openingHours">
              <p class="weekday"><struts:property value="getText(weekday)" />:</p>
              <p class="time"><struts:property value="openHourToString()" />-<struts:property value="closeHourToString()" /></p>
            </section>
          </struts:iterator>
        </struts:a>
      </div>

      
      <struts:a href="%{locationLink}">
        <div class="contacts">
          <h5>Kontakt:</h5>

          <table>
            <colgroup>
              <col width="35">
              <col width="">
            </colgroup>

            <tr>
              <td>
                <img src="<struts:property value="getProperty('project.icon.path')"/>telephone.svg"
                     alt="Telefon"
                     align="absmiddle">
              </td>
              <td><struts:a href="%{contactDetails.telephoneAsLink}"><struts:property value="contactDetails.telephone" /></struts:a></td>
            </tr>

          <struts:if test="getProperty('location.page.mobile') == 'true'">
            <tr>
              <td>
                <img src="<struts:property value="getProperty('project.icon.path')"/>mobile.svg" 
                     alt="Mobil" 
                     align="absmiddle">
              </td>
              <td><struts:property value="contactDetails.mobile" /></td>
            </tr>
          </struts:if>

            <tr>
              <td>
                <img src="<struts:property value="getProperty('project.icon.path')"/>mail.svg" 
                     alt="E-Mail" 
                     align="absmiddle">
              </td>
              <td><struts:property value="contactDetails.email" /></td>
            </tr>
            <tr>
              <td>
                <img src="<struts:property value="getProperty('project.icon.path')"/>world.svg" 
                     alt="Website" 
                     align="absmiddle">
              </td>
              <td><struts:property value="contactDetails.homepage" /></td>
            </tr>
          </table>
        </div>
      </struts:a>

      <hr>
    </div>
    
  </struts:iterator>

    <hr>
  </div>
  <!-- Content Ende -->


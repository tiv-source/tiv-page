<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

  <!-- Content Anfang -->
  <div id=content>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />
    
  <struts:iterator value="list" status="locationStatus">
    <struts:url var="locationLink" action="index" namespace="/location/%{uuid}"/>
    <div class="locations <struts:if test="#locationStatus.odd == true ">color1</struts:if><struts:else>color2</struts:else>">
      <struts:a href="%{locationLink}">
        <div class="location">
          <h4><struts:property value="getName(getText('language'))" /></h4>
          <p><struts:property value="address.street" /></p>
          <p><struts:property value="address.zip" /> <struts:property value="address.city" /></p>
        </div>
      </struts:a>						

      <a href="http://www.openstreetmap.org/?mlat=<struts:property value="latitude" />&mlon=<struts:property value="longitude" />#map=19/<struts:property value="latitude" />/<struts:property value="longitude" />">
        <div id="map">
          <img src="/osmcache/<struts:property value="uuid" />_wdefault.png" />
        </div>
      </a>
      
      <struts:a href="%{locationLink}">  
        <div class="open">
          <h5>Öffnungszeiten:</h5>

          <table>
            <colgroup>
              <col width="110">
              <col width="">
            </colgroup>
            <struts:iterator value="openingHours" status="openingHoursStatus">
            <tr>
              <td><struts:property value="getText(weekday)" />:</td>
              <td><struts:property value="open" />-<struts:property value="close" /></td>
            </tr>
            </struts:iterator>
          </table>
        </div>
      </struts:a>
      
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
                <img src="<struts:property value="getProperty('project.icon.path')"/>telephone.png"
                     alt="Telefon"
                     align="absmiddle">
              </td>
              <td><struts:a href="%{contactDetails.telephoneAsLink}"><struts:property value="contactDetails.telephone" /></struts:a></td>
            </tr>

          <struts:if test="getProperty('location.page.mobile') == 'true'">
            <tr>
              <td>
                <img src="<struts:property value="getProperty('project.icon.path')"/>mobile.png" 
                     alt="Mobil" 
                     align="absmiddle">
              </td>
              <td><struts:property value="contactDetails.mobile" /></td>
            </tr>
          </struts:if>

            <tr>
              <td>
                <img src="<struts:property value="getProperty('project.icon.path')"/>fax.png" 
                     alt="Fax" 
                     align="absmiddle">
              </td>
              <td><struts:property value="contactDetails.fax" /></td>
            </tr>
            <tr>
              <td>
                <img src="<struts:property value="getProperty('project.icon.path')"/>email.png" 
                     alt="E-Mail" 
                     align="absmiddle">
              </td>
              <td><struts:property value="contactDetails.email" /></td>
            </tr>
            <tr>
              <td>
                <img src="<struts:property value="getProperty('project.icon.path')"/>world.png" 
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


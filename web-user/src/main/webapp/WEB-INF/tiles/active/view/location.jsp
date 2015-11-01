<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>


    <struts:property escape="false" value="page.getContent(getText('language'))" />
    
    <struts:iterator value="list" status="locationStatus">
      <div class="locations <struts:if test="#locationStatus.odd == true ">color1</struts:if><struts:else>color2</struts:else>">
        <div class="location">
          <h4><struts:property value="getName(getText('language'))" /></h4>
          <p><struts:property value="address.street" /></p>
          <p><struts:property value="address.zip" /> <struts:property value="address.city" /></p>
        </div>						

        <div id="map">
          <img src="http://staticmap.openstreetmap.de/staticmap.php?center=<struts:property value="latitude" />,<struts:property value="longitude" />&zoom=18&size=280x200&maptype=mapnik&markers=<struts:property value="latitude" />,<struts:property value="longitude" />,lightblue" />
        </div>
        
        <div class="open">
          <table>
            <tr>
              <th>Öffnungszeiten:</th>
            </tr>
            <struts:iterator value="openingHours" status="openingHoursStatus">
            <tr>
              <td><struts:property value="weekday" />:</td>
              <td><struts:property value="open" />-<struts:property value="close" /></td>
            </tr>
            </struts:iterator>
          </table>
        </div>
        
        <div class="contacts">
          <h5>Kontakt:</h5>
          <p><img src="/public/icons/telephone.png" alt="Telefon" align="absmiddle"><struts:property value="contactDetails.telephone" /></p>
          <p><img src="/public/icons/mobile.png" alt="Mobil" align="absmiddle"><struts:property value="contactDetails.mobile" /></p>
          <p><img src="/public/icons/printer.png" alt="Fax" align="absmiddle"><struts:property value="contactDetails.fax" /></p>
          <p><img src="/public/icons/email.png" alt="E-Mail" align="absmiddle"><struts:property value="contactDetails.email" /></p>
          <p><img src="/public/icons/world.png" alt="Website" align="absmiddle"><struts:property value="contactDetails.homepage" /></p>
        </div>
      </div>
    </struts:iterator>




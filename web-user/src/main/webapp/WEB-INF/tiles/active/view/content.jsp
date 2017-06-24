<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/de_DE/sdk.js#xfbml=1&version=v2.5";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

  <struts:if test="page.pictureOnPage">
    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
    </div>
  </struts:if>

    <div id="social_net1">
      <div class="picturebox1">
		<div class="fb-page" data-href="<struts:property value="getProperty('facebook.page.url')"/>" data-tabs="timeline" data-width="300" data-height="560" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="false">
		  <div class="fb-xfbml-parse-ignore">
		    <blockquote cite="<struts:property value="getProperty('facebook.page.url')"/>">
		      <a href="<struts:property value="getProperty('facebook.page.url')"/>"><struts:property value="getProperty('facebook.page.name')"/></a>
		    </blockquote>
		  </div>
		</div>
      </div>
    </div>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

    <struts:if test="getProperty('home.location') == 'true'">
    <div id="actions">

      <struts:url var="eventLinkLeft" action="index" namespace="/event/%{leftLocation.uuid}"/>
      <struts:a href="%{eventLinkLeft}">
        <div class="action">
          <h5>
            Nächster Termin im<br>
            <struts:property value="leftLocation.location.getName(getText('language'))" />
          </h5>
          <div class="info">
            <h3><struts:property value="leftLocation.getName(getText('language'))" /> am <struts:date name="leftLocation.beginning" format="dd.MM.yyyy" /></h3>
            <p>Preis pro Person: <struts:text name="format.money"><struts:param name="value" value="leftLocation.price"/></struts:text></p>
            <p>von <struts:date name="leftLocation.beginning" format="HH:mm" /> Uhr bis <struts:date name="leftLocation.ending" format="HH:mm" /> Uhr</p>
            <p>Online Reservierung möglich bis zum <struts:date name="leftLocation.deadline" format="dd.MM.yyyy" /> um <struts:date name="leftLocation.deadline" format="HH:mm" /> Uhr</p>
          
            <div class="pic">
              <img src="/pictures/FULL/<struts:property value="leftLocation.picture.pictureUrls.FULL.url" />" alt="<struts:property value="leftLocation.getName(getText('language'))" />" title="<struts:property value="leftLocation.getName(getText('language'))" />">
            </div>
          </div>
        </div>
      </struts:a>
   

      <struts:url var="eventLinkRight" action="index" namespace="/event/%{rightLocation.uuid}"/>
      <struts:a href="%{eventLinkRight}">
        <div class="action">
          <h5>
            Nächster Termin im<br>
            <struts:property value="rightLocation.location.getName(getText('language'))" />
          </h5>
          <div class="info">
            <h3><struts:property value="rightLocation.getName(getText('language'))" /> am <struts:date name="rightLocation.beginning" format="dd.MM.yyyy" /></h3>
            <p>Preis pro Person: <struts:text name="format.money"><struts:param name="value" value="rightLocation.price"/></struts:text></p>
            <p>von <struts:date name="rightLocation.beginning" format="HH:mm" /> Uhr bis <struts:date name="rightLocation.ending" format="HH:mm" /> Uhr</p>
            <p>Online Reservierung möglich bis zum <struts:date name="rightLocation.deadline" format="dd.MM.yyyy" /> um <struts:date name="rightLocation.deadline" format="HH:mm" /> Uhr</p>
            <div class="pic">
              <img src="/pictures/FULL/<struts:property value="rightLocation.picture.pictureUrls.FULL.url" />" alt="<struts:property value="rightLocation.getName(getText('language'))" />" title="<struts:property value="rightLocation.getName(getText('language'))" />">
            </div>
          </div>
        </div>
      </struts:a>      

      <hr>
    </div>
    </struts:if>

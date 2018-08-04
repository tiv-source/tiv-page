<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div id="fb-root"></div>
    <script>
    (function(d, s, id) {
    	var js, fjs = d.getElementsByTagName(s)[0];
    	if (d.getElementById(id)) return;
    	js = d.createElement(s); js.id = id;
    	js.src = "//connect.facebook.net/de_DE/sdk.js#xfbml=1&version=v2.5";
    	fjs.parentNode.insertBefore(js, fjs);
    	}(document, 'script', 'facebook-jssdk'));
    </script>
    <div id="social_net1">
      <div class="picturebox1">
		<div class="fb-page" data-href="<struts:property value="getProperty('facebook.page.url')"/>" data-tabs="timeline" data-width="<struts:property value="getProperty('facebook.on.home.data.width')"/>" data-height="<struts:property value="getProperty('facebook.on.home.data.height')"/>" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="false">
		  <div class="fb-xfbml-parse-ignore">
		    <blockquote cite="<struts:property value="getProperty('facebook.page.url')"/>">
		      <a href="<struts:property value="getProperty('facebook.page.url')"/>"><struts:property value="getProperty('facebook.page.name')"/></a>
		    </blockquote>
		  </div>
		</div>
      </div>
    </div>

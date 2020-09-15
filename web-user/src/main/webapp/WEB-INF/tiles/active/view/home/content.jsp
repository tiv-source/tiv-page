<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <struts:if test="getProperty('home.slider') == 'true'">
      <struts:include value="content_slider.jsp" />
    </struts:if>
    <struts:else>
      <struts:if test="page.pictureOnPage">
        <div id="sitePicture">
          <img alt="" src="/pictures/FULL/<struts:property value="page.picture.pictureUrls.FULL.url" />" style="width: 100%;">
        </div>
      </struts:if>
    </struts:else>

    <struts:if test="getProperty('facebook.on.home') == 'true'">
      <struts:include value="content_facebook.jsp" />
    </struts:if>

    <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />

    <struts:if test="getProperty('news.on.home') == 'true'">
      <struts:include value="content_news.jsp" />
    </struts:if>

    <struts:if test="getProperty('home.location') == 'true'">
      <struts:include value="content_event.jsp" />
    </struts:if>

    <struts:if test="getProperty('home.appointment.slider')">
      <struts:if test="sliderList.size() > 0">
        <struts:include value="content_appointment_slider.jsp" />
      </struts:if>
    </struts:if>




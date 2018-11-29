<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <!-- Anfang des Sliders -->
    <div class="cssSlider">
        
      <!-- Die einzelnen Elemente des Sliders -->
      <ul id="homeSlider" class="rslides">
        <struts:iterator value="homeSliderList" status="silderPictureStatus">
          <li>
            <struts:if test="clickable">
              <struts:a href="%{url}">
                <img src="/image/slider/<struts:property value="image.uuid" />/original.png" alt="" width="1200" height="345">
              </struts:a>
            </struts:if>
            <struts:else>
              <img src="/image/slider/<struts:property value="image.uuid" />/original.png" alt="" width="1200" height="345">
            </struts:else>
          </li>
        </struts:iterator>
      </ul>

    </div>

<script>
$(function () {
  $("#homeSlider").responsiveSlides({
    nav: <struts:property value="getProperty('home.slider.nav')"/>,
    maxwidth: <struts:property value="getProperty('home.slider.maxwidth')"/>,
    speed: <struts:property value="getProperty('home.slider.speed')"/>,
    timeout: <struts:property value="getProperty('home.slider.timeout')"/>,
    pager: <struts:property value="getProperty('home.slider.pager')"/>,
    pause: <struts:property value="getProperty('home.slider.pause')"/>,
    pauseControls: <struts:property value="getProperty('home.slider.pauseControls')"/>
  });
});
</script>

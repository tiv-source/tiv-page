<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

      <!-- Anfang des Sliders -->
      <div class="cssSlider">

        <!-- Steuerelemente des Sliders -->
        <struts:iterator value="sliderList" status="silderInputStatus">
          <struts:if test="#silderInputStatus.index == 0">
            <input name="slider" id="slide01" checked="checked" type="radio">
          </struts:if>
          <struts:else>
            <input name="slider" id="slide0<struts:property value="#silderInputStatus.count" />" type="radio">
          </struts:else>
        </struts:iterator>
        
        <!-- Die einzelnen Elemente des Sliders -->
        <ul class="sliderElements sliderElementsWidth">
          <struts:iterator value="sliderList" status="silderPictureStatus">
            <struts:url var="appointmentLink" action="index" namespace="/appointment/%{uuid}"/>
            <li>
              <struts:a href="%{appointmentLink}">
                <figure>
                  <img src="/pictures/NORMAL/<struts:property value="picture.pictureUrls.NORMAL.url" />" alt="" width="600" height="400">
                  <figcaption>
                    <struts:property value="getName(getText('language'))" /><br>
                    <struts:date name="pointInTime" format="dd.MM.yyyy" /> 
                    <struts:if test="hasVenue">
                      - <struts:property value="venue" />
                    </struts:if>
                  </figcaption>
                </figure>
              </struts:a>
            </li>
          </struts:iterator>
        </ul>
 
        <!-- Die Steuerung des Sliders -->
        <ul class="sliderControls">
          <struts:iterator value="sliderList" status="silderControlStatus">
            <struts:if test="#silderControlStatus.index == 0">
              <li><label id="slabel01" for="slide01">1</label></li>
            </struts:if>
            <struts:else>
              <li><label id="slabel0<struts:property value="#silderControlStatus.count" />" for="slide0<struts:property value="#silderControlStatus.count" />"><struts:property value="#silderControlStatus.count" /></label></li>
            </struts:else>
          </struts:iterator>
        </ul>

      </div>
<struts:hidden id="sliderListSize" value="%{sliderList.size()}" name="sliderListSize" theme="simple"/>
<script type="text/javascript">
$(function sliderOn() {
	// console.log("Hier bin ich.");
	var radioLength = $("#sliderListSize").val();
	var intervalLength = $("#sliderListSize").val();
	radioLength = parseInt(radioLength) + parseInt(1);
	// console.log("Radio Länge " + radioLength)
	var labelArray = [];
	for (i = 1; i < radioLength; i++) {
		// console.log("Fülle array mit " + i)
		labelArray.push("#slabel0" + i);
	}
	// console.log("Array Länge " + labelArray.length)
	var interval = 1;
	var intervalId = setInterval(function(){
		if(interval < intervalLength) {
			// console.log("Jetzt wechseln.");
			// console.log("Inhalt Array " + labelArray[interval]);
			$(labelArray[interval]).click();
			interval++;
		} else {
			clearInterval(intervalId);
			$(labelArray[0]).click();
			sliderOn();
		}
	}, 10000);
});
</script>
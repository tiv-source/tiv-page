<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <!-- Anfang des Sliders -->
    <div class="cssSlider">

      <!-- Steuerelemente des Sliders -->
      <struts:iterator value="homeSliderList" status="silderInputStatus">
        <struts:if test="#silderInputStatus.index == 0">
          <input name="slider" id="slide01" checked="checked" type="radio">
        </struts:if>
        <struts:else>
          <input name="slider" id="slide0<struts:property value="#silderInputStatus.count" />" type="radio">
        </struts:else>
      </struts:iterator>
        
      <!-- Die einzelnen Elemente des Sliders -->
      <ul class="homeSliderElements homeSliderElementsWidth">
        <struts:iterator value="homeSliderList" status="silderPictureStatus">
          <li>
            <struts:a href="%{url}">
              <figure>
                <img src="/image/slider/<struts:property value="image.uuid" />/original.png" alt="" width="1200" height="345">
              </figure>
            </struts:a>
          </li>
        </struts:iterator>
      </ul>
 
      <!-- Die Steuerung des Sliders -->
      <ul class="homeSliderControls">
        <struts:iterator value="homeSliderList" status="silderControlStatus">
          <struts:if test="#silderControlStatus.index == 0">
            <li><label id="slabel01" for="slide01">1</label></li>
          </struts:if>
          <struts:else>
           <li><label id="slabel0<struts:property value="#silderControlStatus.count" />" for="slide0<struts:property value="#silderControlStatus.count" />"><struts:property value="#silderControlStatus.count" /></label></li>
          </struts:else>
        </struts:iterator>
      </ul>

    </div>


<struts:hidden id="homeSliderListSize" value="%{homeSliderList.size()}" name="sliderListSize" theme="simple"/>
<script type="text/javascript">
$(function sliderOn() {
	// console.log("Hier bin ich.");
	var radioLength = $("#homeSliderListSize").val();
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
		if(interval < 3) {
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
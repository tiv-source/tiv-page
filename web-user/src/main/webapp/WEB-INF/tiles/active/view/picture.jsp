<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <h1><struts:property value="gallery.getName(getText('language'))" /></h1>

    <div id="touchbox" class="bigpic">
      <div class="bigpic_box">
        <img alt="" src="/pictures/FULL/<struts:property value="picture.pictureUrls.FULL.url" />">

    <struts:if test="previousPicture != null">
      <struts:url var="previousUrl" action="index" namespace="/gallery/%{gallery.uuid}/%{previous}/%{previousPicture}"/>
      <struts:a href="%{previousUrl}">
        <div class="pagination_left">
          <img src="/public/icons/pagination_left_orange.png" alt="">
          <p><struts:text name="pagination.previous" /></p>
        </div>
      </struts:a>
    </struts:if>

    <struts:if test="nextPicture != null">
      <struts:url var="nextUrl" action="index" namespace="/gallery/%{gallery.uuid}/%{next}/%{nextPicture}"/>
      <struts:a href="%{nextUrl}">
        <div class="pagination_right">
          <img src="/public/icons/pagination_right_orange.png" alt="">
          <p><struts:text name="pagination.next" /></p>
        </div>
      </struts:a>
    </struts:if>

      </div>
      <div class="bigpic_info">
        <h4><struts:property value="picture.getName(getText('language'))" /></h4>
        <p><struts:property value="picture.getDescription(getText('language'))" /></p>
      </div>

    </div>
    <hr>


    <script>
      $(function() {
    	  //Enable swiping...
    	  $("#touchbox").swipe( {
    		  swipeStatus:function(event, phase, direction, distance)
    		    {
    			  var sleft = '<struts:property value="%{previousUrl}" />';
    			  var sright = '<struts:property value="%{nextUrl}" />';
    			  if (phase=="end") {
    				  if (direction=="left") {
    					  if(sleft.length > 0) {
    						  window.location.href = sleft;
    					  }
    				  }
    				  if (direction=="right") {
    					  if(sright.length > 0) {
    					      window.location.href = sright;
    					  }
    				  }
    			  }
				},
				triggerOnTouchEnd:false,
				threshold:140
		  });
      });
			</script>

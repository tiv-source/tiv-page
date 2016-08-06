<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="othersUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="locationUrl"          action="index" namespace="/locations/location" />
<struts:url var="eventUrl"             action="index" namespace="/locations/event" />
<struts:url var="queueUrl"             action="queue" namespace="/locations/reservation" />



   	  <div class="nav">
   	    <div id="navi">
   	      <ul id="orientation">
   	        <li><struts:a href="%{othersUrl}"><struts:text name="navigation.category.others"/></struts:a></li>
   	        <li class="activlink1"><struts:text name="navigation.category.locations"/></li>
   	        <li><struts:a href="%{maintenanceUrl}"><struts:text name="navigation.category.maintenance"/></struts:a></li>
   	        <li><struts:a href="%{systemtUrl}"><struts:text name="navigation.category.system"/></struts:a></li>
   	      </ul>

          <div id="button_bar">

            <struts:a href="%{locationUrl}" title="%{getText('navigation.locations')}">
              <div class="button fl_stop">
                <img src="/admin/buttons/tiv_page_button_location.png" alt="<struts:text name="navigation.locations"/>">
                <p><struts:text name="navigation.locations"/></p>
              </div>
            </struts:a>

            <struts:a href="%{eventUrl}" title="%{getText('navigation.events')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.events"/>">
                <p><struts:text name="navigation.events"/></p>
              </div>
            </struts:a>
            
            <struts:a href="%{queueUrl}" title="%{getText('navigation.reservation')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_reservation.png" alt="<struts:text name="navigation.reservation"/>">
                <p><struts:text name="navigation.reservation"/></p>
              </div>
            </struts:a>

            <hr>

         </div>
       </div>
     </div>
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="othersUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="academyUrl"           action="index" namespace="/academy" />
<struts:url var="newsletterUrl"        action="index" namespace="/newsletter" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="lecturerUrl"          action="index" namespace="/academy/lecturer" />
<struts:url var="courseUrl"            action="index" namespace="/academy/course" />
<struts:url var="registrationUrl"      action="index" namespace="/academy/registration" />


   	  <div class="nav">
   	    <div id="navi">
   	      <ul id="orientation">
   	        <li><struts:a href="%{othersUrl}"><struts:text name="navigation.category.others"/></struts:a></li>
   	        <li><struts:a href="%{locationsUrl}"><struts:text name="navigation.category.locations"/></struts:a></li>
   	        <li class="activlink1"><struts:text name="navigation.category.academy"/></li>
   	        <li><struts:a href="%{newsletterUrl}"><struts:text name="navigation.category.newsletter"/></struts:a></li>
   	        <li><struts:a href="%{maintenanceUrl}"><struts:text name="navigation.category.maintenance"/></struts:a></li>
   	        <li><struts:a href="%{systemtUrl}"><struts:text name="navigation.category.system"/></struts:a></li>
   	      </ul>
   	
          <div id="button_bar">
                <struts:if test="getProperty('module.lecturer')">
              <struts:a href="%{lecturerUrl}" title="%{getText('navigation.lecturer')}">
                <div class="button fl_stop">
                  <img src="/admin/buttons/tiv_page_button_reservation.png" alt="<struts:text name="navigation.lecturer"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.lecturer"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.course')">
              <struts:a href="%{courseUrl}" title="%{getText('navigation.courses')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.courses"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.courses"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.course')">
              <struts:a href="%{registrationUrl}" title="%{getText('navigation.registrations')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.registrations"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.registrations"/></p>
                </div>
              </struts:a>
            </struts:if>

            
            <hr>

         </div>
       </div>
     </div>

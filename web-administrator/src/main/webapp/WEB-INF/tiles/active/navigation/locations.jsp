<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="othersUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="newsletterUrl"        action="index" namespace="/newsletter" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="locationUrl"          action="index" namespace="/locations/location" />
<struts:url var="vacancyUrl"           action="index" namespace="/locations/vacancy" />
<struts:url var="eventUrl"             action="index" namespace="/locations/event" />
<struts:url var="queueUrl"             action="queue" namespace="/locations/reservation" />
<struts:url var="feedbackOptionUrl"    action="index" namespace="/locations/feedbackoption" />
<struts:url var="reasonUrl"            action="index" namespace="/locations/reason" />
<struts:url var="requestUrl"           action="index" namespace="/locations/request" />



   	  <div class="nav">
   	    <div id="navi">
   	      <ul id="orientation">
   	        <li><struts:a href="%{othersUrl}"><struts:text name="navigation.category.others"/></struts:a></li>
   	        <li class="activlink1"><struts:text name="navigation.category.locations"/></li>
   	        <li><struts:a href="%{newsletterUrl}"><struts:text name="navigation.category.newsletter"/></struts:a></li>
   	        <li><struts:a href="%{maintenanceUrl}"><struts:text name="navigation.category.maintenance"/></struts:a></li>
   	        <li><struts:a href="%{systemtUrl}"><struts:text name="navigation.category.system"/></struts:a></li>
   	      </ul>

          <div id="button_bar">

            <struts:if test="getProperty('module.location')">
              <struts:a href="%{locationUrl}" title="%{getText('navigation.locations')}">
                <div class="button fl_stop">
                  <img src="/admin/buttons/tiv_page_button_location.png" alt="<struts:text name="navigation.locations"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.locations"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.vacancy')">
              <struts:a href="%{vacancyUrl}" title="%{getText('navigation.vacancies')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_vacancy.png" alt="<struts:text name="navigation.vacancies"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.vacancies"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.event')">
              <struts:a href="%{eventUrl}" title="%{getText('navigation.events')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.events"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.events"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.reservation')">
              <struts:a href="%{queueUrl}" title="%{getText('navigation.reservation')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_reservation.png" alt="<struts:text name="navigation.reservation"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.reservation"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.feedback')">
              <struts:a href="%{feedbackOptionUrl}" title="%{getText('navigation.feedbackOptions')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_reservation.png" alt="<struts:text name="navigation.feedbackOptions"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.feedbackOptions"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.request')">
              <struts:a href="%{reasonUrl}" title="%{getText('navigation.reason')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_reservation.png" alt="<struts:text name="navigation.reason"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.reason"/></p>
                </div>
              </struts:a>
            </struts:if>


            <struts:if test="getProperty('module.request')">
              <struts:a href="%{requestUrl}" title="%{getText('navigation.request')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_reservation.png" alt="<struts:text name="navigation.request"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.request"/></p>
                </div>
              </struts:a>
            </struts:if>


            <hr>

         </div>
       </div>
     </div>
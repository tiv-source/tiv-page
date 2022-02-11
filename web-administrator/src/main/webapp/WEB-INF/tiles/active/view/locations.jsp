<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="locationUrl"          action="index" namespace="/locations/location" />
<struts:url var="eventUrl"             action="index" namespace="/locations/event" />
<struts:url var="queueUrl"             action="queue" namespace="/locations/reservation" />
<struts:url var="feedbackOptionUrl"    action="index" namespace="/locations/feedbackoption" />
<struts:url var="reasonUrl"            action="index" namespace="/locations/reason" />
<struts:url var="requestUrl"           action="index" namespace="/locations/request" />

      <div id="title">
        <h5><struts:text name="navigation.category.locations"/></h5>
      </div>

      <struts:if test="getProperty('module.location')">
        <struts:a href="%{locationUrl}" title="%{getText('navigation.locations')}">
          <div class="buttoninfo typ1 fl_stop">
            <img src="/admin/buttons/tiv_page_button_location.png" alt="<struts:text name="navigation.locations.description"/>">
            <h5><struts:text name="navigation.locations"/></h5>
            <p><struts:text name="navigation.locations.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.event')">
        <struts:a href="%{eventUrl}" title="%{getText('navigation.events')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.events.description"/>">
            <h5><struts:text name="navigation.events"/></h5>
            <p><struts:text name="navigation.events.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.reservation')">
        <struts:a href="%{queueUrl}" title="%{getText('navigation.reservation')}">
          <div class="buttoninfo typ1">
            <img src="/admin/buttons/tiv_page_button_reservation.png" alt="<struts:text name="navigation.reservation.description"/>">
            <h5><struts:text name="navigation.reservation"/></h5>
            <p><struts:text name="navigation.reservation.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.feedback')">
        <struts:a href="%{feedbackOptionUrl}" title="%{getText('navigation.feedbackOptions')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.feedbackOptions.description"/>">
            <h5><struts:text name="navigation.feedbackOptions"/></h5>
            <p><struts:text name="navigation.feedbackOptions.description"/></p>
          </div>
        </struts:a>
      </struts:if>


      <struts:if test="getProperty('module.request')">
        <struts:a href="%{reasonUrl}" title="%{getText('navigation.reason')}">
          <div class="buttoninfo typ1">
            <img src="/admin/buttons/tiv_page_button_reservation.png" alt="<struts:text name="navigation.reason.description"/>">
            <h5><struts:text name="navigation.reason"/></h5>
            <p><struts:text name="navigation.reason.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.request')">
        <struts:a href="%{requestUrl}" title="%{getText('navigation.request')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.request.description"/>">
            <h5><struts:text name="navigation.request"/></h5>
            <p><struts:text name="navigation.request.description"/></p>
          </div>
        </struts:a>
      </struts:if>


      <hr>


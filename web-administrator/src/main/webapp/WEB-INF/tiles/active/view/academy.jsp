<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="lecturerUrl"             action="index" namespace="/academy/lecturer" />
<struts:url var="courseUrl"               action="index" namespace="/academy/course" />
<struts:url var="registrationUrl"         action="index" namespace="/academy/registration" />

      <div id="title">
        <h5><struts:text name="navigation.category.academy"/></h5>
      </div>

      <struts:if test="getProperty('module.lecturer')">
        <struts:a href="%{lecturerUrl}" title="%{getText('navigation.lecturer')}">
          <div class="buttoninfo typ1 fl_stop">
            <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.lecturer.description"/>">
            <h5><struts:text name="navigation.lecturer"/></h5>
            <p><struts:text name="navigation.lecturer.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.course')">
        <struts:a href="%{courseUrl}" title="%{getText('navigation.courses')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.courses.description"/>">
            <h5><struts:text name="navigation.courses"/></h5>
            <p><struts:text name="navigation.courses.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <struts:if test="getProperty('module.course')">
        <struts:a href="%{registrationUrl}" title="%{getText('navigation.registrations')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.registrations.description"/>">
            <h5><struts:text name="navigation.registrations"/></h5>
            <p><struts:text name="navigation.registrations.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <hr>

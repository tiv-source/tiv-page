<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="lecturerUrl"             action="index" namespace="/academy/lecturer" />
<struts:url var="mailaddresslockUrl"      action="index" namespace="/newsletter/mailaddresslock" />

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

      <struts:if test="getProperty('module.newsletter')">
        <struts:a href="%{mailaddresslockUrl}" title="%{getText('navigation.mailAddressLocks')}">
          <div class="buttoninfo typ2">
            <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.mailAddressLocks.description"/>">
            <h5><struts:text name="navigation.mailAddressLocks"/></h5>
            <p><struts:text name="navigation.mailAddressLocks.description"/></p>
          </div>
        </struts:a>
      </struts:if>

      <hr>

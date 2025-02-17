<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="othersUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="newsletterUrl"        action="index" namespace="/newsletter" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="lockrequestUrl"          action="index" namespace="/newsletter/lockrequest" />
<struts:url var="mailaddresslockUrl"      action="index" namespace="/newsletter/mailaddresslock" />


   	  <div class="nav">
   	    <div id="navi">
   	      <ul id="orientation">
   	        <li><struts:a href="%{othersUrl}"><struts:text name="navigation.category.others"/></struts:a></li>
   	        <li><struts:a href="%{locationsUrl}"><struts:text name="navigation.category.locations"/></struts:a></li>
   	        <li class="activlink1"><struts:text name="navigation.category.newsletter"/></li>
   	        <li><struts:a href="%{maintenanceUrl}"><struts:text name="navigation.category.maintenance"/></struts:a></li>
   	        <li><struts:a href="%{systemtUrl}"><struts:text name="navigation.category.system"/></struts:a></li>
   	      </ul>
   	
          <div id="button_bar">
            <struts:if test="getProperty('module.newsletter')">
              <struts:a href="%{lockrequestUrl}" title="%{getText('navigation.mailAddressLockRequests')}">
                <div class="button fl_stop">
                  <img src="/admin/buttons/tiv_page_button_location.png" alt="<struts:text name="navigation.mailAddressLockRequests"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.mailAddressLockRequests"/></p>
                </div>
              </struts:a>
            </struts:if>

            <struts:if test="getProperty('module.newsletter')">
              <struts:a href="%{mailaddresslockUrl}" title="%{getText('navigation.mailAddressLocks')}">
                <div class="button">
                  <img src="/admin/buttons/tiv_page_button_event.png" alt="<struts:text name="navigation.mailAddressLocks"/>">
                  <br />
                  <p class="ellipsis"><struts:text name="navigation.mailAddressLocks"/></p>
                </div>
              </struts:a>
            </struts:if>

            
            <hr>

         </div>
       </div>
     </div>

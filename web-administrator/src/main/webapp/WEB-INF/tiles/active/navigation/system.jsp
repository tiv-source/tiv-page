<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="othersUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="userUrl"              action="index" namespace="/system/user" />
<struts:url var="roleUrl"              action="index" namespace="/system/role" />
<struts:url var="propertyUrl"          action="index" namespace="/system/property" />

   	  <div class="nav">
   	    <div id="navi">
   	      <ul id="orientation">
   	        <li><struts:a href="%{othersUrl}"><struts:text name="navigation.category.others"/></struts:a></li>
   	        <li><struts:a href="%{locationsUrl}"><struts:text name="navigation.category.locations"/></struts:a></li>
   	        <li><struts:a href="%{maintenanceUrl}"><struts:text name="navigation.category.maintenance"/></struts:a></li>
   	        <li class="activlink1"><struts:text name="navigation.category.system"/></li>
   	      </ul>

          <div id="button_bar">

            <struts:a href="%{userUrl}" title="%{getText('navigation.users')}">
              <div class="button fl_stop">
                <img src="/admin/buttons/tiv_page_button_user.png" alt="<struts:text name="navigation.users"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.users"/></p>
              </div>
            </struts:a>

            <struts:a href="%{roleUrl}" title="%{getText('navigation.roles')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_role.png" alt="<struts:text name="navigation.roles"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.roles"/></p>
              </div>
            </struts:a>

            <struts:a href="%{propertyUrl}" title="%{getText('navigation.properties')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_properties.png" alt="<struts:text name="navigation.properties"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.properties"/></p>
              </div>
            </struts:a>

            <hr>

         </div>
       </div>
     </div>
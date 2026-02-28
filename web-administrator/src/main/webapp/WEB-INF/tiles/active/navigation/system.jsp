<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="othersUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="academyUrl"           action="index" namespace="/academy" />
<struts:url var="newsletterUrl"        action="index" namespace="/newsletter" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="userUrl"              action="index" namespace="/system/user" />
<struts:url var="roleUrl"              action="index" namespace="/system/role" />
<struts:url var="propertyUrl"          action="index" namespace="/system/property" />
<struts:url var="salutationUrl"        action="index" namespace="/system/salutation" />
<struts:url var="countryUrl"           action="index" namespace="/system/country" />
<struts:url var="provinceUrl"          action="index" namespace="/system/province" />
<struts:url var="captchaUrl"           action="index" namespace="/system/captcha" />

   	  <div class="nav">
   	    <div id="navi">
   	      <ul id="orientation">
   	        <li><struts:a href="%{othersUrl}"><struts:text name="navigation.category.others"/></struts:a></li>
   	        <li><struts:a href="%{locationsUrl}"><struts:text name="navigation.category.locations"/></struts:a></li>
   	        <li><struts:a href="%{academyUrl}"><struts:text name="navigation.category.academy"/></struts:a></li>
   	        <li><struts:a href="%{newsletterUrl}"><struts:text name="navigation.category.newsletter"/></struts:a></li>
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

            <struts:a href="%{salutationUrl}" title="%{getText('navigation.salutations')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_properties.png" alt="<struts:text name="navigation.salutations"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.salutations"/></p>
              </div>
            </struts:a>

            <struts:a href="%{countryUrl}" title="%{getText('navigation.countries')}">
              <div class="button">
                <img src="/admin/buttons/tiv_button_country.png" alt="<struts:text name="navigation.countries"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.countries"/></p>
              </div>
            </struts:a>

            <struts:a href="%{provinceUrl}" title="%{getText('navigation.provinces')}">
              <div class="button">
                <img src="/admin/buttons/tiv_button_province.png" alt="<struts:text name="navigation.provinces"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.provinces"/></p>
              </div>
            </struts:a>

            <struts:a href="%{captchaUrl}" title="%{getText('navigation.captchas')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_properties.png" alt="<struts:text name="navigation.captchas"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.captchas"/></p>
              </div>
            </struts:a>

            <hr>

         </div>
       </div>
     </div>
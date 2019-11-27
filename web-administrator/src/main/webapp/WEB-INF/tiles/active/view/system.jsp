<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="userUrl"              action="index" namespace="/system/user" />
<struts:url var="roleUrl"              action="index" namespace="/system/role" />
<struts:url var="propertyUrl"          action="index" namespace="/system/property" />
<struts:url var="captchaUrl"           action="index" namespace="/system/captcha" />

      <div id="title">
        <h5><struts:text name="navigation.category.system"/></h5>
      </div>

      <struts:a href="%{userUrl}">
        <div class="buttoninfo typ1 fl_stop">
          <img src="/admin/buttons/tiv_page_button_user.png" alt="<struts:text name="navigation.users.description"/>">
          <h5><struts:text name="navigation.users"/></h5>
          <p><struts:text name="navigation.users.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{roleUrl}">
        <div class="buttoninfo typ2">
          <img src="/admin/buttons/tiv_page_button_role.png" alt="<struts:text name="navigation.roles.description"/>">
          <h5><struts:text name="navigation.roles"/></h5>
          <p><struts:text name="navigation.roles.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{propertyUrl}">
        <div class="buttoninfo typ1">
          <img src="/admin/buttons/tiv_page_button_properties.png" alt="<struts:text name="navigation.properties.description"/>">
          <h5><struts:text name="navigation.properties"/></h5>
          <p><struts:text name="navigation.properties.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{captchaUrl}">
        <div class="buttoninfo typ2">
          <img src="/admin/buttons/tiv_page_button_properties.png" alt="<struts:text name="navigation.captchas.description"/>">
          <h5><struts:text name="navigation.captchas"/></h5>
          <p><struts:text name="navigation.captchas.description"/></p>
        </div>
      </struts:a>

      <hr>






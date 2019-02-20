<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="contentEntryUrl"      action="index" namespace="/others/contententry" />
<struts:url var="linkEntryUrl"      action="index" namespace="/others/linkentry" />

      <div id="title">
        <h5><struts:text name="navigation.category.menuentries"/></h5>
      </div>
      
      <struts:a href="%{linkEntryUrl}" title="%{getText('navigation.linkEntries')}">
        <div class="buttoninfo typ1 fl_stop">
          <img src="/admin/buttons/tiv_page_button_page.png" alt="<struts:text name="navigation.linkEntries.description"/>">
          <h5><struts:text name="navigation.linkEntries"/></h5>
          <p><struts:text name="navigation.linkEntries.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{contentEntryUrl}" title="%{getText('navigation.contentEntries')}">
        <div class="buttoninfo typ2">
          <img src="/admin/buttons/tiv_page_button_message.png" alt="<struts:text name="navigation.contentEntries.description"/>">
          <h5><struts:text name="navigation.contentEntries"/></h5>
          <p><struts:text name="navigation.contentEntries.description"/></p>
        </div>
      </struts:a>




      <hr>


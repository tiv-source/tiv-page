<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="backupUrl"         action="index" namespace="/maintenance/backup" />
<struts:url var="restoreUrl"        action="index" namespace="/maintenance/restore" />
<struts:url var="filesUrl"          action="index" namespace="/maintenance/files" />
<struts:url var="cssFileUrl"        action="index" namespace="/maintenance/cssfile" />
<struts:url var="cssGroupUrl"       action="index" namespace="/maintenance/cssgroup" />

      <div id="title">
        <h5><struts:text name="navigation.category.maintenance"/></h5>
      </div>
      
      <struts:a href="%{backupUrl}">
        <div class="buttoninfo typ1 fl_stop">
          <img src="/admin/buttons/tiv_page_button_backup.png" alt="<struts:text name="navigation.backup.description"/>">
          <h5><struts:text name="navigation.backup"/></h5>
          <p><struts:text name="navigation.backup.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{restoreUrl}">
        <div class="buttoninfo typ2">
          <img src="/admin/buttons/tiv_page_button_restore.png" alt="<struts:text name="navigation.restore.description"/>">
          <h5><struts:text name="navigation.restore"/></h5>
          <p><struts:text name="navigation.restore.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{filesUrl}">
        <div class="buttoninfo typ1">
          <img src="/admin/buttons/tiv_page_button_file.png" alt="<struts:text name="navigation.files.description"/>">
          <h5><struts:text name="navigation.files"/></h5>
          <p><struts:text name="navigation.files.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{cssFileUrl}">
        <div class="buttoninfo typ2">
          <img src="/admin/buttons/tiv_page_button_cssfile.png" alt="<struts:text name="navigation.cssfiles"/>">
          <h5><struts:text name="navigation.cssfiles"/></h5>
          <p><struts:text name="navigation.cssfiles.description"/></p>
        </div>
      </struts:a>

      <struts:a href="%{cssGroupUrl}">
        <div class="buttoninfo typ1">
          <img src="/admin/buttons/tiv_page_button_cssgroups.png" alt="<struts:text name="navigation.cssgroups"/>">
          <h5><struts:text name="navigation.cssgroups"/></h5>
          <p><struts:text name="navigation.cssgroups.description"/></p>
        </div>
      </struts:a>

      <hr>





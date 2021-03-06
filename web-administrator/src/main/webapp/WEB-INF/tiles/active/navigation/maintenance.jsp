<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>

<struts:url var="othersUrl"            action="index" namespace="/others" />
<struts:url var="locationsUrl"         action="index" namespace="/locations" />
<struts:url var="maintenanceUrl"       action="index" namespace="/maintenance" />
<struts:url var="systemtUrl"           action="index" namespace="/system" />

<struts:url var="backupUrl"            action="index" namespace="/maintenance/backup" />
<struts:url var="restoreUrl"           action="index" namespace="/maintenance/restore" />
<struts:url var="filesUrl"             action="index" namespace="/maintenance/files" />
<struts:url var="cssFileUrl"           action="index" namespace="/maintenance/cssfile" />
<struts:url var="cssGroupUrl"          action="index" namespace="/maintenance/cssgroup" />


   	  <div class="nav">
   	    <div id="navi">
   	      <ul id="orientation">
   	        <li><struts:a href="%{othersUrl}"><struts:text name="navigation.category.others"/></struts:a></li>
   	        <li><struts:a href="%{locationsUrl}"><struts:text name="navigation.category.locations"/></struts:a></li>
   	        <li class="activlink1"><struts:text name="navigation.category.maintenance"/></li>
   	        <li><struts:a href="%{systemtUrl}"><struts:text name="navigation.category.system"/></struts:a></li>
   	      </ul>

          <div id="button_bar">

            <struts:a href="%{backupUrl}" title="%{getText('navigation.backup')}">
              <div class="button fl_stop">
                <img src="/admin/buttons/tiv_page_button_backup.png" alt="<struts:text name="navigation.backup"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.backup"/></p>
              </div>
            </struts:a>

            <struts:a href="%{restoreUrl}" title="%{getText('navigation.restore')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_restore.png" alt="<struts:text name="navigation.restore"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.restore"/></p>
              </div>
            </struts:a>
            
            <struts:a href="%{filesUrl}" title="%{getText('navigation.files')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_file.png" alt="<struts:text name="navigation.files"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.files"/></p>
              </div>
            </struts:a>

            <struts:a href="%{cssFileUrl}" title="%{getText('navigation.css')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_cssfile.png" alt="<struts:text name="navigation.cssfiles"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.cssfiles"/></p>
              </div>
            </struts:a>

            <struts:a href="%{cssGroupUrl}" title="%{getText('navigation.cssgroups')}">
              <div class="button">
                <img src="/admin/buttons/tiv_page_button_cssgroups.png" alt="<struts:text name="navigation.cssgroups"/>">
                <br />
                <p class="ellipsis"><struts:text name="navigation.cssgroups"/></p>
              </div>
            </struts:a>

            <hr>

         </div>
       </div>
     </div>
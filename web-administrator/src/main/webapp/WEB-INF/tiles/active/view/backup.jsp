<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="databaseUrl"     action="database" namespace="/maintenance/backup" />
<struts:url var="filesUrl"        action="files"    namespace="/maintenance/backup" />
<struts:url var="picturesUrl"     action="pictures" namespace="/maintenance/backup" />
<struts:url var="cssUrl"          action="css"      namespace="/maintenance/backup" />    

      <div id="title">
        <h5>Backup</h5>
      </div>

      <struts:a href="%{databaseUrl}">
        <div class="buttoninfo typ1" style="width: 32.5%;">
          <img src="/admin/buttons/tiv_page_button_backup.png" alt="<struts:text name="navigation.backup.description"/>">
          <h5>Datenbank Backup</h5>
          <p>Hier klicken um ein Backup von der Datenbank zu erstellen</p>
        </div>
      </struts:a>

      <struts:a href="%{filesUrl}">
        <div class="buttoninfo typ1" style="margin-left: 14px; width: 32.5%;">
          <img src="/admin/buttons/tiv_page_button_backup.png" alt="<struts:text name="navigation.backup.description"/>">
          <h5>Datei Backup</h5>
          <p>Hier klicken um ein Backup von der Datenbank zu erstellen</p>
        </div>
      </struts:a>

      <struts:a href="%{picturesUrl}">
        <div class="buttoninfo typ1" style="margin-left: 14px; width: 32.5%;">
          <img src="/admin/buttons/tiv_page_button_backup.png" alt="<struts:text name="navigation.backup.description"/>">
          <h5>Bild Backup</h5>
          <p>Hier klicken um ein Backup von der Datenbank zu erstellen</p>
        </div>
      </struts:a>

      <struts:a href="%{cssUrl}">
        <div class="buttoninfo typ1" style="width: 32.5%;">
          <img src="/admin/buttons/tiv_page_button_backup.png" alt="<struts:text name="navigation.backup.description"/>">
          <h5>CSS Backup</h5>
          <p>Hier klicken um ein Backup von den CSS-Dateien zu erstellen</p>
        </div>
      </struts:a>

      <hr>





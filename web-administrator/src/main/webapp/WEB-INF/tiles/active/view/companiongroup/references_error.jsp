<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="companionGroup.delete"/></h5>
        </div>

        <div id="backend_update_form" class="update" style="text-align=center;">
          Fehler beim löschen. Es existiert noch Einträge in dieser Gruppe.
        </div>


      </div>
      <!--  Ende MAIN -->
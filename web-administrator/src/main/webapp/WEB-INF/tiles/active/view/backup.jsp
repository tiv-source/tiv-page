<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url id="zipUrl" action="backup" namespace="/backup" />
    
    <div class="main">

      <div class="sub_menu">
        <struts:a href="%{zipUrl}">hier klicken</struts:a>
      </div>

      <div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" id="gbox_gridedittable" dir="ltr" style="width: 1300px;">
        
        <div class="ui-jqgrid-view" id="gview_gridedittable" style="width: 1300px;">
    
          <div class="ui-jqgrid-titlebar ui-corner-top ui-helper-clearfix">
            <span class="ui-jqgrid-title">Backup</span>
          </div>
        </div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>

      </div>
    </div>
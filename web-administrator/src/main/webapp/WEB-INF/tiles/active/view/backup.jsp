<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
    
    <div class="main">

      <div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" id="gbox_gridedittable" dir="ltr" style="width: 1600px;">
        
        <div class="ui-jqgrid-view" id="gview_gridedittable" style="width: 1600px;">
    
          <div class="ui-jqgrid-titlebar ui-corner-top ui-helper-clearfix">
            <span class="ui-jqgrid-title">Backup</span>
          </div>
        </div>

        <div style="width:100%; margin: 10px;">
        <table>
          <tr>
            <td>Datenbank Backup</td>
            <td>
                <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  cssStyle="width: 100px; color: #ffffff !important; text-align: center;"
                  action="database" 
                  namespace="/backup">hier klicken</struts:a>
            </td>
          </tr>
          <tr>
            <td>Datei Backup</td>
            <td>
                <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button"
                  cssStyle="width: 100px; color: #ffffff !important; text-align: center;" 
                  action="files" 
                  namespace="/backup">hier klicken</struts:a>
            </td>
          </tr>
        </table>
        </div>

      </div>
    </div>
    
     
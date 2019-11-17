<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="cssFileBackupUrl" action="backup"  namespace="/maintenance/cssfile" />
<struts:url var="cssFileAddUrl"    action="addForm" namespace="/maintenance/cssfile" />
<struts:url var="remoteurl" action="table" namespace="/maintenance/cssfile"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/maintenance/cssfile/editForm.html?cssFile="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/maintenance/cssfile/deleteForm.html?cssFile="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>";
}
</script>

<!-- Importiere Java Scripte für die Tabelle -->
<script src="/admin/js-tiv/formatTrueFalse.js"></script>
<script src="/admin/js-tiv/formatIsoDate.js"></script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{cssFileBackupUrl}">
            <struts:text name="cssFile.backup"/>
          </struts:a>
        </div>
        <div class="sub_menu">
          <struts:a href="%{cssFileAddUrl}">
            <struts:text name="cssFile.add"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/maintenance/cssfile/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            '<struts:text name="cssFile.name"/>',
            '<struts:text name="cssFile.description"/>',
            '<struts:text name="cssFile.priority"/>',
            '<struts:text name="cssFile.version"/>',
            '<struts:text name="created"/>',
            '<struts:text name="modified"/>',
            '<struts:text name="modifiedBy"/>',
            ""
        ],
        colModel: [
            { name: "name",            width:  140, align: "left" },
            { name: "description",     width:  210, align: "left" },
            { name: "priority",        width:   70, align: "right" },
            { name: "version",         width:   70, align: "right" },
            { name: "created",         width:  105, align: "center", formatter:formatIsoDate },
            { name: "modified",        width:  105, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",      width:   70, align: "right" },
            { name: "uuid",            width:   70, align: "center", sortable: false, formatter:formatLinks }
        ],
        pager: "#entityPager",
        rowNum: 15,
        rowList: [5, 10, 15, 20, 25, 50, 100, 150, 200],
        sortname: "created",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        caption: '<struts:text name="cssFiles"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->



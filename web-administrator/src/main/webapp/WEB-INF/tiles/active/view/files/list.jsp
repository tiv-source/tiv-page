<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="pageAddUrl" action="addForm" namespace="/page" />
<struts:url var="remoteurl" action="table" namespace="/files"/>

<script type="text/javascript">
function formatViewLink(cellvalue, options, rowObject) {
  return "<img src='/uploads/"+ cellvalue +"' style='max-width:200px; max-height:100px;' />";
}
</script>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/files/deleteForm.html?file="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/delete.png'/>" + 
         "</a>";
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
        </div>



<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/maintenance/files/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Bild", 
            "Name", 
            ""
        ],
        colModel: [
            { name: "name",            width:  140, align: "center", formatter:formatViewLink },
            { name: "name",            width:  140, align: "center" },
            { name: "name",            width:  130, align: "center", sortable: false, formatter:formatEditLink }
        ],
        pager: "#entityPager",
        rowNum: 10,
        rowList: [5, 10, 15, 20, 25, 50, 100, 150, 200],
        sortname: "technical",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        caption: "Dateien"
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
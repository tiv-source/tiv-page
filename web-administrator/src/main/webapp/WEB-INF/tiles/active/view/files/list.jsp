<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="filesAddUrl" action="addForm" namespace="/maintenance/files" />
<struts:url var="remoteurl" action="table" namespace="/maintenance/files"/>

<script type="text/javascript">
function formatViewLink(cellvalue, options, rowObject) {
	if(cellvalue.endsWith('.png') | cellvalue.endsWith('.jpg') | cellvalue.endsWith('.jpeg') | cellvalue.endsWith('.JPG') | cellvalue.endsWith('.JPEG') | cellvalue.endsWith('.ico')) {
		return "<img src='/uploads/"+ cellvalue +"' style='max-width:200px; max-height:100px; width: unset; height: unset;' />";
	} else if (cellvalue.endsWith('.mp4')) {
		return "<img src='/admin/icons/128x128/mp4.png' style='width:100px; height:100px; width: unset; height: unset;' />";
	} else if (cellvalue.endsWith('.doc')) {
		return "<img src='/admin/icons/128x128/doc.png' style='width:100px; height:100px; width: unset; height: unset;' />";
	} else {
		return "<img src='/admin/icons/128x128/pdf.png' style='width:100px; height:100px; width: unset; height: unset;' />";
	}
}
</script>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/maintenance/files/deleteForm.html?file="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>";
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{filesAddUrl}">
            <struts:text name="file.add"/>
          </struts:a>
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
        sortname: "name",
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
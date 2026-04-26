<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="propertyAddUrl" action="addForm" namespace="/system/property" />
<struts:url var="remoteurl" action="table" namespace="/system/property" />

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
	var result = rowObject.unchangeable;
	if (result) {
		return "<img src='/admin/icons/ionicons/lock-closed-outline.svg' style='width:16px;'/>";
	} else {
		return "<a href='/admin/system/property/editForm.html?uncheckProperty="+ cellvalue + "' style='border-style: none; display: inline;'>" +
		    "<img src='/admin/icons/ionicons/pencil-outline.svg' style='width:16px;'/>" + 
            "</a>&nbsp;&nbsp;&nbsp;" +
            "<a href='/admin/system/property/protectForm.html?uncheckProperty="+ cellvalue +"' style='border-style: none; display: inline;'>" +
            "<img src='/admin/icons/ionicons/lock-open-outline.svg' style='width:16px;'/>" +
            "</a>&nbsp;&nbsp;&nbsp;" +
            "<a href='/admin/system/property/deleteForm.html?uncheckProperty="+ cellvalue +"' style='border-style: none; display: inline;'>" +
            "<img src='/admin/icons/ionicons/trash-outline.svg' style='width:16px;'/>" +
            "</a>";
	}
}
</script>

<script type="text/javascript">
function formatIsoDate(celldate, options, rowObject) {
    var dateOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
    var timeOptions = { hour: '2-digit', minute: '2-digit' };
    var newDate = new Date();
    newDate.setTime(Date.parse(celldate));
    return newDate.toLocaleTimeString('de-DE', timeOptions) + " - " + newDate.toLocaleDateString('de-DE', dateOptions);
}
</script>

<script type="text/javascript">
function formatTrueFalse(cellvalue, options, rowObject) {
  if (cellvalue) {
    return "<img src='/admin/icons/16x16/accept.png' style='width:14px;'/>";  
  } else {
    return "<img src='/admin/icons/16x16/oneway.png' style='width:14px;'/>";
  }
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{propertyAddUrl}">
            <struts:text name="property.add"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/system/property/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Schlüssel",
            "Wert",
            "Geschützt",
            "Bearbeitet am",
            "Bearbeitet von",
            "Bearbeitungsadresse",
            ""
        ],
        colModel: [
            { name: "key",             width:  280, search: true },
            { name: "value",           width:  280, align: "center", search: false },
            { name: "unchangeable",    width:   70, align: "center", formatter:formatTrueFalse, stype: 'select', searchoptions: { sopt:['eq','ne'], value:':Alle;true:Ja;false:Nein'} },
            { name: "modified",        width:  105, align: "center", search: false, formatter:formatIsoDate },
            { name: "modifiedBy",      width:  105, align: "right",  search: false },
            { name: "modifiedAddress", width:  105, align: "right",  search: false },
            { name: "key",             width:  105, align: "center", search: false, sortable: false, formatter:formatLinks }
        ],
        pager: "#entityPager",
        rowNum: 20,
        rowList: [5, 10, 15, 20, 25, 50, 100, 150, 200],
        sortname: "key",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height: 'auto',
        hidegrid: false,
        caption: "Eigenschaften"
    });
    $("#entityList").jqGrid('filterToolbar', {stringResult: true, searchOnEnter: false, defaultSearch : "cn"});
});
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
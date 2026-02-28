<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="struts" uri="/struts-tags"%>


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
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/system/province/editForm.html?uncheckedProvince="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/system/province/deleteForm.html?uncheckedProvince="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>";
}
</script>

<struts:hidden id="rowNum"  name="rowNum"  value="%{getProperty('admin.object.list.province.default.numberoflines')}" theme="simple"/>
<struts:hidden id="rowList" name="rowList" value="%{getProperty('admin.object.list.province.numberoflines')}" theme="simple"/>

<script type="text/javascript">
$(function () {
    $("#provinceList").jqGrid({
        url: "/admin/system/province/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Name",
            "Code",
            "Erstellt am", 
            "Bearbeitet am", 
            "Bearbeitet von",
            "Bearbeitungsadresse",
            ""
        ],
        colModel: [
            { name: "descriptionMap.de.name", width:   90, index: "name" },
            { name: "code",                   width:   55, align: "center" },
            { name: "created",                width:   70, align: "center", formatter:formatIsoDate },
            { name: "modified",               width:   70, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",             width:   70, align: "right" },
            { name: "modifiedAddress",        width:   90, align: "right" },
            { name: "uuid",                   width:   70, align: "center", sortable: false, formatter:formatLinks }
        ],
        pager: "#provincePager",
        rowNum: $('#rowNum').val(),
        rowList: JSON.parse($("#rowList").val())),
        sortname: "language",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        caption: "Länder"
    }); 
}); 
</script>



<struts:url var="provinceAddUrl" action="addForm" namespace="/system/province" />

      <!--  Start MAIN -->
      <div class="content">
        <div class="sub_menu">
          <struts:a href="%{provinceAddUrl}">
            <struts:text name="province.add"/>
          </struts:a>
        </div>
        <table id="provinceList"><tr><td></td></tr></table>
        <div id="provincePager"></div>
      </div>
      <!--  Ende MAIN -->
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
  return "<a href='/admin/system/country/editForm.html?uncheckCountry="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/system/country/deleteForm.html?uncheckCountry="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>";
}
</script>

<struts:hidden id="rowNum"  name="rowNum"  value="%{getProperty('admin.object.list.country.default.numberoflines')}" theme="simple"/>
<struts:hidden id="rowList" name="rowList" value="%{getProperty('admin.object.list.country.numberoflines')}" theme="simple"/>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/system/country/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Name",
            "ISO 639-1",
            "Alpha 2",
            "Alpha 3",
            "Numerisch",
            "Vorwahl",
            "Erstellt am", 
            "Bearbeitet am", 
            "Bearbeitet von",
            "Bearbeitungsadresse",
            ""
        ],
        colModel: [
            { name: "descriptionMap.de.name", width:   50, index: "name" },
            { name: "language",               width:   30, align: "center" },
            { name: "alpha2",                 width:   30, align: "center" },
            { name: "alpha3",                 width:   30, align: "center" },
            { name: "numeric",                width:   40, align: "center" },
            { name: "areaCode",               width:   30, align: "center" },
            { name: "created",                width:   50, align: "center", formatter:formatIsoDate },
            { name: "modified",               width:   50, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",             width:   50, align: "right" },
            { name: "modifiedAddress",        width:   50, align: "right" },
            { name: "uuid",                   width:   30, align: "center", sortable: false, formatter:formatLinks }
        ],
        pager: "#entityPager",
        rowNum: $('#rowNum').val(),
        rowList: JSON.parse($("#rowList").val()),
        sortname: "language",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        hidegrid: false,
        caption: "Länder"
    }); 
}); 
</script>



<struts:url var="countryAddUrl"     action="addForm" namespace="/system/country" />
<struts:url var="countryBackupUrl"  action="backup"  namespace="/system/country" />

      <!--  Start MAIN -->
      <div class="content">
        <div class="sub_menu">
          <struts:a href="%{countryBackupUrl}">
            <struts:text name="country.backup"/>
          </struts:a>
        </div>
        <div class="sub_menu">
          <struts:a href="%{countryAddUrl}">
            <struts:text name="country.add"/>
          </struts:a>
        </div>
        <table id="entityList"><tr><td></td></tr></table>
        <div id="entityPager"></div>
      </div>
      <!--  Ende MAIN -->


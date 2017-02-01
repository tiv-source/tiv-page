<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="eventAddUrl" action="addForm" namespace="/locations/event" />
<struts:url var="remoteurl" action="table" namespace="/locations/event"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/locations/reservation/index.html?event="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/view.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/locations/event/editForm.html?event="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/locations/event/copyForm.html?event="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/copy.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/locations/event/deleteForm.html?event="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>";
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

<script type="text/javascript">
function formatIsoDate(celldate, options, rowObject) {
  var newDate = new Date();
  newDate.setTime(Date.parse(celldate));
  return newDate.toLocaleFormat('%H:%M - %d.%m.%Y');
}
</script>

<script type="text/javascript">
function formatCurrency(cellvalue, options, rowObject) {
  var outputCurrency = parseFloat(cellvalue).toFixed(2);
  return outputCurrency + " €";  
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{eventAddUrl}">
            <struts:text name="event.add"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/locations/event/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Name", 
            "Filiale",
            "Preis", 
            "Anfang", 
            "Ende", 
            "Reservierungsschl.", 
            "Reservierbar", 
            "Sichtbar", 
            "Bearbeitet am", 
            "Bearbeitet von",
            "Bearbeitungsadresse",
            ""
        ],
        colModel: [
            { name: "descriptionMap.DE.name",          width:  140 },
            { name: "location.descriptionMap.DE.name", width:  210 },
            { name: "price",                           width:   90, align:  "right", formatter:formatCurrency },
            { name: "beginning",                       width:  140, align: "center", formatter:formatIsoDate },
            { name: "ending",                          width:  140, align: "center", formatter:formatIsoDate },
            { name: "deadline",                        width:  140, align: "center", formatter:formatIsoDate },
            { name: "reservation",                     width:  130, align: "center", formatter:formatTrueFalse },
            { name: "visible",                         width:  120, align: "center", formatter:formatTrueFalse },
            { name: "modified",                        width:  140, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",                      width:  140, align:  "right" },
            { name: "modifiedAddress",                 width:  180, align:  "right" },
            { name: "uuid",                            width:  130, align: "center", sortable: false, formatter:formatEditLink }
        ],
        pager: "#entityPager",
        rowNum: 15,
        rowList: [5, 10, 15, 20, 25, 50, 100, 150, 200],
        sortname: "beginning",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        caption: '<struts:text name="events"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="getListUrl" action="list" namespace="/locations/reservation">
  <struts:param name="event" value="event.uuid"/>
</struts:url>

<struts:url var="addReservationUrl" action="addForm" namespace="/locations/reservation">
  <struts:param name="event" value="event.uuid"/>
</struts:url>

<struts:url var="remoteurl"   action="table"   namespace="/locations/reservation">
  <struts:param name="event" value="event.uuid"/>
</struts:url>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/locations/reservation/view.html?reservation="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/view.png' style='width:16px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" +
         "<a href='/admin/locations/reservation/editForm.html?reservation="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:16px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/locations/reservation/deleteForm.html?reservation="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:16px;'/>" + 
         "</a>";
}
</script>

<script type="text/javascript">
function formatGender(cellvalue, options, rowObject) {
  if (cellvalue) {
    return "Frau";  
  } else {
    return "Herr";
  }
}
</script>

<script type="text/javascript">
function formatIsoDate(celldate, options, rowObject) {
  var newDate = new Date();
  newDate.setTime(Date.parse(celldate));
  return newDate.toLocaleFormat('%H:%M:%S - %d.%m.%Y');
}
</script>

<script type="text/javascript">
function formatIsoTime(celldate, options, rowObject) {
  var newDate = new Date();
  newDate.setTime(Date.parse(celldate));
  return newDate.toLocaleFormat('%H:%M');
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{addReservationUrl}">
            <struts:text name="reservation.add"/>
          </struts:a>
          <struts:a href="%{getListUrl}">
            <struts:text name="reservation.list"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: '<struts:property value="remoteurl"/>',
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Anrede", 
            "Vorname",
            "Nachname", 
            "Telefon", 
            "EMail", 
            "Personen", 
            "Uhrzeit", 
            "Erstellt von", 
            "Erstellt am",
            "Bestätigt am",
            "Bestätigt von",
            ""
        ],
        colModel: [
            { name: "gender",                 width:  140, align: "left", formatter:formatGender },
            { name: "firstname",              width:  140 },
            { name: "lastname",               width:  140 },
            { name: "telephone",              width:  140 },
            { name: "email",                  width:  140 },
            { name: "quantity",               width:  140 },
            { name: "time",                   width:   70, align: "center", formatter:formatIsoTime },
            { name: "createdAddress",         width:  100, align: "center" },
            { name: "created",                width:  110, align: "center", formatter:formatIsoDate },
            { name: "confirmedDate",          width:  110, align: "center", formatter:formatIsoDate },
            { name: "confirmedBy",            width:  120, align: "center" },
            { name: "uuid",                   width:   70, align: "center", sortable: false, formatter:formatEditLink }
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
        caption: 'Reservierungen für das <struts:property value="event.descriptionMap.DE.name"/> am <struts:text name="format.reservation.date"><struts:param value="event.beginning"/></struts:text> in der Filiale <struts:property value="event.location.descriptionMap.DE.name"/>'
    }); 
}); 
</script>

        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
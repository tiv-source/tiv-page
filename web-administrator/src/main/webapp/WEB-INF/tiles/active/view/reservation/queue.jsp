<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="remoteurl"   action="queueTable"   namespace="/reservation" />


<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/locations/reservation/confirmForm.html?uncheckedReservation="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/view.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" +
         "<a href='/admin/locations/reservation/editForm.html?uncheckedReservation="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/locations/reservation/deleteForm.html?uncheckedReservation="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
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
    var dateOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
    var newDate = new Date();
    newDate.setTime(Date.parse(celldate));
    return newDate.toLocaleDateString('de-DE', dateOptions);
}
</script>

<script type="text/javascript">
function formatIsoTime(celldate, options, rowObject) {
    var timeOptions = { hour: '2-digit', minute: '2-digit' };
    var newDate = new Date();
    newDate.setTime(Date.parse(celldate));
    return newDate.toLocaleTimeString('de-DE', timeOptions);
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">

        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/locations/reservation/queueTable.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Filiale",
            "Veranstaltung",
            "Datum",
            "Anrede",
            "Vorname",
            "Nachname", 
            "Telefon", 
            "EMail", 
            "Personen", 
            "Uhrzeit", 
            ""
        ],
        colModel: [
            { name: "event.location.descriptionMap.DE.name", width:  140, align: "left" },
            { name: "event.descriptionMap.DE.name",          width:  140, align: "left" },
            { name: "event.beginning",                       width:  140, align: "left", formatter:formatIsoDate },
            { name: "gender",                                width:  140, align: "left", formatter:formatGender },
            { name: "firstname",              width:  140 },
            { name: "lastname",               width:  140 },
            { name: "telephone",              width:  140 },
            { name: "email",                  width:  140 },
            { name: "quantity",               width:  140 },
            { name: "time",                   width:  140, align: "center", formatter:formatIsoTime },
            { name: "uuid",                   width:  130, align: "center", sortable: false, formatter:formatEditLink }
        ],
        pager: "#entityPager",
        rowNum: 10,
        rowList: [5, 10, 15, 20, 25, 50, 100, 150, 200],
        sortname: "location",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        caption: "Unbestätigte Reservierungen"
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>


        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
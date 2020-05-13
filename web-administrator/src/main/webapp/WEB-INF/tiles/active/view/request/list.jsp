<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="reasonAddUrl" action="addForm" namespace="/locations/request" />
<struts:url var="remoteurl" action="table" namespace="/locations/request"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/locations/request/editForm.html?request="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/locations/request/"+ cellvalue +"/original.html' target='_blank' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/locations/request/deleteForm.html?request="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
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
function formatGender(cellvalue, options, rowObject) {
  if (cellvalue == 'M') {
    return "Herr";
  } else if (cellvalue == 'F') {
	  return "Frau";  
  } else {
    return "Divers";
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

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{reasonAddUrl}">
            <struts:text name="request.add"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/locations/request/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Anrede",
            "Vorname",
            "Nachname",
            "Grund",
            "Bestätigt",
            "Erstellt am",
            "Erstellungsadresse",
            ""
        ],
        colModel: [
            { name: "gender",                        width:   70, formatter:formatGender },
            { name: "firstname",                     width:  140 },
            { name: "lastname",                      width:  140 },
            { name: "reason.descriptionMap.DE.name", width:  140, index: "reason" },
            { name: "confirmed",                     width:   70, align: "center", formatter:formatTrueFalse },
            { name: "created",                       width:  140, align: "center", formatter:formatIsoDate },
            { name: "createdAddress",                width:  140, align: "right" },
            { name: "uuid",                          width:   70, align: "center", sortable: false, formatter:formatLinks }
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
        caption: "Anträge"
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
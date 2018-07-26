<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="appointmentAddUrl" action="addForm" namespace="/others/appointment" />
<struts:url var="remoteurl" action="table" namespace="/others/appointment"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/others/appointment/editForm.html?appointment="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/others/appointment/deleteForm.html?appointment="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
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
    var dateOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
    var timeOptions = { hour: '2-digit', minute: '2-digit' };
    var newDate = new Date();
    newDate.setTime(Date.parse(celldate));
    return newDate.toLocaleTimeString('de-DE', timeOptions) + " - " + newDate.toLocaleDateString('de-DE', dateOptions);
}
</script>

<script type="text/javascript">
function formatPicture(cellvalue, options, rowObject) {
	return "<img src='/pictures/THUMBNAIL/" + cellvalue + "'/>";  
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{appointmentAddUrl}">
            <struts:text name="appointment.add"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/others/appointment/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            '<struts:text name="picture"/>',
            '<struts:text name="descriptionMap.DE.name"/>',
            '<struts:text name="appointment.pictureOnPage.table"/>',
            '<struts:text name="visible"/>',
            '<struts:text name="pointInTime"/>',
            '<struts:text name="modified"/>',
            '<struts:text name="modifiedBy"/>',
            '<struts:text name="modifiedAddress"/>',
            ""
        ],
        colModel: [
            { name: "picture.pictureUrls.THUMBNAIL.url", width:  140, align: "center", sortable: false, formatter:formatPicture },
            { name: "descriptionMap.DE.name",            width:  210, align: "right", index: "name" },
            { name: "pictureOnPage",                     width:   70, align: "right", formatter:formatTrueFalse },
            { name: "visible",                           width:   70, align: "right", formatter:formatTrueFalse },
            { name: "pointInTime",                       width:  120, align: "center", formatter:formatIsoDate },
            { name: "modified",                          width:  120, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",                        width:  100, align: "right" },
            { name: "modifiedAddress",                   width:  100, align: "right" },
            { name: "uuid",                              width:   70, align: "center", sortable: false, formatter:formatLinks }
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
        caption: '<struts:text name="appointments"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
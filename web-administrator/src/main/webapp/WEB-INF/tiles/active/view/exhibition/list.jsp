<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="exhibitionAddUrl" action="addForm" namespace="/others/exhibition" />
<struts:url var="remoteurl" action="table" namespace="/others/exhibition"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/others/exhibition/editForm.html?uncheckExhibition="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/others/exhibition/deleteForm.html?uncheckExhibition="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
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
    var newDate = new Date();
    newDate.setTime(Date.parse(celldate));
    return newDate.toLocaleDateString('de-DE', dateOptions);
}
</script>

<script type="text/javascript">
function formatIsoDateTime(celldate, options, rowObject) {
    var dateOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
    var timeOptions = { hour: '2-digit', minute: '2-digit' };
    var newDate = new Date();
    newDate.setTime(Date.parse(celldate));
    return newDate.toLocaleTimeString('de-DE', timeOptions) + " - " + newDate.toLocaleDateString('de-DE', dateOptions);
}
</script>

<script type="text/javascript">
function formatPicture(cellvalue, options, rowObject) {
	return "<img src='/image/pictureitem/" + cellvalue + "/thumbnail.png?cache=false'/>";  
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{exhibitionAddUrl}">
            <struts:text name="exhibition.add"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/others/exhibition/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            '<struts:text name="exhibition.thumbnail"/>',
            '<struts:text name="exhibition.descriptionMap.DE.name"/>',
            '<struts:text name="exhibition.moment"/>',
            '<struts:text name="exhibition.start"/>',
            '<struts:text name="exhibition.end"/>',
            '<struts:text name="exhibition.visible"/>',
            '<struts:text name="exhibition.created"/>',
            '<struts:text name="exhibition.modified"/>',
            ""
        ],
        colModel: [
            { name: "uuid",                    width:  140, align: "right", sortable: false, formatter:formatPicture },
            { name: "descriptionMap.DE.name",  width:  235, align: "right", index: "name" },
            { name: "moment",                  width:  105, align: "right", formatter:formatIsoDateTime },
            { name: "start",                   width:  140, align: "right", formatter:formatIsoDate },
            { name: "end",                     width:  140, align: "right", formatter:formatIsoDate },
            { name: "visible",                 width:  140, align: "right", formatter:formatTrueFalse },
            { name: "created",                 width:  140, align: "center", formatter:formatIsoDate },
            { name: "modified",                width:  140, align: "center", formatter:formatIsoDate },
            { name: "uuid",                    width:   70, align: "center", sortable: false, formatter:formatLinks }
        ],
        pager: "#entityPager",
        rowNum: 15,
        rowList: [5, 10, 15, 20, 25, 50, 100, 150, 200],
        sortname: "start",
        sortorder: "desc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        caption: '<struts:text name="exhibitions"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->





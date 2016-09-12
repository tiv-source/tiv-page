<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="manualAddUrl" action="addForm" namespace="/others/manual" />
<struts:url var="remoteurl" action="table" namespace="/others/manual"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/others/manual/editForm.html?manual="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/others/manual/deleteForm.html?manual="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
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
  return newDate.toLocaleFormat('%H:%M:%S - %d.%m.%Y');
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
          <struts:a href="%{manualAddUrl}">
            <struts:text name="manual.add"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/others/manual/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            '<struts:text name="picture"/>',
            '<struts:text name="descriptionMap.DE.name"/>',
            '<struts:text name="releaseDate"/>',
            '<struts:text name="visible"/>',
            '<struts:text name="created"/>',
            '<struts:text name="modified"/>',
            '<struts:text name="modifiedBy"/>',
            '<struts:text name="modifiedAddress"/>',
            ""
        ],
        colModel: [
            { name: "picture.pictureUrls.THUMBNAIL.url", width:  140, align: "center", formatter:formatPicture },
            { name: "descriptionMap.DE.name",            width:  140, align: "right" },
            { name: "releaseDate",                       width:  140, align: "right", formatter:formatIsoDate },
            { name: "visible",                           width:  140, align: "right", formatter:formatTrueFalse },
            { name: "created",                           width:  140, align: "center", formatter:formatIsoDate },
            { name: "modified",                          width:  140, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",                        width:  140, align: "right" },
            { name: "modifiedAddress",                   width:  140, align: "right" },
            { name: "uuid",                              width:  130, align: "center", sortable: false, formatter:formatLinks }
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
        caption: '<struts:text name="manuals"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
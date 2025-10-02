<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="pictureAddUrl" action="addForm" namespace="/others/picture" />
<struts:url var="remoteurl" action="table" namespace="/others/picture"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/others/picture/editForm.html?uncheckPicture="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/others/picture/deleteForm.html?uncheckPicture="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>";
}
</script>

<script type="text/javascript">
function formatPicture(cellvalue, options, rowObject) {
	return "<img src='/image/picture/" + cellvalue + "/thumbnail.png?cache=false'/>";
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

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{pictureAddUrl}">
            <struts:text name="picture.add"/>
          </struts:a>
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/others/picture/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            '<struts:text name="picture.pictureUrls.THUMBNAIL.url"/>',
            '<struts:text name="picture.descriptionMap.DE.name"/>',
            '<struts:text name="picture.gallery.descriptionMap.DE.name"/>',
            '<struts:text name="visible"/>',
            '<struts:text name="modified"/>',
            '<struts:text name="modifiedBy"/>',
            '<struts:text name="modifiedAddress"/>',
            ""
        ],
        colModel: [
            { name: "uuid",                            width:  140, align: "center", sortable: false, formatter:formatPicture },
            { name: "descriptionMap.DE.name",          width:  180, align: "left", index:"name" },
            { name: "gallery.descriptionMap.DE.name",  width:  140, align: "left", index:"gallery" },
            { name: "visible",                         width:  100, align: "right", formatter:formatTrueFalse },
            { name: "modified",                        width:  140, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",                      width:  140, align: "right" },
            { name: "modifiedAddress",                 width:  140, align: "right" },
            { name: "uuid",                            width:  130, align: "center", sortable: false, formatter:formatLinks }
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
        caption: '<struts:text name="pictures"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
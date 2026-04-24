<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="courseUnitAddUrl" action="unitAddForm" namespace="/academy/course">
  <struts:param name="uncheckCourse" value="%{course.uuid}" />
</struts:url>
<struts:url var="courseEditUrl" action="editForm" namespace="/academy/course">
  <struts:param name="uncheckCourse" value="%{course.uuid}" />
</struts:url>
<struts:url var="remoteurl" action="table" namespace="/academy/course"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/academy/course/unitEditForm.html?uncheckCourse="+ $("#courseUuid").val() + "&uncheckedCourseUnit="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/academy/course/unitDeleteForm.html?uncheckCourse="+ $("#courseUuid").val() +"&uncheckedCourseUnit="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
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
	return "<img src='/image/pictureitem/" + cellvalue + "/thumbnail.png?cache=false'/>";  
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{courseUnitAddUrl}">
            <struts:text name="courseUnit.add"/>
          </struts:a>
        </div>

        <div class="sub_menu">
          <struts:a href="%{courseEditUrl}" cssClass="cancel small_red_button button">
            <struts:text name="courseUnit.abort"/>
          </struts:a>
        </div>


<struts:hidden id="courseUuid" name="courseUuidName" value="%{course.uuid}" theme="simple"/>
<script type="text/javascript">

function createTableUrl() {
	var tableUrl = "/admin/academy/course/unit/" + $("#courseUuid").val() + "/table.html";
	console.log(tableUrl);
	return tableUrl;
}

createTableUrl();

$(function () {
    $("#entityList").jqGrid({
        url: createTableUrl(),
        datatype: "json",
        mtype: "GET",
        colNames: [
        	'<struts:text name="picture"/>',
            '<struts:text name="courseUnit.descriptionMap.DE.name"/>',
            '<struts:text name="courseUnit.pointInTime"/>',
            '<struts:text name="courseUnit.endDate"/>',
            '<struts:text name="courseUnit.duration"/>',
            '<struts:text name="modified"/>',
            '<struts:text name="modifiedBy"/>',
            ""
        ],
        colModel: [
            { name: "uuid",                            width:  140, align: "center", sortable: false, formatter:formatPicture },
            { name: "descriptionMap.DE.name",          width:  140, align: "left" },
            { name: "pointInTime",                     width:  140, align: "center", formatter:formatIsoDate },
            { name: "endDate",                         width:  140, align: "center", formatter:formatIsoDate },
            { name: "duration",                        width:  140, align: "right" },
            { name: "modified",                        width:  140, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",                      width:  140, align: "left" },
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
        caption: '<struts:text name="courseunits"/> des Kurses <struts:property value="course.descriptionMap.DE.name"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/academy/registration/editForm.html?uncheckCourse="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/academy/registration/deleteForm.html?uncheckCourse="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>";
}
</script>

<script type="text/javascript">
function formatCancelLink(cellvalue, options, rowObject) {
	if (rowObject.status == "CANCELLED") {
		return "<div class='cancelled'></div>";
	} else {
		return "<div class='cancelled'>" +
               "<a class='cancelled' href='/admin/academy/registration/cancelForm.html?uncheckedRegistration="+ cellvalue +"'>" + 
               " stornieren " + 
               "</a></div>";
	}
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

<struts:url var="registrationDeclaredUrl"     action="index"     namespace="/academy/registration" />
<struts:url var="registrationConfirmedUrl"    action="confirmed" namespace="/academy/registration" />
<struts:url var="registrationPaidUrl"         action="paid"     namespace="/academy/registration" />
<struts:url var="registrationWaitingUrl"      action="waiting"  namespace="/academy/registration" />
<struts:url var="registrationCompleteUrl"     action="complete" namespace="/academy/registration" />
<struts:url var="registrationAddUrl"          action="addForm"  namespace="/academy/registration" />

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{registrationDeclaredUrl}">
            <struts:text name="registration.declared"/>
          </struts:a>

          <struts:a href="%{registrationConfirmedUrl}">
            <struts:text name="registration.confirmed"/>
          </struts:a>

          <struts:a href="%{registrationPaidUrl}">
            <struts:text name="registration.paid"/>
          </struts:a>

          <struts:a href="%{registrationWaitingUrl}">
            <struts:text name="registration.waiting"/>
          </struts:a>

          <struts:a href="%{registrationCompleteUrl}">
            <struts:text name="registration.complete"/>
          </struts:a>

          <struts:a href="%{registrationAddUrl}">
            <struts:text name="registration.add"/>
          </struts:a>
        </div>

<struts:url var="remoteurl" action="table" namespace="/academy/registration"/>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/academy/registration/tableComplete.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
        	'<struts:text name="consumer"/>',
        	'<struts:text name="firm"/>',
        	'<struts:text name="salutation"/>',
        	'<struts:text name="firstname"/>',
        	'<struts:text name="lastname"/>',
            '<struts:text name="course"/>',
            '<struts:text name="status"/>',
            '<struts:text name="listOfMaterials"/>',
            '<struts:text name="created"/>',
            '<struts:text name="modified"/>',
            '<struts:text name="modifiedBy"/>',
            "",
            ""
        ],
        colModel: [
        	{ name: "consumer",                          width:  140, align: "center", hidden: true, formatter:formatTrueFalse },
        	{ name: "firm",                              width:  140, align: "left" },
            { name: "salutation.descriptionMap.DE.name", width:  140, align: "left" },
            { name: "firstname",                         width:  140, align: "left" },
            { name: "lastname",                          width:  140, align: "left" },
            { name: "course.descriptionMap.DE.name",     width:  140, align: "left" },
            { name: "status",                            width:  140, align: "left" },
            { name: "listOfMaterials",                   width:  140, align: "center", formatter:formatTrueFalse },
            { name: "created",                           width:  140, align: "center", formatter:formatIsoDate },
            { name: "modified",                          width:  140, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",                        width:  140, align: "left" },
            { name: "uuid",                              width:  140, align: "center", sortable: false, formatter:formatLinks },
            { name: "uuid",                              width:  140, align: "center", sortable: false, formatter:formatCancelLink }
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
        hidegrid: false,
        caption: '<struts:text name="registrations"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
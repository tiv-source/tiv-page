<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="captchaAddUrl" action="addForm" namespace="/system/captcha" />
<struts:url var="remoteurl" action="table" namespace="/system/captcha" />

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/system/captcha/editForm.html?uncheckCaptcha="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:16px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/system/captcha/deleteForm.html?uncheckCaptcha="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:16px;'/>" + 
         "</a>";
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
	return "<img src='/image/captcha/" + cellvalue + "/thumbnail.png?cache=false'/>";  
}
</script>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/system/captcha/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            '<struts:text name="captcha.image.uploadFile"/>',
            '<struts:text name="captcha.content"/>', 
            '<struts:text name="created"/>',
            '<struts:text name="modified"/>',
            '<struts:text name="modifiedBy"/>',
            '<struts:text name="modifiedAddress"/>',
            ""
        ],
        colModel: [
            { name: "uuid",            width:  140, align: "center", formatter:formatPicture },
            { name: "content",         width:  140 },
            { name: "created",         width:  140, align: "center", formatter:formatIsoDate },
            { name: "modified",        width:  140, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",      width:  140, align: "right" },
            { name: "modifiedAddress", width:  210, align: "right" },
            { name: "uuid",            width:  130, align: "center", sortable: false, formatter:formatLinks }
        ],
        pager: "#entityPager",
        rowNum: 10,
        rowList: [5, 10, 15, 20, 25, 50, 100, 150, 200],
        sortname: "uuid",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        caption: '<struts:text name="captchas"/>'
    }); 
}); 
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{captchaAddUrl}">
            <struts:text name="captcha.add"/>
          </struts:a>
        </div>

        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
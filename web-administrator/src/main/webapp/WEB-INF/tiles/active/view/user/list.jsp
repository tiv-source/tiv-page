<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="userAddUrl" action="addForm" namespace="/user" />
<struts:url var="remoteurl" action="table" namespace="/user"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/system/user/editForm.html?user="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/pencil.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/system/user/copyForm.html?user="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/copy.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/system/user/deleteForm.html?user="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/delete.png' style='width:14px;'/>" + 
         "</a>";
}
</script>


<script type="text/javascript">
function formatIsoDate(celldate, options, rowObject) {
  var newDate = new Date();
  newDate.setTime(Date.parse(celldate));
  return newDate.toLocaleFormat('%H:%M - %d.%m.%Y');
}
</script>




      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          <struts:a href="%{userAddUrl}">
            <struts:text name="user.add"/>
          </struts:a>
        </div>


<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/system/user/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            "Benutzername", 
            "Vorname", 
            "Nachname", 
            "EMail", 
            "Bearbeitet am", 
            "Bearbeitet von",
            "Bearbeitungsadresse",
            ""
        ],
        colModel: [
            { name: "username",        width:  140 },
            { name: "firstname",       width:  140, align: "center" },
            { name: "lastname",        width:  140, align: "center" },
            { name: "email",           width:  140, align: "center" },
            { name: "modified",        width:  140, align: "center", formatter:formatIsoDate },
            { name: "modifiedBy",      width:  140, align: "right" },
            { name: "modifiedAddress", width:  210, align: "right" },
            { name: "uuid",            width:  130, align: "center", sortable: false, formatter:formatLinks }
        ],
        pager: "#entityPager",
        rowNum: 10,
        rowList: [5, 10, 15, 20, 25, 50, 100, 150, 200],
        sortname: "firstname",
        sortorder: "asc",
        viewrecords: true,
        gridview: true,
        autoencode: true,
        jsonReader : {root:"gridModel", records: "record"},
        width : 1600,
        cellLayout : 5,
        height:'auto',
        caption: "Benutzer"
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
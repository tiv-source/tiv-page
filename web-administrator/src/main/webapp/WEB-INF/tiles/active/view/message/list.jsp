<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="remoteurl"   action="table"   namespace="/message"/>

<script type="text/javascript">
function formatLinks(cellvalue, options, rowObject) {
  return "<a href='/admin/others/message/view.html?message="+ cellvalue + "' style='border-style: none; display: inline;'>" + 
         "<img src='/admin/icons/16x16/view.png' style='width:14px;'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/others/message/deleteForm.html?message="+ cellvalue +"' style='border-style: none; display: inline;'>" + 
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
  var newDate = new Date();
  newDate.setTime(Date.parse(celldate));
  return newDate.toLocaleFormat('%H:%M:%S - %d.%m.%Y');
}
</script>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">
          
        </div>

<script type="text/javascript">
$(function () {
    $("#entityList").jqGrid({
        url: "/admin/others/message/table.html",
        datatype: "json",
        mtype: "GET",
        colNames: [
            '<struts:text name="message.gender"/>',
            '<struts:text name="message.firstname"/>',
            '<struts:text name="message.lastname"/>',
            '<struts:text name="message.telephone"/>',
            '<struts:text name="message.fax"/>',
            '<struts:text name="message.email"/>',
            '<struts:text name="created"/>',
            '<struts:text name="createdAddress"/>',
            ""
        ],
        colModel: [
            { name: "gender",             width:  140, align: "right", formatter:formatGender },
            { name: "firstname",          width:  140, align: "right" },
            { name: "lastname",           width:  140, align: "right" },
            { name: "telephone",          width:  140, align: "right" },
            { name: "fax",                width:  140, align: "right" },
            { name: "mail",               width:  140, align: "right" },
            { name: "created",            width:  140, align: "center", formatter:formatIsoDate },
            { name: "createdAddress",     width:  140, align: "right" },
            { name: "uuid",               width:  130, align: "center", sortable: false, formatter:formatLinks }
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
        caption: '<struts:text name="messages"/>'
    }); 
}); 
</script>



        <table id="entityList"><tr><td></td></tr></table> 
        <div id="entityPager"></div>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
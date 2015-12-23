<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<struts:url var="eventAddUrl" action="addForm" namespace="/event" />
<struts:url id="remoteurl" action="table" namespace="/event"/>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/reservation/index.html?event="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/view.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/event/editForm.html?event="+ cellvalue + "' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/pencil.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/event/copyForm.html?event="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/copy.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/event/deleteForm.html?event="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/delete.png'/>" + 
         "</a>";
}
</script>

<script type="text/javascript">
function formatTrueFalse(cellvalue, options, rowObject) {
  if (cellvalue) {
    return "<img src='/admin/icons/16x16/accept.png'/>";  
  } else {
    return "<img src='/admin/icons/16x16/oneway.png'/>";
  }
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
          <struts:a href="%{eventAddUrl}">
            <struts:text name="event.add"/>
          </struts:a>
        </div>

        <sjg:grid
          id="gridedittable"
          caption="Event"
          dataType="json"
          href="%{remoteurl}"
          pager="true"
          navigator="true"
          navigatorAdd="false"
          navigatorSearch="false"
          navigatorEdit="false"
          navigatorView="false"
          navigatorDelete="false"
          gridModel="gridModel"
          rowList="5,10,15,20"
          rowNum="20"
          editinline="false"
          viewrecords="true"
        >
    	  <sjg:gridColumn 
    	    name="descriptionMap.DE.name" 
    	    index="descriptionMap.DE.name" 
    	    title="Name" 
    	    width="280" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="location.descriptionMap.DE.name" 
    	    index="location.descriptionMap.DE.name" 
    	    title="Location" 
    	    width="300" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="price" 
    	    index="price" 
    	    title="Preis" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	  />
    	  <sjg:gridColumn 
    	    name="beginning" 
    	    index="beginning" 
    	    title="Beginning" 
    	    width="160" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatIsoDate"
    	  />
    	  <sjg:gridColumn 
    	    name="ending" 
    	    index="ending" 
    	    title="Ending" 
    	    width="160" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center"
    	    formatter="formatIsoDate" 
    	  />
    	  <sjg:gridColumn 
    	    name="deadline" 
    	    index="deadline" 
    	    title="Deadline" 
    	    width="160" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center"
    	    formatter="formatIsoDate" 
    	  />
    	  <sjg:gridColumn 
    	    name="reservation" 
    	    index="reservation" 
    	    title="Reservation" 
    	    width="100" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatTrueFalse"
    	  />
    	  <sjg:gridColumn 
    	    name="visible" 
    	    index="visible" 
    	    title="Visible" 
    	    width="70" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatTrueFalse"
    	  />
    	  <sjg:gridColumn 
    	    name="uuid" 
    	    index="editbar" 
    	    title="" 
    	    width="140" 
    	    editable="false" 
    	    sortable="false" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="right" 
    	    formatter="formatEditLink" 
    	  />    	
        </sjg:grid>

        <div style="width:100%; margin: 10px;">&nbsp;</div>
    
      </div>
      <!--  Ende MAIN -->
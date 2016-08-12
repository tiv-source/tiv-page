<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="remoteurl"   action="queueTable"   namespace="/reservation" />


<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/reservation/confirmForm.html?reservation="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/view.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" +
         "<a href='/admin/reservation/editForm.html?reservation="+ cellvalue + "' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/pencil.png'/>" + 
         "</a>&nbsp;&nbsp;&nbsp;" + 
         "<a href='/admin/reservation/deleteForm.html?reservation="+ cellvalue +"' style='border-style: none;'>" + 
         "<img src='/admin/icons/16x16/delete.png'/>" + 
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
  return newDate.toLocaleFormat('%d.%m.%Y');
}
</script>

<script type="text/javascript">
function formatIsoTime(celldate, options, rowObject) {
  var newDate = new Date();
  newDate.setTime(Date.parse(celldate));
  return newDate.toLocaleFormat('%H:%M');
}
</script>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu">

        </div>
        
        <sjg:grid
          id="gridedittable"
          caption="Unbestätigte Reservierungen"
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
          rowList="5,10,15,20,25,50,100"
          rowNum="20"
          editinline="false"
          viewrecords="true"
        >
    	  <sjg:gridColumn 
    	    name="event.location.descriptionMap.DE.name" 
    	    index="location.name" 
    	    title="%{getText('event.location.descriptionMap.DE.name')}" 
    	    width="175" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="event.descriptionMap.DE.name" 
    	    index="event.name" 
    	    title="%{getText('event.descriptionMap.DE.name')}" 
    	    width="140" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="left" 
    	  />
    	  <sjg:gridColumn 
    	    name="event.beginning" 
    	    index="beginning" 
    	    title="%{getText('event.beginning')}" 
    	    width="70" 
    	    editable="false" 
    	    sortable="true" 
    	    hidden="false" 
    	    search="false" 
    	    resizable="false" 
    	    align="center" 
    	    formatter="formatIsoDate"
    	  />
    	  <sjg:gridColumn
    	      name="gender"
    	      index="gender"
    	      title="Geschlecht"
    	      width="110"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	      formatter="formatGender"
    	  />
    	  <sjg:gridColumn
    	      name="firstname"
    	      index="firstname"
    	      title="Firstname"
    	      width="210"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="left"
    	  />
    	  <sjg:gridColumn
    	      name="lastname"
    	      index="lastname"
    	      title="Lastname"
    	      width="210"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="left"
    	  />
    	  <sjg:gridColumn
    	      name="telephone"
    	      index="telephone"
    	      title="Telephone"
    	      width="120"
    	      editable="false"
    	      sortable="false"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="left"
    	  />
    	  <sjg:gridColumn
    	      name="email"
    	      index="email"
    	      title="Mail"
    	      width="210"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	  />
    	  <sjg:gridColumn
    	      name="quantity"
    	      index="quantity"
    	      title="Quantity"
    	      width="140"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	  />
    	  <sjg:gridColumn 
    	      name="time"
    	      index="time"
    	      title="Time"
    	      width="70"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	      formatter="formatIsoTime"
    	  />
    	  <sjg:gridColumn
    	      name="uuid"
    	      index="editbar"
    	      title=""
    	      width="70"
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
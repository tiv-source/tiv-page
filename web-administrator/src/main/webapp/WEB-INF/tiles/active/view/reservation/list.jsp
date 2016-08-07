<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

<struts:url var="getListUrl" action="list" namespace="/reservation">
  <struts:param name="event" value="event.uuid"/>
</struts:url>

<struts:url var="addReservationUrl" action="addForm" namespace="/reservation">
  <struts:param name="event" value="event.uuid"/>
</struts:url>

<struts:url id="remoteurl"   action="table"   namespace="/reservation">
  <struts:param name="event" value="event.uuid"/>
</struts:url>

<script type="text/javascript">
function formatEditLink(cellvalue, options, rowObject) {
  return "<a href='/admin/reservation/view.html?reservation="+ cellvalue +"' style='border-style: none;'>" + 
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
  return newDate.toLocaleFormat('%H:%M:%S - %d.%m.%Y');
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
          <struts:a href="%{addReservationUrl}">
            <struts:text name="reservation.add"/>
          </struts:a>
          <struts:a href="%{getListUrl}">
            <struts:text name="reservation.list"/>
          </struts:a>
        </div>
        
        <sjg:grid
          id="gridedittable"
          caption="Reservierungen für das %{event.getName(getText('language'))} am %{getText('format.reservation.date',{event.beginning})} im %{event.location.getName(getText('language'))}"
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
          rowNum="15"
          editinline="false"
          viewrecords="true"
        >
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
    	      width="190"
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
    	      width="270"
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
    	      name="createdAddress"
    	      index="createdAddress"
    	      title="createdAddress"
    	      width="140"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	  />
    	  <sjg:gridColumn 
    	      name="created"
    	      index="created"
    	      title="Created"
    	      width="140"
    	      editable="false"
    	      sortable="true"
    	      hidden="false"
    	      search="false"
    	      resizable="false"
    	      align="center"
    	      formatter="formatIsoDate"
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
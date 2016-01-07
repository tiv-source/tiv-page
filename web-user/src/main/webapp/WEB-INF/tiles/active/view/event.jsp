<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div>
      <img alt="" src="/uploads/<struts:property value="event.picture" />" style="width: 100%;" />
    </div>
    <h1>Reservierung</h1>
    <h6><struts:property value="event.getName(getText('language'))" /> im <struts:property value="event.location.getName(getText('language'))" /> am <struts:date name="event.beginning" format="dd.MM.yyyy" /></h6>

    <struts:form
          cssClass="form" 
          action="reserve" 
          namespace="/event" 
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
    >
      <struts:hidden    key="reservation.event" value="%{event.uuid}" />
      
      <fieldset class="fieldset">

        <div class="field">
          <struts:radio     key="reservation.gender" list="#{true:'Frau',false:'Herr'}" />
        </div>

        <div class="field">
          <struts:textfield key="reservation.firstname"  />
        </div>

        <div class="field">
          <struts:textfield key="reservation.lastname"  />
        </div>

        <div class="field">
          <struts:textfield key="reservation.email"  />
        </div>

        <div class="field">
          <struts:textfield key="reservation.telephone" />
        </div>

        <div class="field">
          <struts:select
              key="reservation.quantity"
              multiple="false"
              value="reservation.quantity"
              list="#{'1':'01','2':'02','3':'03','4':'04','5':'05','6':'06','7':'07','8':'08','9':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20'}"
          />
        </div>

        <div class="field">
          <struts:select
              key="reservation.time"
              listValue="%{getText('format.time',{clone()})}"
              listKey="toString()"
              multiple="false"
              list="times"
          />
        </div>

        <div class="field">
          <struts:textarea  key="reservation.wishes" />
        </div>

      </fieldset>

      <struts:submit type="button" value="Reservieren" />
      <struts:reset type="button" value="Löschen" />
      
      <hr>
    </struts:form>
  
    

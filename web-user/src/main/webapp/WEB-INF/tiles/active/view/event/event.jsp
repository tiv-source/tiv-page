<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div id="sitePicture">
      <img alt="" src="/pictures/FULL/<struts:property value="event.picture.pictureUrls.FULL.url" />">
    </div>

  <!-- Content Anfang -->
  <div id=content>

    <h1>Reservierung</h1>
    <h6><struts:property value="event.getName(getText('language'))" /> im <struts:property value="event.location.getName(getText('language'))" /> am <struts:date name="event.beginning" format="dd.MM.yyyy" /></h6>

    <struts:if test="getProperty('event.show.description') == 'true'">
      <p><struts:property value="event.getDescription(getText('language'))" /></p>
    </struts:if>

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
              list="quantityList"
          />
        </div>

        <struts:if test="event.timeSelection">
          <div class="field">
            <struts:select
                key="reservation.time"
                listValue="%{getText('format.time',{clone()})}"
                listKey="toString()"
                multiple="false"
                list="times"
            />
          </div>
        </struts:if>
        <struts:else>
          <struts:hidden 
            key="reservation.time" 
            value="%{event.formatedDate}" 
          />
        </struts:else>

        <div class="field">
          <struts:textarea  key="reservation.wishes" />
        </div>

      </fieldset>

      <struts:submit type="button" value="Reservieren" />
      <struts:reset type="button" value="Löschen" />
      
      <hr>
    </struts:form>
  
    <hr>
  </div>
  <!-- Content Ende -->

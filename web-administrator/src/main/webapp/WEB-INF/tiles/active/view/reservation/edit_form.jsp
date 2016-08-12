<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5>
            <struts:text name="reservation.edit"/> 
            für das 
            <struts:text name="%{reservation.event.getName(getText('language'))}"/> 
            am 
            <struts:text name="%{getText('format.reservation.date',{reservation.event.beginning})}"/> 
            im
            <struts:text name="%{reservation.event.location.getName(getText('language'))}"/> 
          </h5>

        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="edit" 
                  namespace="/locations/reservation" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
          >
            <struts:hidden key="reservation.uuid"/>

            <fieldset class="fieldset">

              <div class="field">
                <struts:radio
                    key="reservation.gender"
                    list="#{true:'Frau',false:'Herr'}" 
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em; float: left;"
					labelposition="left"
                />
              </div>
              
              <div class="field">
                <struts:textfield 
                    key="reservation.firstname" 
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"

                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="reservation.lastname"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="reservation.email" 
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="reservation.telephone"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:select
                    key="reservation.quantity"
                    multiple="false"
                    value="reservation.quantity"
                    list="#{'1':'01','2':'02','3':'03','4':'04','5':'05','6':'06','7':'07','8':'08','9':'09','10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19','20':'20'}" 
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:select
                    key="reservation.time"
                    listValue="%{getText('format.time',{clone()})}"
                    listKey="toString()"
                    multiple="false"
                    list="times"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"

                />
              </div>

              <div class="field">
                <struts:textarea  key="reservation.wishes" />
              </div>
            </fieldset>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.save"/>
              </button>
              <struts:url var="abortReservationUrl" action="index" namespace="/locations/reservation">
                <struts:param name="event" value="reservation.event.uuid"/>
              </struts:url>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  href="%{abortReservationUrl}">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

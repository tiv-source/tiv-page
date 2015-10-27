<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="usermanager_update_header" class="update usermanager module_header">
          <h2>Location Opening</h2>
        </div>

        <div id="backend_update_form" class="update">


            <fieldset class="fieldset">

              <div class="field">
                <label for="location.descriptionMap.DE.name" class="label">Name:</label>
                <struts:property value="location.descriptionMap.DE.name"/>
              </div>

              <div class="field">
                <label for="location.descriptionMap.DE.name" class="label">Name:</label>
                <struts:property value="location.descriptionMap.DE.name"/>
              </div>
              
              <table>
                <tr>
                  <td>Wochentag</td>
                  <td>von</td>
                  <td>bis</td>
                  <td></td>
                </tr>

                <struts:set var="myLocation" value="location.uuid" />
                <struts:iterator value="location.openingHours" status="openingHoursStatus">
                <tr>
                  <td><struts:property value="weekday" /></td>
                  <td><struts:property value="open" /></td>
                  <td><struts:property value="close" /></td>
                  <td>
                    <struts:url id="openingHourDeleteURL" action="openingHourDeleteForm" namespace="/location">
                      <struts:param name="openingHours" value="#openingHoursStatus.index" />
                      <struts:param name="locationUuid" value="#myLocation" />
                    </struts:url>
                    <struts:a
                        id="submit_deny__Close"
                        name="submitClose"
                        cssClass="cancel small_red_button button"
                        href="%{openingHourDeleteURL}"
                    >
                        l&ouml;schen
                    </struts:a>
                  </td>
                </tr>
                </struts:iterator>

              </table>



            </fieldset>

            <div class="buttons form_bottom">
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/location">Zur&uuml;ck</struts:a>
            </div>
          
        </div>


      </div>
      <!--  Ende MAIN -->

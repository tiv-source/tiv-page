<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>


      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="location.openingHours" /></h5>
        </div>

        <div id="backend_update_form" class="update">

          <form class="form">

            <fieldset class="fieldset">

              <div class="field" style="padding-left: 13px;">
                <label for="location.descriptionMap.DE.name" class="label" style="display: block; float: left; width: 77px;">Filiale: </label>
                <struts:property value="location.descriptionMap.DE.name"/>
              </div>

              <div class="field" style="padding-left: 13px;">
                <label for="location.address.city" class="label" style="display: block; float: left; width: 77px;">Ort: </label>
                <struts:property value="location.address.city"/>
              </div>

              <div class="field" style="padding-left: 13px;">
                <struts:url var="openingHourAddUrl" action="openingHourAddForm" namespace="/locations/location">
                  <struts:param name="uncheckLocation" value="location.uuid" />
                </struts:url>
                <struts:a href="%{openingHourAddUrl}" cssStyle="color: white; font-weight: bold; padding: 0px 1.75em; background-image: url('/admin/images/buttonSmallMiddle.png'); font-size: 10pt; width: 70px; margin-left: 435px;">
                  <struts:text name="add"/>
                </struts:a>
              </div>

              <div class="field" style="padding-left: 13px;">
                <table style="margin-top: 14px; margin-left: 70px;">
                  <tr>
                    <td style="width: 140px; border: 1px solid black; padding: 2px 7px;"><b><struts:text name="weekday" /></b></td>
                    <td style="width: 77px; border: 1px solid black; padding: 2px 7px;"><b><struts:text name="from" /></b></td>
                    <td style="width: 77px; border: 1px solid black; padding: 2px 7px;"><b><struts:text name="to" /></b></td>
                    <td style="width: 105px; border: 1px solid black;"></td>
                  </tr>

                  <struts:set var="myLocation" value="location.uuid" />
                  <struts:iterator value="location.openingHours" status="openingHoursStatus">
                  <tr>
                    <td style="border: 1px solid black; padding: 2px 7px;">
                      <struts:property value="getText(weekday)" />
                    </td>
                    <td style="border: 1px solid black; padding: 2px 7px;">
                      <struts:property value="open" /> 
                      <struts:text name="clock" />
                    </td>
                    <td style="border: 1px solid black; padding: 2px 7px;">
                      <struts:property value="close" /> 
                      <struts:text name="clock" />
                    </td>
                    <td style="border: 1px solid black; padding: 2px 7px;">
                      <struts:url var="openingHourDeleteURL" action="openingHourDeleteForm" namespace="/locations/location">
                        <struts:param name="openingHours" value="#openingHoursStatus.index" />
                        <struts:param name="uncheckLocation" value="#myLocation" />
                      </struts:url>
                      <struts:a
                          id="submit_deny__Close"
                          name="submitClose"
                          cssClass="cancel small_red_button button"
                          href="%{openingHourDeleteURL}">
                            <struts:text name="form.delete"/>
                      </struts:a>
                    </td>
                  </tr>
                  </struts:iterator>

                </table>
              </div>

            </fieldset>

            <div class="buttons form_bottom" style="height: 21px;">
              <struts:url var="editFormUrl" action="editForm" namespace="/locations/location">
                <struts:param name="uncheckLocation" value="location.uuid" />
              </struts:url>
              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  href="%{editFormUrl}">
                    <struts:text name="form.abort"/>
              </struts:a>
            </div>

          </form>
        </div>


      </div>
      <!--  Ende MAIN -->

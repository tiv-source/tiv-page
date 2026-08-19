<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>

      <!--  Start MAIN -->
      <div class="main">
        <div class="sub_menu"></div>
        <div id="title">
          <h5><struts:text name="registration.add"/></h5>
        </div>

        <div id="backend_update_form" class="update">
          <struts:form 
                  cssClass="form" 
                  action="add" 
                  namespace="/academy/registration" 
                  tooltipIconPath="/images/info.png" 
                  javascriptTooltip="true" 
                  tooltipDelay="500"
                  theme="css_xhtml"
                  enctype="multipart/form-data"
                  method="post"
          >

          <div id="participant">
            <fieldset class="fieldset">

              <p class="headline">Teilnehmer:</p>

              <div class="field">
                <struts:radio
                  key="registration.salutation"
                  list="salutations"
                  listValue="%{getName(getText('language'))}"
                  listKey="uuid"
                  value="registration.salutation.{uuid}"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="registration.firstname"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:98%;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="registration.lastname"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:98%;"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="registration.birthday"
                    placeholder="01.01.2001"
                    type="date"
                    value="%{getText('format.birthday',{registration.birthday})}"
                />
              </div>
            </fieldset>
          </div>


          <div id="billingData">
            <fieldset class="fieldset">
              <p class="headline">Rechnungsdaten:</p>

              <div class="field">
                  <struts:radio
                      key="registration.consumer"
                      list="sbtList"
                      listValue="name"
                      listKey="value"
                  />
              </div>
<script type="text/javascript">
$(document).ready(function() {
   $('input[type="radio"]').click(function() {
       if($(this).attr('id') == 'add_registration_consumertrue') {
            $('#wwgrp_add_registration_firm').hide();
            $('#add_registration_firm').val("");
            
       }

       if($(this).attr('id') == 'add_registration_consumerfalse') {
    	   $('#wwgrp_add_registration_firm').show();
      }
   });
   if(!$('#registration_add_consumerfalse').is(":checked")) {
	   $('#wwgrp_add_registration_firm').hide();
	   $('#add_registration_consumertrue').prop('checked', true);
   }
});
</script>

              <div class="field">
                <struts:textfield
                    key="registration.firm"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:98%;"
                />
              </div>

              <div class="field">
                <struts:textfield 
                    key="registration.street"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:98%;"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="registration.zip"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:98%;"
                />
              </div>

              <div class="field">
                <struts:textfield
                    key="registration.city"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em; width:98%;"
                />
              </div>

              <div class="field">
                <struts:select
                    key="registration.country"
                    listValue="%{getName(getText('language'))}"
                    listKey="uuid"
                    multiple="false"
                    parentTheme="css_xhtml"
                    list="countryList"
                    value="registration.country.{uuid}"
                />
              </div>

            </fieldset>
          </div>

          <div id="contactDetails">
            <fieldset class="fieldset">

              <p class="headline">Kontaktdaten:</p>

              <div class="field">
                  <struts:textfield key="registration.email"  />
              </div>

              <div class="field">
                  <struts:textfield
                      key="registration.telephone"
                      placeholder="+49-172-5555555"
                      inputmode="tel"
                  />
              </div>

            </fieldset>
          </div>

          <div id="objectOfAgreement">
            <fieldset class="fieldset">

              <p class="headline">Vertragsdaten:</p>

              <div class="field">
                <struts:select
                    key="registration.course"
                    listValue="%{getName(getText('language')) + ' (' + number + ')'}"
                    listKey="uuid"
                    multiple="false"
                    value="registration.course.{uuid}"
                    list="courseList"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="registration.listOfMaterials"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="registration.privacy"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="registration.terms"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>

              <div class="field">
                <struts:select
                    key="registration.status"
                    listValue="%{toString()}"
                    listKey="%{toString()}"
                    multiple="false"
                    value="registration.status"
                    list="statusList"
                />
              </div>

              <div class="field">
                <struts:checkbox
                    key="registration.fullyPaid"
                    parentTheme="xhtml"
					cssStyle="padding: 0.3em;"
					labelposition="left"
                />
              </div>
<script type="text/javascript">
$(document).ready(function() {
    $('#add_registration_fullyPaid').on('change', function() {
        if ($(this).is(':checked')) {
            $('#wwgrp_add_registration_partialPayment').hide();
            $('#add_registration_partialPayment').val("");
        } else {
        	$('#wwgrp_add_registration_partialPayment').show();
        	$('#add_registration_partialPayment').val("0,00");
        }
    });
});
</script>
              <div class="field">
                <struts:textfield 
                    key="registration.partialPayment"
                    parentTheme="css_xhtml"
                    cssStyle="padding: 0.3em;"
                    min="0"
                    inputmode="decimal"
                    placeholder="100,00"
                />
              </div>

          </fieldset>
        </div>

            <div class="buttons form_bottom">
              <button 
                  id="submit_confirm__Save" 
                  name="submit" 
                  value="save" 
                  class="save small_green_button button">
                    <struts:text name="form.save"/>
              </button>

              <struts:a 
                  id="submit_deny__Close" 
                  name="submitClose" 
                  cssClass="cancel small_red_button button" 
                  action="index" 
                  namespace="/academy/registration">
                   <struts:text name="form.abort"/>
              </struts:a>
            </div>
          </struts:form>

          
        </div>


      </div>
      <!--  Ende MAIN -->

      

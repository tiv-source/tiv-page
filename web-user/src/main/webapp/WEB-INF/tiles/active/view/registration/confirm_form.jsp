<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="registration.course.uuid" />/large.png">
    </div>

  <!-- Content Anfang -->
  <div id=content>

    <div id="formContent">
      <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />
    </div>

    <struts:form
          cssClass="form" 
          action="confirm.html"
          tooltipIconPath="/images/info.png" 
          javascriptTooltip="true" 
          tooltipDelay="500"
          theme="css_xhtml"
    >
    
      <div class="fieldgrp" id="group1">
        <!-- Anfang Gruppe 1 -->  
        <fieldset class="fieldset" id="personal">
          <div class="header">
            <h2>Teilnehmer</h2>
          </div>

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
            <struts:textfield key="registration.firstname"  />
          </div>

          <div class="field">
            <struts:textfield key="registration.lastname"  />
          </div>

          <div class="field">
            <struts:textfield key="registration.birthday"  />
          </div>

        </fieldset>

        <fieldset class="fieldset" id="contact">
          <div class="header">
            <h2>Kontaktdaten</h2>
          </div>

          <div class="field">
            <struts:textfield key="registration.email"  />
          </div>

          <div class="field">
            <struts:textfield key="registration.telephone" />
          </div>

        </fieldset>

        <!-- Ende Gruppe 1 -->
      </div>

      <div class="fieldgrp" id="group2">
        <!-- Anfang Gruppe 2 -->

        <fieldset class="fieldset" id="factura">
          <div class="header">
            <h2>Rechnungsdaten</h2>
          </div>

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
       if($(this).attr('id') == 'confirm_registration_consumertrue') {
            $('#wwgrp_confirm_registration_firm').hide();
       }
       
       if($(this).attr('id') == 'confirm_registration_consumerfalse') {
    	   $('#wwgrp_confirm_registration_firm').show();
      }
   });
   if(!$('#confirm_registration_consumerfalse').is(":checked")) {
	   $('#wwgrp_confirm_registration_firm').hide();
	   $('#confirm_registration_consumertrue').prop('checked', true);
   }
});
</script>

          <div class="field">
            <struts:textfield key="registration.firm"  />
          </div>

          <div class="field">
            <struts:textfield key="registration.street"  />
          </div>

          <div class="field">
            <struts:textfield key="registration.zip"  />
          </div>

          <div class="field">
            <struts:textfield key="registration.city"  />
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

        <!-- Ende Gruppe 2 -->
      </div>

      <div class="fieldgrp" id="group3">
        <!-- Anfang Gruppe 3 -->

        <fieldset class="fieldset" id="course">
          <div class="header">
            <h2>Kurs</h2>
          </div>

          <div class="name">
            <h3><struts:property value="registration.course.getName(getText('language'))" /> (<struts:property value="registration.course.number" />)</h3>
          </div>

          <div class="specifications">
            <div class="image">
              <img id="image" alt="<struts:property value="registration.course.getName(getText('language'))" />" src="/image/pictureitem/<struts:property value="registration.course.uuid" />/thumbnail.png">
            </div>
            <div class="descriptionbox">
              <div class="description">
                <p id="description">
                  <struts:property value="registration.course.getDescription(getText('language'))" />
                </p>
              </div>
              <div class="startdate">
                <p id="startdate">
                  beginnt am <struts:date name="registration.course.startDate" format="dd.MM.yyyy" />
                </p>
              </div>
            </div>
          </div>

          <div class="price">
            <div class="number"><struts:property value="getText('{0,number,#,##0.00}',{registration.course.price})"/> &euro;</div>
            <div class="unit">pro Teilnehmer</div>
            <struts:hidden key="registration.course.price" />
          </div>

        </fieldset>

        <struts:if test="registration.course.listOfMaterials">
        <fieldset class="fieldset" id="material">

          <div class="field">
            <struts:checkbox key="registration.listOfMaterials" theme="tivpage" onchange="calculateSum(this)" value="true"/>
            <script type="text/javascript">
            function calculateSum(element) {
          	  if(element.checked) {
          		  var material = parseFloat(($('#registration_course_listOfMaterialsPrice').val()).replace(",","."));
          		  var course = parseFloat(($('#registration_course_price').val()).replace(",","."));
          		  var result = material + course;
          		  var sum = result.toFixed(2);
          		  $('#sum').text(new Intl.NumberFormat("de-DE", { style: "currency", currency: "EUR" }).format(
          	        sum,
          		  ));
          		  console.log("Summe Checked");
          		  console.log(sum);
          	  } else {
          		  var course = parseFloat(($('#registration_course_price').val()).replace(",","."));
          		  var sum = course.toFixed(2);
          		  $('#sum').text(new Intl.NumberFormat("de-DE", { style: "currency", currency: "EUR" }).format(
                    sum,
          		  ));
          		  console.log("Summe Unchecked");
          		  console.log(sum);
          	  }
            }
            </script>
          </div>

          <div class="price">
            <div class="number"><struts:property value="getText('{0,number,#,##0.00}',{registration.course.listOfMaterialsPrice})"/> &euro;</div>
            <div class="unit">pro Teilnehmer</div>
            <struts:hidden key="registration.course.listOfMaterialsPrice" />
          </div>

        </fieldset>
        </struts:if>

        <!-- Ende Gruppe 3 -->
      </div>

      <div class="fieldgrp" id="group4">
        <!-- Anfang Gruppe 4 -->
        <fieldset class="fieldset" id="privacy">
          <div class="field">
            <struts:checkbox key="registration.privacy" theme="tivpage"/>
          </div>
        </fieldset>

        <fieldset class="fieldset" id="terms">
          <div class="field">
            <struts:checkbox key="registration.terms" theme="tivpage"/>
          </div>
        </fieldset>

        <!-- Ende Gruppe 4 -->
      </div>

    <struts:if test="!waiting">
      <div class="fieldgrp" id="group5">
        <!-- Anfang Gruppe 5 -->
        <div class="sumlbl">
          Gesamtsumme:
        </div>

        <div class="sum">
          <p id="sum"><struts:property value="getText('{0,number,#,##0.00}',{registration.course.sum})"/> &euro;</p>
        </div>
        <!-- Ende Gruppe 5 -->
      </div>
    </struts:if>

      <div class="fieldgrp" id="group6">
        <struts:if test="!waiting">
          <struts:submit type="button" value="Zahlungspflichtig anmelden" onclick="this.disabled=true; this.form.submit();" />
        </struts:if>
        <struts:else>
          <struts:submit type="button" value="In Warteliste eintragen" onclick="this.disabled=true; this.form.submit();" />
        </struts:else>
      </div>

      <hr>
    </struts:form>
  
    <hr>
  </div>
  <!-- Content Ende -->

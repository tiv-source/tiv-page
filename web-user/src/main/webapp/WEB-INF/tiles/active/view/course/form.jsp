<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags"%>

    <div id="sitePicture">
      <img alt="" src="/image/pictureitem/<struts:property value="course.uuid" />/large.png">
    </div>

  <!-- Content Anfang -->
  <div id=content>

    <div id="formContent">
      <struts:property escapeHtml="false" value="page.getContent(getText('language'))" />
    </div>

    <struts:form
          cssClass="form" 
          action="registration.html"
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
            <struts:textfield 
                key="registration.birthday"
                placeholder="01.01.2001"
                format="dd-MM-yyyy"
                type="date"
            />
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
            <struts:textfield 
                key="registration.telephone"
                placeholder="+49-172-5555555"
                inputmode="tel"
            />
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
       if($(this).attr('id') == 'registration_registration_consumertrue') {
            $('#wwgrp_registration_registration_firm').hide();
            $('#registration_registration_firm').val("");
            
       }

       if($(this).attr('id') == 'registration_registration_consumerfalse') {
    	   $('#wwgrp_registration_registration_firm').show();
      }
   });
   if(!$('#registration_registration_consumerfalse').is(":checked")) {
	   $('#wwgrp_registration_registration_firm').hide();
	   $('#registration_registration_consumertrue').prop('checked', true);
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
            <h3><struts:property value="course.getName(getText('language'))" /> (<struts:property value="course.number" />)</h3>
          </div>

          <div class="specifications">
            <div class="image">
              <img id="image" alt="<struts:property value="course.getName(getText('language'))" />" src="/image/pictureitem/<struts:property value="course.uuid" />/thumbnail.png">
            </div>
            <div class="descriptionbox">
              <div class="description">
                <p id="description">
                  <struts:property value="course.getDescription(getText('language'))" />
                </p>
              </div>
              <div class="startdate">
                <p id="startdate">
                  beginnt am <struts:date name="course.startDate" format="dd.MM.yyyy" />
                </p>
              </div>
            </div>
          </div>

          <div class="price">
            <div class="number"><struts:property value="getText('{0,number,#,##0.00}',{course.price})"/> &euro;</div>
            <div class="unit">pro Teilnehmer</div>
            <struts:hidden key="course.price" />
          </div>

        </fieldset>

        <struts:if test="course.material">
        <fieldset class="fieldset" id="material">

          <div class="field">
            <struts:checkbox key="registration.listOfMaterials" theme="tivpage" onchange="calculateSum(this)" value="true"/>
            <script type="text/javascript">
            function calculateSum(element) {
          	  if(element.checked) {
          		  var material = parseFloat(($('#registration_course_materialPrice').val()).replace(",","."));
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
            <div class="number"><struts:property value="getText('{0,number,#,##0.00}',{course.materialPrice})"/> &euro;</div>
            <div class="unit">pro Teilnehmer</div>
            <struts:hidden key="course.materialPrice" />
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
          <p id="sum"><struts:property value="getText('{0,number,#,##0.00}',{course.sum})"/> &euro;</p>
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
        <struts:reset type="button" value="Löschen" />
      </div>

      <hr>
    </struts:form>
  
    <hr>
  </div>
  <!-- Content Ende -->

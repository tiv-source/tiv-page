<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>




            <fieldset class="fieldset">

              <div class="field">
                <label for="reservation.gender" class="label">Anrede:</label>
                <struts:if test="reservation.gender">
                  Frau
                </struts:if>
                <struts:else>
                  Herr
                </struts:else>
              </div>

              <div class="field">
                <label for="reservation.firstname" class="label">Vorname:</label>
                <struts:property value="reservation.firstname"/>
              </div>

              <div class="field">
                <label for="reservation.lastname" class="label">Nachname:</label>
                <struts:property value="reservation.lastname"/>
              </div>

              <div class="field">
                <label for="reservation.email" class="label">Mail:</label>
                <struts:property value="reservation.email"/>
              </div>

              <div class="field">
                <label for="reservation.telephone" class="label">Telefon:</label>
                <struts:property value="reservation.telephone"/>
              </div>

              <div class="field">
                <label for="reservation.quantity" class="label">Personen:</label>
                <struts:property value="reservation.quantity"/>
              </div>

              <div class="field">
                <label for="reservation.time" class="label">Uhrzeit:</label>
                <struts:property value=""/>
                <struts:date name="reservation.time" format="HH:mm" />
              </div>

              <div class="field">
                <label for="reservation.wishes" class="label">Wünsche:</label>
                <div style="padding-left: 154px; padding-bottom: 14px;"><struts:property value="reservation.wishes" escape="false" /></div>
              </div>

              <div class="field">
                <label for="reservation.created" class="label">Erstellt am:</label>
                <struts:property value="reservation.created"/>
              </div>

              <div class="field">
                <label for="reservation.createdAddress" class="label">Erstellt IP:</label>
                <struts:property value="reservation.createdAddress"/>
              </div>

              <div class="field">
                <label for="reservation.confirmed" class="label">Bestätigt:</label>
                <struts:property value="reservation.confirmed"/>
              </div>

              <div class="field">
                <label for="reservation.confirmedBy" class="label">Bestätigt von:</label>
                <struts:property value="reservation.confirmedBy"/>
              </div>

              <div class="field">
                <label for="reservation.confirmedAddress" class="label">Bestätigt IP:</label>
                <struts:property value="reservation.confirmedAddress"/>
              </div>

              <div class="field">
                <label for="reservation.confirmedDate" class="label">Bestätigt am:</label>
                <struts:property value="reservation.confirmedDate"/>
              </div>

              <div class="field">
                <label for="reservation.modifiedBy" class="label">Bearbeitet von:</label>
                <struts:property value="reservation.modifiedBy"/>
              </div>

              <div class="field">
                <label for="reservation.modifiedAddress" class="label">Bearbeitet IP:</label>
                <struts:property value="reservation.modifiedAddress"/>
              </div>

              <div class="field">
                <label for="reservation.modified" class="label">Bearbeitet am:</label>
                <struts:property value="reservation.modified"/>
              </div>

              <div class="field">
                <label for="reservation.origin" class="label">Weg:</label>
                <struts:property value="reservation.origin"/>
              </div>

            </fieldset>



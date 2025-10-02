<%@page pageEncoding="utf-8" contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjm" uri="/struts-jquery-mobile-tags"%>

      <s:form 
        id="checkboxlist_form1" 
        action="edit" 
        theme="simple"
      >

        <sjm:radio
            id="reservation.gender"
            name="reservation.gender"
            label="Anrede"
            horizontal="true"
            list="#{true:'Frau',false:'Herr'}"
        />

        <sjm:textfield
            id="reservation.firstname"
            name="reservation.firstname"
            label="Vorname"
        />

        <sjm:textfield
            id="reservation.lastname"
            name="reservation.lastname"
            label="Nachname"
        />

        <sjm:textfield
            id="reservation.email"
            name="reservation.email"
            label="E-mail"
        />

        <sjm:textfield
            id="reservation.telephone"
            name="reservation.telephone"
            label="Telefon"
        />

        <sjm:slider
            id="reservation.quantity"
            name="reservation.quantity"
            label="Personen"
            min="1"
            max="30"
        />

        <sjm:textarea
            id="reservation.wishes"
            name="reservation.wishes"
            label="Wünsche"
        />


        <sjm:a
            id="checkboxlist_form_link1"
            formIds="checkboxlist_form1"
            targets="checkboxlist_form_result_1"
            button="true"
            buttonIcon="gear"
        >
   		    Submit
        </sjm:a>
       
      </s:form>	
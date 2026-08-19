package de.tivsource.page.helper.pdf;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.tivsource.page.common.country.Country;
import de.tivsource.page.common.country.description.CountryDescription;
import de.tivsource.page.common.salutation.Salutation;
import de.tivsource.page.common.salutation.description.SalutationDescription;
import de.tivsource.page.entity.course.Course;
import de.tivsource.page.entity.enumeration.Language;
import de.tivsource.page.entity.namingitem.Description;
import de.tivsource.page.entity.registration.Registration;

public class TestConfirmationPDF {

    public static void main(String[] args) throws IOException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        Calendar pdfCreationDate = Calendar.getInstance();
        Calendar pdfModificationDate = Calendar.getInstance();
        pdfCreationDate.setTime(new Date());
        pdfModificationDate.setTime(new Date());

        File file = new File("target/ConfirmationPDF.pdf");

        // Anrede
        SalutationDescription salutationDescription = new SalutationDescription();
        salutationDescription.setName("Herr");
        salutationDescription.setLocale(Language.DE);
        Map<Language, SalutationDescription> salutationDescriptionMap = new HashMap<Language, SalutationDescription>();
        salutationDescriptionMap.put(Language.DE, salutationDescription);
        Salutation salutation = new Salutation();
        salutation.setDescriptionMap(salutationDescriptionMap);

        // Land
        CountryDescription countryDescription = new CountryDescription();
        countryDescription.setName("Deutschland");
        countryDescription.setLocale("de");
        Map<String, CountryDescription> countryDescriptionMap = new HashMap<String, CountryDescription>();
        countryDescriptionMap.put("de", countryDescription);
        Country country = new Country();
        country.setDescriptionMap(countryDescriptionMap);

        // DocumentData
        String[] documentData = new String[7];
        documentData[0] = "T.I.V. Consulting GmbH - Auf dem Schnee 29 - 58454 Witten";// Adressfeld Überschrift
        documentData[1] = "Datum";// Überschrift Datum
        documentData[2] = simpleDateFormat.format(pdfModificationDate.getTime());// Datum
        documentData[3] = "Auftrags-Nr.";// Überschrift Auftrags-Nummer
        documentData[4] = "Seiten-Nr.";// Überschrift Seitennummer
        documentData[5] = "1 von 1";// Seitennummer
        
        // DocumentInformation
        String[] documentInformation = new String[6];
        documentInformation[0] = "Marc Michele";                                          // Author
        documentInformation[1] = "T.I.V. Consulting GmbH";                                // Creator
        documentInformation[2] = "Anmeldung, Auftragsbestätigung, Kurs, TIV-Page";        // Keywords
        documentInformation[3] = "TIV-Page";                                              // Producer
        documentInformation[4] = "Auftragsbestätigung";                                   // Subject
        documentInformation[5] = "Auftragsbestätigung";                                   // Title

        // DocumentFooter
        String[][] documentFooter = new String[5][4];
        documentFooter[0][0] = "Anschrift";                  // Block 1 Feld 1
        documentFooter[0][1] = "TIV Consulting GmbH";        // Block 1 Feld 2
        documentFooter[0][2] = "Auf dem Schnee 29";          // Block 1 Feld 3
        documentFooter[0][3] = "58454 Witten";               // Block 1 Feld 4
        documentFooter[1][0] = "Kontakt";                    // Block 2 Feld 1
        documentFooter[1][1] = "Tel.: 02302 / 8798783";      // Block 2 Feld 2
        documentFooter[1][2] = "info@tiv-consulting.de";     // Block 2 Feld 3
        documentFooter[1][3] = "www.tiv-consulting.de";      // Block 2 Feld 4
        documentFooter[2][0] = "Bankverbindung";             // Block 3 Feld 1
        documentFooter[2][1] = "APOBANK";                    // Block 3 Feld 2
        documentFooter[2][2] = "IBAN";                       // Block 3 Feld 3
        documentFooter[2][3] = "DE02 3006 0601 0002 4746 89";// Block 3 Feld 4
        documentFooter[3][0] = "Registergericht Bochum";     // Block 4 Feld 1
        documentFooter[3][1] = "HRB XXXXX";                  // Block 4 Feld 2
        documentFooter[3][2] = "Steuernummer";               // Block 4 Feld 3
        documentFooter[3][3] = "XXX/XXXX/XXXX";              // Block 4 Feld 4
        documentFooter[4][0] = "Geschäftsführung";           // Block 5 Feld 1
        documentFooter[4][1] = "Marc Michele";               // Block 5 Feld 2
        documentFooter[4][2] = "Ust-IdNr.";                  // Block 5 Feld 3
        documentFooter[4][3] = "DExxxxxxxxx";                // Block 5 Feld 4

        // BankData
        String[] bankData = new String[10];
        bankData[0] = "Bitte verwenden Sie folgende Bankdaten für die Bezahlung Ihrer Anmeldung:";// Title
        bankData[1] = "IBAN:";                      // Überschrift IBAN
        bankData[2] = "DE02 3006 0601 0002 4746 89";// IBAN
        bankData[3] = "BIC:";                       // Überschift BIC
        bankData[4] = "DAAEDEDD";                   // BIC
        bankData[5] = "Bank:";                      // Überschrift Bank
        bankData[6] = "APOBANK";                    // Bank
        bankData[7] = "Empfänger:";                 // Empfänger überschrift
        bankData[8] = "T.I.V. Consulting GmbH";     // Empfänger
        bankData[9] = "Verwendungszweck:";          // Überschrift Verwendungszweck

        // Kurs
        Description courseDescription = new Description();
        courseDescription.setName("Name des Kurses");
        courseDescription.setLanguage(Language.DE);
        Map<Language, Description> courseDescriptionMap = new HashMap<Language, Description>();
        courseDescriptionMap.put(Language.DE, courseDescription);
        Course course = new Course();
        course.setDescriptionMap(courseDescriptionMap);
        course.setNumber("AB5252552");
        course.setCourseDocumentDescription("Kursgebür für Kurs: Hier gibt es eine sehr langen Kursnamen damit wir sehen wie das mit dem umbruch geht (AB5252552)");
        course.setPrice(new BigDecimal("159.95"));
        course.setPriceTax(new BigDecimal("19"));
        course.setMaterialDocumentDescription("Materialkosten für den Kurs für den für den für den für den für den für den für den für den");
        course.setMaterial(true);
        course.setMaterialPrice(new BigDecimal("149.95"));


        Registration registration = new Registration();

        // Firmenkunde (Ja/Nein)
        registration.setConsumer(false);
        registration.setFirm("Firma GmbH");

        // Anrede
        registration.setSalutation(salutation);
        registration.setFirstname("Max");
        registration.setLastname("Mustermann");

        // Adresse
        registration.setStreet("Bahnhofstrasse 1");
        registration.setZip("44xxx");
        registration.setCity("Dortmund");
        registration.setCountry(country);
        registration.setOrderConfirmationNumber("AUFTRAG-4230002");

        registration.setCourse(course);
        registration.setListOfMaterials(true);
        
        new CreateConfirmationPDF(
                file,
                registration,
                pdfCreationDate,
                pdfModificationDate,
                new File("testfiles/invoice_header_logo.png"),
                new File("testfiles/fonts/arial.ttf"),
                new File("testfiles/fonts/arialbd.ttf"),
                documentInformation,
                documentData,
                documentFooter,
                bankData
        );

    }

}// Ende class

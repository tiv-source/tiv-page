package de.tivsource.page.helper;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Werkzeugklasse um Passwörter zu hashen und Passwörter zu überprüfen. Die
 * Passwörter werden gesalzen und gehasht. Für das hashen wird der Algorithmus
 * PBKDF2WithHmacSHA512 benutzt. Das gehashte Ergebnis hat 512 bit.
 */
public class Passwords {

    /**
     * Logger der Klasse.
     */
    private static final Logger LOGGER = LogManager.getLogger("AuthLogger");

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 7777;
    private static final int KEY_LENGTH = 512;

    /**
     * Statische Werkzeugklasse, der Konstruktor ist leer.
     */
    private Passwords() {
    }

    /**
     * Liefert ein zufällig erzeugtes Salz das zum hashen des Passwortes benutzt
     * wird.
     *
     * @return 32 byte zufälliges Salz
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[32];
        RANDOM.nextBytes(salt);
        return salt;
    }

    /**
     * Liefert ein gesalzenes und gehashtes Passwort zurück (PBKDF2WithHmacSHA512).
     * Achtung: das Passwort wird gelöscht und durch Nullen überschrieben.
     *
     * @param password - das Passwort das gehashed werden soll
     * @param salt - das Salz mit dem das a Passwort gesalzen werden soll
     *
     * @return Das gesalzene und gehashte Passwort
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Es ist folgender Fehler wären der Hashwerterstellung aufgetreten: "
                    + e.getMessage(), e);
        } finally {
            pbeKeySpec.clearPassword();
        }
    }

    /**
     * Methode zum vergleichen eines angegeben Passwortes und Salzes mit dem
     * angegeben Hash, die Methode liefert true wenn das sich aus dem Passwort
     * und dem Salz der angegeben Hash berechnen lässt ansonsten liefert die
     * Methode false.
     * Achtung: das Passwort wird gelöscht und durch Nullen überschrieben.
     *
     * @param password - das Passwort das getestet werden soll
     * @param salt - das Salz mit dem das a Passwort gesalzen werden soll
     * @param expectedHash - Erwarteter Hashwert der zum Passwort und Salz passen soll
     *
     * @return true wenn das sich aus dem Passwort und dem Salz der angegeben Hash berechnen läst, ansonsten false
     */
    public static boolean isExpectedPassword(char[] password, byte[] salt,
            byte[] expectedHash) {
        LOGGER.info("isExpectedPassword aufgerufen");
        byte[] passwordHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (passwordHash.length != expectedHash.length) {
            return false;
        }
        for (int i = 0; i < passwordHash.length; i++) {
            if (passwordHash[i] != expectedHash[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generiert ein zufälliges Passwort mit der angegebenen Länge, dazu werden
     * Buchstaben und Zahlen benutzt.
     *
     * @param length - Die Länge des Passwortes
     *
     * @return Das zufällige Passwort
     */
    public static String generateRandomPassword(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int c = RANDOM.nextInt(62);
            if (c <= 9) {
                stringBuilder.append(String.valueOf(c));
            } else if (c < 36) {
                stringBuilder.append((char) ('a' + c - 10));
            } else {
                stringBuilder.append((char) ('A' + c - 36));
            }
        }
        return stringBuilder.toString();
    }

}// Ende class



package com.example.lease_management.email;




import java.util.Properties;

public class EmailSender {
    public static void sendEmail( String toEmail, String subject, String body){

        final String fromEmail= "katarzyna.bogdan@buziaczek.pl";
        final String password ="Kasia1988";

        Properties props = new Properties();// obiekt Properties, który pozwala przechowywać ustawienia serwera SMTP (czyli dane potrzebne do wysyłania e-maili).
        props.put("mail.smtp.host", "smtp.poczta.onet.pl"); //
        props.put("mail.smtp.port", "587"); //Ustawiamy port, przez który będzie odbywała się komunikacja z serwerem SMTP. Port 587 oznacza, że używamy szyfrowania TLS.
        props.put("mail.smtp.auth", "true"); // Włączamy opcję uwierzytelniania. To oznacza, że serwer SMTP wymaga podania loginu i hasła (Twojego adresu e-mail i hasła), aby wysłać wiadomość.
        props.put("mail.smtp.starttls.enable", "true"); //Włączamy opcję TLS, która zapewnia bezpieczne połączenie z serwerem SMTP.


    }




}

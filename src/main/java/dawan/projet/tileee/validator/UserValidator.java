package dawan.projet.tileee.validator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import dawan.projet.tileee.bean.User;
import dawan.projet.tileee.dao.GenericDao;
import dawan.projet.tileee.dao.UserDao;
import dawan.projet.tileee.tool.StringFunctions;

public class UserValidator extends GenericDao {

	private String bdd;
	
    public UserValidator(String bdd) {
		super(bdd);
		this.bdd = bdd;
		// TODO Auto-generated constructor stub
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean eMailValidate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }


    public String userValidator(String login, String password, boolean validation){
        String message = "";
        if(password.equals("")) {
            message += "password|";
        }
        if(login.equals("")) {
            message += "loginNotNull|";
        }
        
         
        if(!validation) { 
        	message += "noValidationForThisUser";
        }
        

        
        
        
        try {
        	UserDao userdao = new UserDao(bdd);
            Boolean isMatches = userdao.pswAndLoginMatche(login, password, true);
            if(!isMatches)
                message += "LoginAndPasswordNotCorrespondant";
        } catch(Exception e) {

        }
        //System.out.println("je retourne message = " + message);
        return message;
    }

    public String userValidator(User user, String passwordConfirmation){
        String message = "";
        if(user.getLogin().isEmpty())
            message += "forename";
        if(user.getPassword().isEmpty())
            message += "password";
        if(user.getMail().isEmpty())
            message += "emailNotNull";
        if(!eMailValidate(user.getMail())) {
            message += "invalidEmail";
        } else {
            try {
            	UserDao userdao = new UserDao(bdd);
                Boolean isExist = userdao.doesEmailExist(user.getMail(), true);
                if(isExist) {
                    message += "alreadyExistMail";
                }
            } catch(Exception e){
                System.out.println("IMPOSSIBLE D'ETABLIR LA CONNEXION");
            }
        }
        if(!user.getPassword().equals(passwordConfirmation))
            message += "errorPasswordConfirmation";
        return message;
    }
    
	public static boolean isEmailAddress(String email){
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
		Matcher m = p.matcher(email.toUpperCase());
		return m.matches();
	}

	/**
	 * Configure un serveur d'envoi de courriel avec pi�ce jointe)
	 * @param email
	 * Objet Java MultiPartEmail qui permet d'envoyer des courriels avec pi�ce jointe
	 * @param subject
	 * Sujet de l'email
	 * @param htmlContent
	 * Corps de l'email
	 * @param to
	 * Adresse email du destinataire de l'email
	 * @return
	 * Un objet MultiPartEmail
	 * @throws Exception
	 */
	private static MultiPartEmail configureIMAP( MultiPartEmail email, Object subject, Object htmlContent, String to ) throws Exception {
		email.setCharset( "utf-8" );
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort( 465 );
		email.setAuthenticator(new DefaultAuthenticator("poefrancetest@gmail.com", "Madagascar75014@"));
		email.setSSLOnConnect(true);
		email.setFrom( "DAWAN <hrabesiaka@gmail.com>" );
		email.setSubject( subject.toString() );
		email.setMsg( htmlContent.toString() );
		email.addTo( to );
		return email;
	}
	
	/**
	 * Configure un serveur d'envoi de courriel sans pi�ce jointe)
	 * @param email
	 * Objet Java MultiPartEmail qui permet d'envoyer des courriels avec pi�ce jointe
	 * @param subject
	 * Sujet de l'email
	 * @param htmlContent
	 * Corps de l'email
	 * @param to
	 * Adresse email du destinataire de l'email
	 * @return
	 * Un objet MultiPartEmail
	 * @throws Exception
	 */
	private static Email configureIMAP( Email email, Object subject, Object htmlContent, String to ) throws Exception {
		email.setCharset( "utf-8" );
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort( 465 );
		email.setAuthenticator(new DefaultAuthenticator("poefrancetest@gmail.com", "Madagascar75014@"));
		email.setSSLOnConnect(true);
		email.setFrom( "DAWAN <hrabesiaka@gmail.com>" );
		email.setContent("Content-Type", "text/html; charset=UTF-8");
		email.setSubject( subject.toString() );
		email.setMsg( htmlContent.toString() );
		email.addTo( to );
		return email;
	}
	
	
	private static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static String hashPassword(String password) {
		String hexHashedPassword = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] psw = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			hexHashedPassword = bytesToHex(psw);
		} catch (Exception e) {

		}
		return hexHashedPassword;
	}

//	public static String attributeTitle(String pageTitle) {
//		String result = pageTitle;
//		Properties p = new Properties();
//		try {
//			p.load(Thread.currentThread().getContextClassLoader()
//					.getResourceAsStream("resources/application.properties"));
//			String dawanTitle = p.getProperty("title");
//			if (!"".equals(pageTitle))
//				result = LibraryDao.reduce(pageTitle, 20) + " - " + dawanTitle;
//			else
//				result = dawanTitle;
//		} catch (Exception e) {
//
//		}
//		return result;
//	}
	
	public static String hash(String word) {

		// Cryptage du mot de passe
		String hash = "";
		for (int i = word.length() - 1; i >= 0; i--) {
			hash += Integer.toString((byte) ((int) word.charAt(i) / 16 * 10 + (int) word.charAt(i) % 16));
		}
		return hash;
	}

	public void sendEmail(String from, String to, String subject, String htmlContent, String attachmentUrl,
			String name, String description) throws Exception {

		attachmentUrl = "";
		
		// pi�ce jointe : attachmentUrl
		try {

			if (!attachmentUrl.equals("")) {
				// Avec pi�ce jointe
				MultiPartEmail email = new MultiPartEmail();
				configureIMAP(email, StringFunctions.utf8Encode(subject), StringFunctions.utf8Encode(htmlContent), to);
				EmailAttachment attachement = new EmailAttachment(); // Objet Java pi�ce jointe
				attachement.setPath(attachmentUrl); // Chemin et nom du fichier
				attachement.setDisposition(EmailAttachment.ATTACHMENT);
				attachement.setDescription(description); // description de l'image
				email.addHeader("Content-Type", "text/html; charset=UTF-8"); // Pour que les balises HTML soient ex�cut�es mais pas affich�es
				attachement.setName(name); // le nom du fichier quand le destinataire enregistre la pi�ce
				email.attach(attachement); // attacher la pi�ce � l'email
				email.send();
			} else {
				// Sans pi�ce jointe
				Email email = new SimpleEmail();
				configureIMAP(email, StringFunctions.utf8Encode(subject.toString()),
						StringFunctions.utf8Encode(htmlContent.toString()), to);
				email.addHeader("Content-Type", "text/html; charset=UTF-8"); // Pour que les balises soient ex�cut�es mais pas affich�es
				email.setFrom("DAWAN <hrabesiaka@gmail.com>");
				email.send();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}


/**
 * 
 */
package de.tivsource.page.admin.security;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.tivsource.page.dao.administration.UserDaoLocal;
import de.tivsource.page.entity.administration.Role;
import de.tivsource.page.entity.administration.User;

/**
 * @author Marc Michele
 *
 */
public class AdminLoginModule implements LoginModule {

	private static final Logger LOGGER = LogManager.getLogger("AuthLogger");
	
	private CallbackHandler handler;
	private Subject subject;
	private User userPrincipal;
	private List<Role> rolePrincipals;
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		handler = callbackHandler;
		this.subject = subject;
	}// Ende initialize()

	@Override
	public boolean login() throws LoginException {
	    Callback[] callbacks = new Callback[2];
	    callbacks[0] = new NameCallback("login");
	    callbacks[1] = new PasswordCallback("password", true);


	    try {
	      handler.handle(callbacks);
	      String name = ((NameCallback) callbacks[0]).getName();
	      String password = String.valueOf(((PasswordCallback) callbacks[1]).getPassword());

	      // Überprüfen ob der Name und
	      // das Passwort gesetzt wurden
	      if (name != null &&
	          password != null) {

	    	  Context initialContext = new InitialContext();
	    	  UserDaoLocal userDaoLocal = (UserDaoLocal) initialContext.lookup("java:global/tiv-page/dao-0.0.1/UserDao");
	    	  
	    	  // Versuche benutzer mit dem Namen aus der Datenbank zu holen.
	    	  User dbUser = userDaoLocal.findByUsername(name);

	    	  // Überprüfen ob ein Benutzer gefunden wurde
	    	  // und ob die Daten stimmen.
	    	  if(dbUser != null &&
	    	     name.equals(dbUser.getName()) && 
	    	     password.equals(dbUser.getPassword())) {
	    		  LOGGER.info("Auth-User: " + dbUser.getUsername());
	    		  userPrincipal = dbUser;
	    		  return true;
	    	  }// Ende if

	      }// Ende if
	      
	      // If credentials are NOT OK we throw a LoginException
	      throw new LoginException("Authentication failed");

	    } catch (UnsupportedCallbackException e) {
	      throw new LoginException(e.getMessage());
	    } catch (NamingException e) {
	      throw new LoginException(e.getMessage());
		} catch (NoResultException e) {
			LOGGER.info("Login failed");
			LOGGER.info("Auth-User: " + ((NameCallback) callbacks[0]).getName());
			LOGGER.info("Auth-Password: " + String.valueOf(((PasswordCallback) callbacks[1]).getPassword()));
			return false;			
		} catch (IOException e) {
			throw new LoginException(e.getMessage());
		} 
	}

	@Override
	public boolean commit() throws LoginException {
	    subject.getPrincipals().add(userPrincipal);
	    rolePrincipals = userPrincipal.getRoles();

	    for (Role roleName : rolePrincipals) {
	    	subject.getPrincipals().add(roleName);
	    }
	    return true;
	}

	@Override
	public boolean abort() throws LoginException {
		return false;
	}

	@Override
	public boolean logout() throws LoginException {
		subject.getPrincipals().remove(userPrincipal);
	    subject.getPrincipals().remove(rolePrincipals);
	    return true;
	}

}// Ende class

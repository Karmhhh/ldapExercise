package com.example.ldapExercise;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.NamingEnumeration;
import javax.naming.directory.*;

import java.util.Hashtable;

@SpringBootApplication
public class LdapExerciseApplication {

	public static void main(String[] args) {
		// Configurazione del server LDAP
		String ldapUrl = "ldap://localhost:389";
		String baseDn = "dc=example,dc=com";
		String username = "cn=admin,dc=example,dc=com";
		String password = "adminpassword";

		// Configurazione delle proprietà JNDI
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, username);
		env.put(Context.SECURITY_CREDENTIALS, password);

		try {
			// Connessione al server LDAP
			DirContext ctx = new InitialDirContext(env);
			System.out.println("Connessione LDAP avvenuta con successo!");

			// Creazione di una query
			String searchFilter = "(objectClass=inetOrgPerson)";
			String[] requiredAttributes = { "cn", "sn", "mail" };
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchControls.setReturningAttributes(requiredAttributes);

			// Esecuzione della ricerca
			NamingEnumeration<SearchResult> results = ctx.search(baseDn, searchFilter, searchControls);

			// Elaborazione dei risultati
			while (results.hasMore()) {
				SearchResult result = results.next();
				Attributes attrs = result.getAttributes();
				System.out.println("DN: " + result.getNameInNamespace());
				for (String attr : requiredAttributes) {
					Attribute attribute = attrs.get(attr);
					if (attribute != null) {
						System.out.println(attr + ": " + attribute.get());
					}
				}
				System.out.println("---------------------------");
			}

			// Chiusura del contesto
			ctx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

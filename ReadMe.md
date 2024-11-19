# Guida Completa: Configurazione e Uso di LDAP con Java

## Passo 1: Configurazione di un Server LDAP Locale

### Opzione 1: Usare un Docker Container per OpenLDAP
Il modo più semplice per configurare un server LDAP rapidamente è utilizzare Docker.

**Comandi:**

1. Scarica e avvia un container LDAP:
   ```bash
   docker run --rm -d \
       --name openldap \
       -p 389:389 -p 636:636 \
       -e LDAP_ORGANISATION="Example Corp" \
       -e LDAP_DOMAIN="example.com" \
       -e LDAP_ADMIN_PASSWORD="adminpassword" \
       osixia/openldap:latest


2. Avvia il container di phpLDAPadmin per la gestione grafica:
docker run --rm -d \
    --name phpldapadmin \
    -p 6443:443 \
    --link openldap:ldap-host \
    -e PHPLDAPADMIN_LDAP_HOSTS=ldap-host \
    osixia/phpldapadmin:latest

3. Accedi a https://localhost:6443 per gestire LDAP graficamente.


markdown
Copia codice
# Guida Completa: Configurazione e Uso di LDAP con Java

## Passo 1: Configurazione di un Server LDAP Locale

### Opzione 1: Usare un Docker Container per OpenLDAP
Il modo più semplice per configurare un server LDAP rapidamente è utilizzare Docker.

**Comandi:**

1. Scarica e avvia un container LDAP:
   ```bash
   docker run --rm -d \
       --name openldap \
       -p 389:389 -p 636:636 \
       -e LDAP_ORGANISATION="Example Corp" \
       -e LDAP_DOMAIN="example.com" \
       -e LDAP_ADMIN_PASSWORD="adminpassword" \
       osixia/openldap:latest
Avvia il container di phpLDAPadmin per la gestione grafica:

bash
Copia codice
docker run --rm -d \
    --name phpldapadmin \
    -p 6443:443 \
    --link openldap:ldap-host \
    -e PHPLDAPADMIN_LDAP_HOSTS=ldap-host \
    osixia/phpldapadmin:latest
Accedi a https://localhost:6443 per gestire LDAP graficamente.

### Opzione 2: Installare Manualmente OpenLDAP
Linux
Installa OpenLDAP:

bash
Copia codice
sudo apt update
sudo apt install slapd ldap-utils
Configura il server LDAP:

bash
Copia codice
sudo dpkg-reconfigure slapd
Windows/MacOS
Usa strumenti alternativi come Apache Directory Server o LdapAdmin per configurare un server LDAP.

## Passo 2: Aggiungere Dati al Server LDAP
### Creare un File LDIF per Aggiungere Utenti chiamato data.ldif con il seguente contenuto:

dn: dc=example,dc=com
objectClass: top
objectClass: dcObject
objectClass: organization
o: Example Corp
dc: example

dn: ou=People,dc=example,dc=com
objectClass: organizationalUnit
ou: People

dn: ou=Groups,dc=example,dc=com
objectClass: organizationalUnit
ou: Groups

dn: cn=John Smith,ou=People,dc=example,dc=com
objectClass: inetOrgPerson
sn: Smith
cn: John Smith
mail: john.smith@example.com
uid: jsmith
userPassword: password123

dn: cn=Admins,ou=Groups,dc=example,dc=com
objectClass: groupOfNames
cn: Admins
member: cn=John Smith,ou=People,dc=example,dc=com

### Caricare i Dati nel Server LDAP tramite inteffaccia effettuando i login a ldap con dm e pass 
{
    dm: cn=admin,dc=example,dc=com,
   psw: adminpassword
}

Ldap ora è pronto per essere utilizzato.
usa le seguenti librerie per interagirci:

- javax.naming
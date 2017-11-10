import jenkins.model.*
import hudson.security.*
import org.jenkinsci.plugins.*
import jenkins.security.plugins.ldap.LDAPConfiguration
import hudson.util.Scrambler
import hudson.util.Secret;

String server = '%uri%'
String rootDN = ''
String userSearchBase = 'ou=,o=,dc=,dc='
String userSearch = 'uid={0}'
String groupSearchBase = 'ou=,o=,dc=,dc='
String managerDN = 'uid=%ldap_user%,ou=,o=,dc=,dc='
String managerPassword = '%user_password%'
boolean inhibitInferRootDN = false
boolean disableMailAddressResolver = false

int cache_size = 50
int ttl = 3600

LDAPSecurityRealm.CacheConfiguration cache = new LDAPSecurityRealm.CacheConfiguration(cache_size,ttl)

LDAPConfiguration ldap_config = new LDAPConfiguration(server, rootDN, inhibitInferRootDN, managerDN, Secret.fromString(managerPassword))
ldap_config.setUserSearchBase(userSearchBase);
ldap_config.setUserSearch(userSearch);
ldap_config.setGroupSearchBase(groupSearchBase);

SecurityRealm ldap_realm = new LDAPSecurityRealm(
    [ldap_config],
    false,
    cache,
    IdStrategy.CASE_INSENSITIVE,
    IdStrategy.CASE_INSENSITIVE)

Jenkins.instance.setSecurityRealm(ldap_realm)
Jenkins.instance.save()

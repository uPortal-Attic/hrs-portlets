# ReadMe

## Continuous Integration Status

This project uses [Travis-CI.org][] for continuous integration.  This means that every commit to `master` and every
Pull Request is automatically built and the unit tests are automatically run.  The below badge shows the current status
*of building the `master` branch* (neat for display on [the github page][], less interesting if you're reading this
README locally. :) )

![Build Status](https://travis-ci.org/Jasig/hrs-portlets.png?branch=master)


## Local Setup Instructions

### Property files

Several property files need to be configured for your local environment before the Portlet will run in your local uPortal server.

* /hrs-portlets-webapp/src/main/resources/logback.xml
	* Update paths in file to where HRS portlet should write it's log files to.
* /hrs-portlets-bnsemail-impl/src/mail/resources/
	* Copy EXAMPLE_bnsemail-placeholder.properties to **bnsemail-placeholder.properties**
	* Copy **smime-keystore.jks** into directory
	* Do Not add bnsemail-placeholder.properties OR smime-keystore.jks to source control (should be automatically ignored via local .gitignore)
* /hrs-portlets-cypress-impl/src/mail/resources/
	* Copy EXAMPLE_cypress-placeholder.properties to **cypress-placeholder.properties**
	* Do Not add cypress-placeholder.properties to source control (should be automatically ignored via local .gitignore)
* /hrs-portlets-ps-impl/src/mail/resources/
	* Copy EXAMPLE_ps-placeholder.properties to **ps-placeholder.properties**
	* Do Not add ps-placeholder.properties to source control (should be automatically ignored via local .gitignore)

## Declaring the employee-id-conveying user attribute

The HRS Portlets need the logged in user's identifier for communicating about the user with the backing HRS systems.  The portlets get this identifier from the portal as one or more user attributes.  You'll need to both tell the portlets what user identifier to use and tell the portal to release those user attributes to the portlets.

### Telling the portlet what attribute to use

There's a `emplidUserAttributes` portlet preference, multi-valued, the values of which are the names of user attributes the portlet should look in to resolve emplId.  The demo default simply uses `user.login.id` which should always be available.  

That's probably not your HRS identifier though.  The University of Wisconsin-Madison's value for this preference looks more like this to cope with the multi-campus nature of the portal:

    <portlet-preferences>
      <preference>
        <name>emplidUserAttributes</name>

        <!-- the value or values here should be the user attributes providing HRS system user identifiers -->
        <value>eduWisconsinHRSEmplID</value>
        <value>eduWisconsinHRPersonID</value>
        <value>wiscEduHRSEmplid</value>
        <value>wisceduhrpersonid</value>
        
      </preference>
    </portlet-preferences>

(Ideally your portal configuration and identity management processes will digest this down to one simple attribute for your portal to feed to your portlets.)

You'll need to do this within *each* portlet declaration in `portlet.xml`, since each of the portlets uses this preference to figure out how to resolve the HRS employee identifier.

### Telling the portal to release the attributes to the portlet

Also in `portlet.xml` you'll need to declare the user attributes relied upon by your portlets.  These will be the user attributes you just told the portlets to look at in setting that `emplidUserAttributes` preference.

The University of Wisconsin-Madison's user-attribute declarations are something like this:

    <user-attribute>
      <name>eduWisconsinHRSEmplID</name>
     </user-attribute>
     <user-attribute>
       <name>eduWisconsinHRPersonID</name>
     </user-attribute>
     <user-attribute>
       <name>wiscEduHRSEmplid</name>
     </user-attribute>
     <user-attribute>
       <name>wisceduhrpersonid</name>
     </user-attribute>

Again, ideally carefully considered identity and portal management will have pre-digested this down to one simple attribute to feed to this portlet so you'll only be declaring one `user-attribute`.





[Travis-CI.org]: https://travis-ci.org/Jasig/hrs-portlets
[the github page]: https://github.com/Jasig/hrs-portlets

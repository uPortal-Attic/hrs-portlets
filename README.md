## ReadMe

### Local Setup Instructions

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

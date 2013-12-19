## ReadMe

### Continuous Integration Status

This project uses [Travis-CI.org][] for continuous integration.  This means that every commit to `master` and every
Pull Request is automatically built and the unit tests are automatically run.  The below badge shows the current status
*of building the `master` branch* (neat for display on [the github page][], less interesting if you're reading this
README locally. :) )

![Build Status](https://travis-ci.org/Jasig/hrs-portlets.png?branch=master)


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


[Travis-CI.org]: https://travis-ci.org/Jasig/hrs-portlets
[the github page]: https://github.com/Jasig/hrs-portlets

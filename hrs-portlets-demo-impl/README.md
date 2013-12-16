# A demo implementation

This module contains a demo implementation of each of the interfaces declared in the `hrs-portlets-api` module.

# Approach

The demo implementations largely generate random data the first time they are asked about a user, remember that data
in-memory, and parrot it back again whenever they're asked again.  This way you get interesting demo data but it
doesn't chaotically change during a portal usage.

# Structure

The package `edu.wisc.hr.demo` contains only implementations of the `hrs-portlets-api` APIs (DAOs, mostly).

The package `edu.wisc.hr.demo.support` contains helper classes (random generators, mostly) that do not directly
implement `hrs-portlets-api` APIs but that are used by the classes that do implement those APIs.

# Unit tests

There are a few true unit tests.

The unit tests are also containers for utility methods for printing examples.  The non-test just-printing methods are
 marked `@Ignore` so that they don't add noise to routine run of the unit tests (in say the `mvn test` lifecycle
 step).  To print you switch those @Ignore annotations to @Test and then either run the tests via `mvn test` or run
 the tests via your favorite IDE.

# What good is this?

You can use this demo implementation as an alternative to the live API implementation .jars in the HRS Portlet so as
to exercise, demonstrate, and develop the portlet without necessarily having access to suitable systems exposing the
data you want to work with.

# Shortcomings

There are several places where the demo implementation is vague as to the meaning of various names, statuses, titles,
 and other interesting properties of domain objects.  This is mostly because the author is fuzzy about these details
 of the domain model as of this writing.  This demo implementation would be a lot more valuable if it had better
 examples so as to add clarity to how the domain model is intended to be used.  On the bright side,
 this implementation serves to highlight what needs clarifying and with updates can serve as living documenation of
 examples of how the domain model is intended to be filled.

 This demo implementation does not remember

 This demo implementation doesn't use @Cacheable annotations, instead using in-memory Hashmap caches to ensure
 consistency in the values it returns (remembering what it randomly generated and re-using it when asked about that
 same employee ID again, essentially).  Arguably it would be clearer to use @Cacheable.

 Because the data is random, it may not for any given employee ID on any given run of your JVM have just the demo
 case you're looking for.  The intent is that you try additional employee IDs until you get what you want.  Of course
  if what you want is edge-case enough, this try-try-again approach will get frustrating and you'll wish for an
  implementation more like the alternative discussed below wherein demo data is editable.


# Alternatives

An alternative approach would have been to represent demo data as XML and use JAXB to read in that data.  That's a
pretty good idea but it loses the chance to use random generators to generate a variety of data on the fly.  It also
entails dependency on JAXB and and on the XML files at runtime in the demo data implementation.  This approach
minimizes dependencies.

Perhaps a hybrid approach of using XML files via JAXB where demo data is so defined,
and falling back on random data where it is not, would be the best of both worlds, allowing both handcrafted persona
examples of just what you're looking to try, while also allowing rapidly experimenting with different combinations of
 the data.

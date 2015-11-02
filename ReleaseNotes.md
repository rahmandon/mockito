## Release notes moved ##

Currently, the release notes are automatically generated at mockito's [GitHub site](https://github.com/mockito/mockito/blob/master/doc/release-notes/official.md).

## Changed in 1.9.5 (06-10-2012) ##

Few minor bugfixes and a relatively small extension point added to improve the android experience.

**StackTraceCleaner API**

To improve the experience of mocking on android platform we've added an extension point for cleaning the stack traces. This allows the friends behind the [dexmaker](http://code.google.com/p/dexmaker) to implement custom stack trace filter and hence make the Mockito verification errors contain clean and tidy stack traces. Clean stack trace is something Mockito always cares about! Thanks a lot **Jesse Wilson** for reporting, submitting the initial patch and validating the final solution.

2 minor bugs fixed:
  * javadoc fix ([issue 356](https://code.google.com/p/mockito/issues/detail?id=356)) - thanks **konigsberg** for reporting and patching and **Brice** for merging.
  * @InjectMocks inconsistency between java 6 and 7 ([issue 353](https://code.google.com/p/mockito/issues/detail?id=353)) - neat **Brice's** work.
  * fixed a problem with autoboxed default return values, needed for the MockMaker extension point ([issue 352](https://code.google.com/p/mockito/issues/detail?id=352)). Thanks so much **Jesse Wilson** for reporting and the patch, and **Brice** for merging!

## Changed in 1.9.5 rc-1 (03-06-2012) ##

**MockMaker API**

The [MockMaker](http://docs.mockito.googlecode.com/hg/1.9.5-rc1/org/mockito/plugins/MockMaker.html) extension point enables replacing the default proxy-creation implementation (cglib) with... something else :) For example, with a help from [dexmaker](http://code.google.com/p/dexmaker) you will be able to use Mockito with Android tests. Special thanks to friends from Google: **Jesse Wilson** and **Crazy Bob** for initiating the whole idea, for the patches, and for friendly pings to get the new feature released.

**MockingDetails**

The [MockingDetails](http://docs.mockito.googlecode.com/hg/1.9.5-rc1/org/mockito/Mockito.html#mocking_details) can be used to inspect objects and find out if they are Mockito mocks or spies.

Special thanks to **David Wallace**, one of the new members of the Mockito team, who was championing the feature.

**Other features**

  * [The delegating answer](http://docs.mockito.googlecode.com/hg/1.9.5-rc1/org/mockito/Mockito.html#delegating_call_to_real_instance) that can be useful for spying some objects difficult to spy in the typical way. Thanks to Brice Dutheil (who is one of the most active contributors :) for making it happen!
  * Driven by changes needed by the MockMaker API we started externalizing some internal interfaces. Hence some new public types. Down the road it will make Mockito more flexible and extensible.
  * We moved some classes from the public interface 'org.mockito.exceptions' to an internal interface (Pluralizer, Discrepancy, JUnitTool). Don't worry though, those classes were not exposed by our API at all so chance that someone is uses them is minimal. Just in case, though, I left the deprecated variants.

**Special thanks for the participants of Hackergarten Paris**

  * **Eric Lefevre** for his contribution on the simple answers, yet useful.
  * **José Paumard**, who contributed several issues including the delegating answer.
  * **Julien Meddah** for an even better error reporting.


The complete list of bug fixes and features is listed [here](http://code.google.com/p/mockito/issues/list?can=1&q=label%3AMilestone-Release1.9.5-rc1&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles).

[Mockito 1.9.5-rc1 Jvadoc](http://docs.mockito.googlecode.com/hg/1.9.5-rc1/org/mockito/Mockito.html)

### Thanks a lot to all community members for reporting issues, submitting patches and ideas! ###


---


## Changed in 1.9.0 (16-12-2011) ##

If you're upgrading from 1.8.5 please read about all the goodies delivered by 1.9.0-rc1!

This release contains 2 bug fixes and 1 awesome improvement.

  * Thanks to our mysterious friend **Dharapvj**, we now have most beautiful documentation. Take a look [here](http://docs.mockito.googlecode.com/hg/latest/org/mockito/Mockito.html)
  * Credits to **Daniel Spilker** for helping out with the issue related to mocks in superclasses
  * **Dpredovic** helped making the Mockito.reset() even better :)

Full details are listed [here](http://code.google.com/p/mockito/issues/list?can=1&q=label%3AMilestone-Release1.9&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles).

[Mockito 1.9.0 Javadoc](http://docs.mockito.googlecode.com/hg/1.9.0/org/mockito/Mockito.html)

## Changed in 1.9.0-rc1 (23-07-2011) ##

  * Annotations are smarter. They can use constructor injection and instantiate objects. More information [here](http://docs.mockito.googlecode.com/hg/1.9.0-rc1/org/mockito/Mockito.html#23).
  * To keep the test code slim you can now create a stub [in one line](http://docs.mockito.googlecode.com/hg/1.9.0-rc1/org/mockito/Mockito.html#24).
  * Made it possible to verify interactions [ignoring stubs](http://docs.mockito.googlecode.com/hg/1.9.0-rc1/org/mockito/Mockito.html#25).
  * Fixed various bugs & enhancements. Full list is [here](http://code.google.com/p/mockito/issues/list?can=1&q=label%3AMilestone-Release1.9&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles).
  * The jars should arrive to maven central within hours

### Thanks to all the community members who helped improving Mockito! ###

**Brice Dutheil** is a new Mockito champion, having contributed a lot of excellent code in the recent months! Without him, there wouldn't be any release and your mailing list queries wouldn't be answered so promptly! Brice - thank you and welcome to the team!

Some fresh mojitoes ought to be served to:

  * **Steven Baker** for sharing the one-liner stubs idea
  * **Konrad Garus** for reporting the inconsistencies in the docs & exception messages
  * **Murat Knecht** for the verbose logging
  * **Krisztian Milesz** for the maven javadoc enhancement
  * **Edwinstang** for patience and patches to injection logic
  * **Kristofer Karlsson** for important bug reports and help with the mailing list
  * **Gordon Tyler** for his vigilance and help on getting the main docs sorted
  * **lucasmrtuner** for patches
  * **jakubholy.net** for excellent doc updates
  * **Andre Rigon** for patches on constructor injection
  * **Ulrich Hobelmann** for important doc updates
  * **Peter Knista, Ivan Koblik, Slawek Garwol, Ruediger Herrmann, Robert Thibaut, Clive Evans** for reporting important issues
  * **rdamazio, kenpragma, mszczytowski, albelsky, everflux, twillhorn, nurkiewicz, hanriseldon, exortech, edwinstang, dodozhang21** for some more issue reports :)

[Mockito 1.9.0-rc1 Javadoc](http://docs.mockito.googlecode.com/hg/1.9.0-rc1/org/mockito/Mockito.html)


---


## Changed in 1.8.5 (24-05-2010) ##

  * Added verification with timeout for testing in concurrent conditions. Read more [here](http://mockito.googlecode.com/svn/tags/latest/javadoc/org/mockito/Mockito.html#22)
  * Error message for verifyNoMoreInvocations() prints all invocations and marks those unverified. Useful for debugging.
  * Fixed some minor bugs & enhancements. Full list is [here](http://code.google.com/p/mockito/issues/list?can=1&q=label%3AMilestone-Release1.8.5&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles)

**Thanks a lot to everyone who helped with bug reports, feedback and patches!**

[Mockito 1.8.5 Javadoc](http://docs.mockito.googlecode.com/hg/1.8.5/org/mockito/Mockito.html)


---


## Changed in 1.8.4 (17-03-2010) ##

  * **Important:** Moved annotation configuration enum to external package to avoid problems with OSGi ([issue 179](https://code.google.com/p/mockito/issues/detail?id=179)). **Majority** of users should not be affected because the enum is only used for extra configuration of @Mock annotation. Nevertheless if you use 1.8.3 please upgrade. If you have used extra configuration for annotations in your code you will have to soothe the compiler. There was no other way to fix it - deprecation mechanism couldn't do.

  * Fixed some minor bugs & enhancements. Full list is [here](http://code.google.com/p/mockito/issues/list?can=1&q=label%3AMilestone-Release1.8.4&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles)


---


## Changed in 1.8.3 (07-03-2010) ##

All changes are listed [here](http://code.google.com/p/mockito/issues/list?can=1&q=label%3AMilestone-Release1.8.3&colspec=ID+Type+Status+Priority+Milestone+Owner+Summary&cells=tiles)

  * added [new annotations](http://mockito.googlecode.com/svn/tags/1.8.3/javadoc/org/mockito/Mockito.html#21). Credits go to **Alen Vrecko, Tom Rathbone, Brice Dutheil**.
  * fixed an issue with 'Unrooted tests' when using runner in Eclipse. Many thanks to  **James R Carr** who finally cracked it (there was at least one rock star developer who was defeated by this bug :)
  * fixed an issue with OSGi bundle (thanks for reporting, **Zladdi**)
  * undeprecated stub() (especially for Mr. BDD)
  * added 'deep stubs' that help dealing dealing with legacy code. (using it for new code is a clear sign you are developing legacy code). [Read more](http://mockito.googlecode.com/svn/tags/1.8.3/javadoc/org/mockito/Mockito.html#RETURNS_DEEP_STUBS). Thanks to **James R Carr** who must have been dealing with legacy code lately :).


---


## Changed in 1.8.2 (12-12-2009) ##

Maintenance release, fixed [2 minor bugs](http://code.google.com/p/mockito/issues/list?can=1&q=label%3AMilestone-Release1.8.2%20status%3ADone%2CVerified%2CFixed). Thanks to Brice Dutheil and Greg Hengeli for reporting!


---


## Changed in 1.8.1 (22-11-2009) ##

**Intro:**

Thanks a lot to many of you for helping!!!

**Noteworthy:**

  * couple of bugs fixed
  * mocks can be made serializable
  * OSGi headers fixed

**Minor features:**

  * matching arrays smartly
  * anyVararg() matcher
  * only() verification mode

**All issues/enhancements including minor things:**

See issue tracker [here](http://code.google.com/p/mockito/issues/list?can=1&q=label%3AMilestone-Release1.8.1%20status%3ADone%2CVerified%2CFixed)


---


## Changed in 1.8.0 (23-07-2009) ##

**Intro:**

Send us feedback about this release and don't hesitate to report suggestions. In maven central to be within a day.

**Highlight features:**

  * Capturing arguments for further assertions
  * Real partial mocks
  * Resetting mocks
  * Invalid use of framework errors point exactly the place in code where you misuse Mockito
  * Troubleshooting & validating framework usage
  * Aliases for [BDD](http://en.wikipedia.org/wiki/Behavior_Driven_Development)-style tests that use //given //when //then comments. More info [here](http://mockito.googlecode.com/svn/branches/1.8.0/javadoc/org/mockito/BDDMockito.html).

Read more about highlight features [here](http://mockito.googlecode.com/svn/branches/1.8.0/javadoc/org/mockito/Mockito.html#15)

Full documentation is available [here](http://mockito.googlecode.com/svn/branches/1.8.0/javadoc/org/mockito/Mockito.html)

**Important changes:**

  * spy() method produces real partial mocks so it changes current behavior. It should not introduce any regression in sane tests, though.
  * Changed the way clickable actual calls are presented in verification errors. Let us know to the mailing list how do you like it.
  * WantedButNotInvoked verification error now shows extra information. It tells if there were no iteractions with specific mock or lists actual invocations in concise manner. Let us know to the mailing list how do you like it.
  * Mockito is now OSGfied but please confirm if the bundle is 100% correct.
  * Removed some deprecated, old and well hidden api that was most likely never used by any user. Removed api was related to global mockito configuration.

**All issues/enhancements including minor things:**

See issues tracker: http://code.google.com/p/mockito/issues/list?q=label:Release1.8%20status:Fixed&can=1


---


## Changed in 1.7 (24-01-2009) ##

First the simplest fix, but important:

**type safety** for return values when stubbing.

> - **warning** this might make your code not compiling but it's quite unlikely.
> You probably always want return the correct type when stubbing
> so please don't curse Mockito and fix your code in case this feature breaks compilation.

```
when(foo.get()).thenReturn(compilerChecksTypeHere);
```

**Minor fixes**

See issues tracker: http://code.google.com/p/mockito/issues/list?q=label:Release1.7%20status:Fixed&can=1

**Setting default return values on mock creation**

This feature was invented by Dan North and should be helpful when dealing with legacy code. For example, you can create a mock that returns 'SmartNulls' by default (e.g. unless stubbed). If your code under test tries to use the SmartNull it will throw an exception saying something along these lines: 'You've got a NullPointerException here <clickable stack trace element> because you forgot to stub this method: <another clickable stack trace element>'

```
Foo mock = (Foo.class, RETURNS_SMART_NULLS);

//calling unstubbed method here:
Stuff stuff = mock.getStuff();

//using object returned by unstubbed call:
stuff.doSomething();
   
//Above doesn't yield NullPointerException this time!
//Instead, SmartNullPointerException is thrown. 
//Exception's cause links to unstubbed mock.getStuff() - just click on the stack trace.
```

Mockito 2.0 mocks will probably return SmartNulls by default.

**Shorthand API for consecutive stubbing**
```
when(foo.get())
  .thenReturn("one", "two", "three");
```

```
when(foo.get())
  .thenThrow(new NullPointerException(), new RuntimeException());
```

**Better errors when framework misused**

When one misuses the framework (for example: verify(mock.foo()) instead of verify(mock).foo()) then he gets earlier error. Since 1.7, framework is validated also on mock creation.


---


## Changed in 1.6 (21-10-2008) ##

**API change:**

```

//instead of:

stub(mock.getStuff()).toReturn("stuff");

//please use:

when(mock.getStuff()).thenReturn("stuff");

```

Many users found stub() confusing therefore [stub()](http://mockito.googlecode.com/svn/branches/1.6/javadoc/org/mockito/Mockito.html#stub(T)) has been deprecated in favor of [when()](http://mockito.googlecode.com/svn/branches/1.6/javadoc/org/mockito/Mockito.html#when(T)). We discussed this API change on the mailing list (for example [here](http://groups.google.com/group/mockito/browse_thread/thread/77c328c4175cfc6d/c2a934bee51920bb?lnk=gst&q=thenReturn#c2a934bee51920bb)

If you're an existing user then sorry for making your code littered with deprecation warnings. This change was required to make Mockito better. How to fix deprecation warnings? Typically it's just few minutes of search & replace job:

  * **stub**       replace with: **when(**
  * **.toReturn(** replace with: **.thenReturn(**
  * **.toThrow(**  replace with: **.thenThrow(**
  * **.toAnswer(** replace with: **.thenAnswer(**

**Fixed problem** with @Mock annotation not resolved properly in IDE ([issue 21](https://code.google.com/p/mockito/issues/detail?id=21)). This change may also require some clean up of deprecated code. Typically, the quickest way to fix deprecation warnings is some search & replace job: search: **import org.mockito.MockitoAnnotations.Mock;** and replace with: **import org.mockito.Mock;**

**Added junit runners** ([MockitoJunit44Runner](http://mockito.googlecode.com/svn/branches/1.6/javadoc/org/mockito/runners/MockitoJUnit44Runner.html) and [MockitoJunitRunner](http://mockito.googlecode.com/svn/branches/1.6/javadoc/org/mockito/runners/MockitoJUnitRunner.html)) that automatically initialize annotated mocks ([issue 23](https://code.google.com/p/mockito/issues/detail?id=23))

**New feature:** when arguments are different the exception now allows using the comparison window ([issue 22](https://code.google.com/p/mockito/issues/detail?id=22)).

**Fixed** mock creation logic so that there no problems when running with maven ([issue 24](https://code.google.com/p/mockito/issues/detail?id=24))

**Fixed ArrayEquals** exception ([issue 20](https://code.google.com/p/mockito/issues/detail?id=20)) thanks to report by Ward Bryon

**Added performance** tweak ([issue 19](https://code.google.com/p/mockito/issues/detail?id=19)) thanks to patch by Roberto Tyley

**Removed 'final'** on Mockito class ([issue 18](https://code.google.com/p/mockito/issues/detail?id=18))

**Fixed [issue 25](https://code.google.com/p/mockito/issues/detail?id=25)**

## Changed in 1.5 (26-07-2008) ##

**1. Stubbing methods with generic Answer interface**:

```
   stub(mock.someMethod(anyString())).toAnswer(new Answer() {
       Object answer(InvocationOnMock invocation) {
           Object[] args = invocation.getArguments();
           Object mock = invocation.getMock();
           return "called with arguments: " + args;
       }});
```

Useful for adding side effects or in some other non-trivial scenarios. Should be used carefully and occasionally. Adds more power and flexibility to the framework. As usual: with the great power comes the great responsibility :) Thanks to John Hampton Jr for contributing a patch.

**2. Syntax modification**:

```
   //Instead of:
   stubVoid(mock).toThrow(ex).on().someVoidMethod();

   //Since 1.5 you do:
   doThrow(ex).when(mock).someVoidMethod();
```

stubVoid() **has been deprecated** so a bit of clean up in your code is required (unless you don't care about compiler warnings). However, there should not be too much of a hassle since stubbing voids is pretty rare.

Syntax modification was required to make things more readable, more consistent and to solve one corner case (more reading [here](http://groups.google.com/group/mockito/browse_thread/thread/77c328c4175cfc6d/c2a934bee51920bb?lnk=gst&q=stubVoid#c2a934bee51920bb))

doThrow() functionality was completed by doAnswer(), doNothing(), doReturn() methods. Please refer to [docs](http://mockito.googlecode.com/svn/branches/1.5/javadoc/org/mockito/Mockito.html#doThrow(java.lang.Throwable)) for more information.

**3. Spying on real objects**

In rare scenarios we would like to use real objects but still take advantage of verify() and stub() methods. Spying on real objects is associated with controversial partial mocking feature. We discussed it [here](http://groups.google.com/group/mockito/browse_thread/thread/8b7ac0b7d18605ff/01d79bcd9627575f?lnk=gst&q=spying+on+real#01d79bcd9627575f)

```
   List list = new LinkedList();
   List spy = spy(list);

   //wow, I can stub it!
   stub(spy.size()).toReturn(100);

   //wow, I can use it and add real elements to the list!
   spy.add("one");

   //wow, I can verify it!
   verify(spy).add("one);
```

Enough of wows, this feature should be used carefully and occasionally. With the great power...

**4. Started including cglib and asm in mockito-core**.

There were few problems with cglib: no latest release in maven central blocking our release; problems when upgrading from mockito 1.4->1.5 due to conflicting versions of cglib on classpath. To fix those problems we decided to include cglib and asm inside mockito-core. Libraries are included with renamed packages (JarJar task feature) so there should be no problems with conflicting versions any more. Thanks to Ola Bini for this idea.

**5. Fixed** [issue 11](https://code.google.com/p/mockito/issues/detail?id=11) related to using mockito in eclipse container (e.g. Run as plugin test)

**6. Fixed** [issue 14](https://code.google.com/p/mockito/issues/detail?id=14) related to multiple threads playing with the same mock instances.

**7. Javadoc enhancements** based on **always welcome** users' feedback.

## Changed in 1.4 ##

**Added new stubbing feature**: different return values for consecutive method calls (like mocking iterators). This feature is to be used judiciously. Mocking iterators can be avoided by preferring Iterables/collections which results in simpler and cleaner code.

```
stub(mock.getStuff())
  .toReturn(1)
  .toReturn(2)
  .toThrow(new RuntimeException());

```

**Changed anyX() matchers** to treat nulls as a valid 'anything' value.

**Added** few handy matchers: anyCollection(), anyList(), anyMap().

**Started using cglib 2.2 stable**.


**Fixed** [issue 13](https://code.google.com/p/mockito/issues/detail?id=13) (anyObject() matcher with varargs problem).


**Added more descriptive exception message** based on reported typical misuse (Added comment that the user might forget to call initMocks() when annotation @Mock is used).


**Javadoc enhancements** based on comments.

## Changed in 1.3 ##

**Started using cglib 2.1.3** to sort out maven dependencies.

**Started using field names** of annotated (@Mock) mocks for printing failed interactions. That gives better readability and also promotes good names for fields. For example following mock:

```
  @Mock private LinkedList listOfArticles;

  //in case of verification error, will be printed like that: 

  "Wanted but not invoked: listOfArticles.clear();"

  //In previous version, it would be like that:

  "Wanted but not invoked: LinkedList.clear();"
```

**To be more consistent** with previous feature, invocations are printed with **lowercase first letter**. For example if you happened to mock a LinkedList then the verification error will show linkedList.clear() instead of LinkedList.clear().

**Tuned verification messages** when arguments don't match. Arguments are automatically broken to vertical list if printed invocation is too long.

**Default return values** for primitive wrapper classes are now consistent with primitives. E.g: an int method returns 0 but an Integer method also return 0 instead of null.

**Enabled stubbing toString()** because 'why not' and it may be useful for debugging purposes

**Exposed configuration of default return values** to enable custom 'mocking style'. Helpful for legacy code. For more information read this [thread](http://groups.google.com/group/mockito/t/cd61a7a34a51aa4b) or look at examples [here](http://code.google.com/p/mockito/source/browse/branches/1.3/test/org/mockitousage/examples/configure/withstaticutility) or [here](http://code.google.com/p/mockito/source/browse/branches/1.3/test/org/mockitousage/examples/configure/withrunner).

**Fixed issue** with reporting errors related to [issue #7](https://code.google.com/p/mockito/issues/detail?id=#7)

**Added some examples** to the test code, e.g. using JUnit runner to take advantage on @Mock annotation and avoid infamous base class for tests.

**Javadoc changes** to reflect feedback from users

## Changed in 1.2 ##

  * added better verification message when the difference is only about arguments. Arguments are now listed vertically and exception/message is more explicit. It is particularly useful if custom/hamcrest matchers are used.
  * [ArgumentMatcher](http://mockito.googlecode.com/svn/branches/1.2/javadoc/org/mockito/ArgumentMatcher.html) has now better default description (describeTo() method). It decamelizes class name, e.g: in case of failure, StringWithStrongLanguage matcher will describe itself as 'String with strong language'.
  * fixed bug with mocking in java main (thanks to bug report by Paweł Jagus).

## Changed in 1.1 ##

  * added **never()** method which is an alias to **times(0)**. It makes the test code explicit and nicely communicates an intent (thanks to Liz Keogh for this suggestion).

```
  verify(mock, never()).someMethod();
```

  * if times(0) or never() fails the exception is explicit (NeverWantedButInvoked).
  * fixed bug with @Mock: mocks annotated in base classes weren't initialized with initMocks() method (thanks to bug report by Vitaly Berov).
  * javadoc tuning according to comments/mailing list feedback.

### Changed in 1.0 ###

Since beta version, matchers were refactored into hamcrest matchers. Hence another jar dependency. Also, if you created custom argument matchers in beta you will have to change them after moving to 1.0. CustomMatcher became an ArgumentMatcher which is an implementation of hamcrest Matcher.

### Changed in 1.0 ###

  * integrated with hamcrest for argument matching.
  * minor javadoc/error messages fixes

### Changed in 0.91 ###

  * made verification in-order relaxed so that I can leave out some unimportant interactions from the middle of chain.

### Changed in 0.9 ###

  * added @Mock annotation to eliminate some boilerplate.
  * added refEq() matcher for reflection-based equals.
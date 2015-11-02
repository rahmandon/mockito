## If you want to use Mockito with jvm < 1.5 you have 3 options: ##

**1.** get **all your code** upgraded to newer java (c'mon, you've got to do it at some point!)

**2.** get **your test code** upgraded to newer java. You don't have to change production code - just set up your IDE & build tool to use jvm 1.5 to compile & run your tests. It may be tricky sometimes (you might need separate projects if you use Eclipse, etc) but there are Mockito users who managed to set it up.

**3.** Use [mockito-all-jvm14-1.8.0.jar](http://mockito.googlecode.com/files/mockito-all-jvm14-1.8.0.jar). It is maintained by James Carr. It was created using [retrotranslator](http://retrotranslator.sourceforge.net). In jdk14 there are no static imports so Mockito api via static methods makes the code a bit cluttered. To work around that there is an additional class org.mockito.MockitoTestCase that provides methods like mock, verify, etc. Please refer to javadocs or to the mailing list in case you have any questions.
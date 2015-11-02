# Introduction #

Code in which new objects are created can be difficult to test.  There are a number of patterns for doing this; two of them are discussed here.  Both of these patterns may require rearranging your code a little, to make it more testable.
Writing code that's easily testable is a good thing to be doing,
regardless of which mocking framework you end up using.

# Details #

Pattern 1 involves factoring uses of `new` into one-line methods, then
using a Mockito spy.  This is the simpler of the two patterns.
Pattern 2 involves factoring uses of `new` into a separate class and
injecting it.  It's a little more work, but it can be more powerful.
The `new` calls that you want to factor out (using either pattern) are
those where an object is created, that you are likely to want to
mock.  It is recommended that you use one or other of these patterns, whenever you find yourself writing `new`, on a class that's in your code base (as opposed to a JDK class or a class from a third party library).

## Pattern 1 - using one-line methods for object creation ##

To use pattern 1 (testing a class called `MyClass`), you would replace a
call like
```
Foo foo = new Foo( a, b, c ); 
```
with
```
Foo foo = makeFoo( a, b, c ); 
```
and write a one-line method
```
Foo makeFoo( A a, B b, C c ){ 
    return new Foo( a, b, c ); 
} 
```

It's important that you don't include any logic in the method; just
the one line that creates the object.  The reason for this is that the method itself is never going to be unit tested.

When you come to test the class, the object that you test will actually be a Mockito spy, with this method overridden, to return a mock.  What you're testing is therefore not the class itself, but a very slightly modified version of it.

Your test class might contain members like
```
@Mock private Foo mockFoo; 
private MyClass toTest = spy( new MyClass()); 
```

Lastly, inside your test method you mock out the call to makeFoo with
a line like
```
doReturn( mockFoo )
    .when( toTest )
    .makeFoo( any( A.class ), any( B.class ), any( C.class )); 
```
You can use matchers that are more specific than `any()` if you want to
check the arguments that are passed to the constructor.

## Pattern 2 - the factory helper pattern ##

One case where this pattern won't work is if `MyClass` is final.  Most
of the Mockito framework doesn't play particularly well with final
classes; and this includes the use of `spy()`.  Another case is where
`MyClass` uses `getClass()` somewhere, and requires the resulting value to be `MyClass`.  This won't work, because the class of a spy is actually a Mockito-generated subclass of the original class.

In either of these cases, you'll need the slightly more robust factory helper pattern, as follows.

```
public class MyClass{ 
    static class FactoryHelper{ 
        Foo makeFoo( A a, B b, C c ){ 
            return new Foo( a, b, c ); 
        } 
    } 

    //... 

    private FactoryHelper helper; 
    public MyClass( X x, Y y ){ 
        this( x, y, new FactoryHelper()); 
    } 

    MyClass( X x, Y, y, FactoryHelper helper ){ 

        //... 

        this.helper = helper; 
    } 

    //... 

    Foo foo = helper.makeFoo( a, b, c ); 
} 
```

So, you have a special constructor, just for testing, that has an
additional argument.  This is used from your test class, when creating
the object that you're going to test.  In your test class, you mock
the `FactoryHelper` class, as well as the object that you want to
create.
```
@Mock private MyClass.FactoryHelper mockFactoryHelper; 
@Mock private Foo mockFoo; 
private MyClass toTest; 
```
and you can use it like this
```
toTest = new MyClass( x, y, mockFactoryHelper ); 
when( mockFactoryHelper.makeFoo( 
    any( A.class ), any( B.class ), any( C.class )))
    .thenReturn( mockFoo ); 
```

As with pattern 1, you can use matchers that are more specific than `any()` if you need to.
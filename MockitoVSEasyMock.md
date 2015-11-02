Again, hats down before the EasyMock gang (record-playback is one of the coolest ideas I came across) - THANKS!!! Mockito started off as an EasyMock fork but we evolved too much and we don't share any code with EasyMock. Most of the logic was rewritten, some of the code was also inspired by jMock (like the excellent ClassImposterizer).

# EasyMock #

```
  import static org.easymock.classextension.EasyMock.*; 	

  List mock = createNiceMock(List.class);					
  
  expect(mock.get(0)).andStubReturn("one");				
  expect(mock.get(1)).andStubReturn("two");
  mock.clear();
  
  replay(mock);
  
  someCodeThatInteractsWithMock();							
  
  verify(mock); 
```

# Mockito #

```
  import static org.mockito.Mockito.*;
  
  List mock = mock(List.class);
  
  when(mock.get(0)).thenReturn("one");
  when(mock.get(1)).thenReturn("two");
  
  someCodeThatInteractsWithMock();
  
  verify(mock).clear();                       
```

# Similarities #

  * allow the same level verification as EasyMock (unexpected invocations, redundant invocations, verification in order)

  * argument matchers (anyInt(), anyObject(), etc.)

# Differences #

  * no record/replay modes - no need for them. There only 2 things you can do with Mockito mocks - verify or stub. Stubbing goes before execution and verification afterwards.

  * all mocks are 'nice' (even somehow nicer, because collection-returning methods return empty collections instead of nulls). Even though mocks are nice, you can verify them as strictly as you want and detect any unwanted interaction.

  * explicit language for better readability: verify() and when() VS the mixture of expect(mock.foo()) and mock.foo() (plain method call without 'expect'). I'm sure some of you will find this argument subjective :)

  * simplified stubbing model - stubbed methods replay all the time with stubbed value no matter how many times they are called. Works exactly like EasyMock's andStubReturn(), andStubThrow(). Also, you can stub with different return values for different arguments (like in EasyMock).

  * Verification of stubbed methods is optional because usually it's more important to test if the stubbed value is used correctly rather than where's it come from.

  * verification is explicit - verification errors point at line of code showing what interaction failed.

  * verification in order is flexible and doesn't require to verify every single interaction.

  * custom argument matchers use hamcrest matchers, so you can use your existing hamcrest matchers. (EasyMock can also integrate with hamcrest though it is not a part of EasyMock but hamcrest. See the documentation of hamcrest).

# Verification in order #

### EasyMock ###

```
  Control control = createStrictControl();  
  
  List one = control.createMock(List.class);				
  List two = control.createMock(List.class);				
  
  expect(one.add("one")).andReturn(true);
  expect(two.add("two")).andReturn(true);
  
  control.replay();
  
  someCodeThatInteractsWithMocks();							

  control.verify();	
```

### Mockito ###

```
  List one = mock(List.class);	
  List two = mock(List.class);		
  
  someCodeThatInteractsWithMocks();	
  
  InOrder inOrder = inOrder(one, two);
                                                            
  inOrder.verify(one).add("one");
  inOrder.verify(two).add("two");
```

# Stubbing void methods #

### EasyMock ###

```
  List mock = createNiceMock(List.class);					
  
  mock.clear();												
  expectLastCall().andThrow(new RuntimeException());
  
  replay(mock);
```

### Mockito ###

```
  List mock = mock(List.class);

  doThrow(new RuntimeException()).when(mock).clear();
```

# Exact number of times verification and argument matchers #

### EasyMock ###

```
  List mock = createNiceMock(List.class);						
  
  mock.clear();
  expectLastCall().times(3);
  
  expect(mock.add(anyObject())).andReturn(true).atLeastOnce();				

  replay(mock);								
  
  someCodeThatInteractsWithMock();							

  verify(mock);	  
```

### Mockito ###

```
  List mock = mock(List.class);        

  someCodeThatInteractsWithMock();                 
  
  verify(mock, times(3)).clear();
  verify(mock, atLeastOnce()).add(anyObject());      
```
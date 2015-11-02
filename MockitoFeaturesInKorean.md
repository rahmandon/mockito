#Mockito Features에 대한 한글 번역 문서입니다. : http://docs.mockito.googlecode.com/hg/org/mockito/Mockito.html

This Document is a Korean translation of the Mockito Features

# Details #

# 1. 동작을 검증해보자! #
```
// 코드를 깔끔하게 만들기 위해 Mockito를 static 으로 import 하자.
import static org.mockito.Mockito.*;

// mock 생성
List mockedList = mock(List.class);

// mock 객체 사용
mockedList.add(“one”);
mockedList.add(“two”);

// 검증 하기
verify(mockedList).add(“one”);
verify(mockedList).clear();
```
일단 생성되고 나면, mock은 모든 상호작용을 기억합니다. 사용자는 mock의 어떤 method가 실행되었는지 선택적으로 검증해볼 수 있습니다.

# 2. stubbing은 어떻게 하는거야? #
```
// interface 뿐 아니라 구체 클래스도 mock으로 만들 수 있다.
LinkedList mockedList = mock(LinkedList.class);
 
// stubbing
when(mockedList.get(0)).thenReturn("first");
when(mockedList.get(1)).thenThrow(new RuntimeException());
 
// 첫 번째 element를 출력한다.
System.out.println(mockedList.get(0));
 
// runtime exception이 발생한다.
System.out.println(mockedList.get(1));
 
// 999번째 element 얻어오는 부분은 stub되지 않았으므로 null이 출력
System.out.println(mockedList.get(999));
  
// stubbing 된 부분이 호출되는지 확인할 수 있긴 하지만 불필요한 일입니다.
// 만일 코드에서 get(0)의 리턴값을 확인하려고 하면 테스트가 깨집니다.
// 만일 코드에서 get(0)의 리턴값에 대해 관심이 없다면 stubbing 하면 안 됩니다. 더 많은 정보를 위해서는 여기를 읽어보세요.
verify(mockedList).get(0)
```

# 3. Argument matchers #
Mockito는 자연스러운 자바 스타일(equals()를 이용해 비교하는 방식)로 파라미터를 검증합니다. 하지만 가끔 유연하게 파라미터를 검증하고 싶을 때 argument matcher를 사용할 수 있습니다.
```
// 내장된 argument matcher인 anyInt()를 이용한 stubbing
when(mockedList.get(anyInt())).thenReturn("element");
 
// hamcrest를 이용한 stubbing (isValid()는 사용자가 정의한 hamcrest matcher이다.)
when(mockedList.contains(argThat(isValid()))).thenReturn("element");

// 다음 코드는 "element"를 출력한다. 
System.out.println(mockedList.get(999));
 
// argument matcher를 이용해 검증할 수도 있다.
verify(mockedList).get(anyInt());
```
Argument matchers는 유연한 검증 또는 유연한 stubbing을 할 수 있게 도와줍니다. 더 많은 custom argument matcher와 hamcrest matcher를 보려면 여기를 클릭 하세요.

custom argument matcher와 관련있는 정보를 원하시면 ArgumentMatcher 클래스의 javadoc 문서를 다운받으세요.

복잡한 argument matching을 사용하는 것도 괜찮습니다. anyX()계열의 matcher가 포함된 equals()를 사용함으로써 테스트를 깔끔하게 유지할 수 있기 때문입니다. 가끔은 테스트에 도움이 되도록 equals()를 구현하거나 equals()를 이용한 비교가 가능하도록 코드를 리팩토링 하는 것이 더 나을 때도 있습니다.

섹션 15를 읽거나 ArgumentCaptor 클래스의 자바독을 읽어보세요. ArgumentCaptor는 더 진화된 assert를 사용할 수 있게 해주는 argument matcher입니다.

argument matchers에 대한 경고:

argument matcher를 사용하려면 모든 파라미터는 matcher로 전달되어야 합니다.
예를 들어: (예제는 검증 단계에 대한 것이지만, stubbing에 대해서도 마찬가지입니다.)
```
verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
// 위의 코드는 옳은 코드입니다. - eq()는 matcher의 일종입니다.
   
verify(mock).someMethod(anyInt(), anyString(), "third argument");
// 위의 코드는 틀렸습니다. - 세 번째 파라미터가 argument matcher 없이 전달되었으므로 예외가 던져질 것입니다.
```
# 4. 몇 번 호출됐는지 / 최소한 한 번 호출됐는지 / 호출되지 않았는지 확인하기 #
```
// mock 설정
mockedList.add("once");
 
mockedList.add("twice");
mockedList.add("twice");
 
mockedList.add("three times");
mockedList.add("three times");
mockedList.add("three times");
 
// 아래의 두 가지 검증 방법은 동일하다. times(1)은 기본값이라 생략되도 상관없다.
verify(mockedList).add("once");
verify(mockedList, times(1)).add("once");

// 정확히 지정된 횟수만큼만 호출되는지 검사한다.
verify(mockedList, times(2)).add("twice");
verify(mockedList, times(3)).add("three times");

// never()를 이용하여 검증한다. never()는 times(0)과 같은 의미이다.
verify(mockedList, never()).add("never happened");

// atLeast()와 atMost()를 이용해 검증한다.
verify(mockedList, atLeastOnce()).add("three times");
verify(mockedList, atLeast(2)).add("five times");
verify(mockedList, atMost(5)).add("three times");
```
times(1)은 기본값이므로 생략될 수 있습니다.

# 5. void method의 exception stubbing #
```
doThrow(new RuntimeException()).when(mockedList).clear();
   
// 다음 문장은 RuntimeException를 던진다.
mockedList.clear();
```
12번째 문단에 있는 doThrow|doAnswer 군의 메소드에 대해서 더 읽어보세요.

처음에 stubVoid(Object)가 void 메소드를 stubbing 하기 위해 사용되었습니다. 현재는 doThrow(Throwable)이 대신해서 사용되도록 stubVoid는 deprecated 되었습니다. doThrow를 사용함으로써 가독성이 좋아지고 doAnswer(Answer) 메소드군과 일관성도 지킬 수 있습니다.

# 6. 순서 검증하기 #
```
List firstMock = mock(List.class);
List secondMock = mock(List.class);
 
// mock을 사용한다.
firstMock.add("was called first");
secondMock.add("was called second");

// mock이 순서대로 실행되는지 확인하기 위해 inOrder 객체에 mock을 전달한다.
InOrder inOrder = inOrder(firstMock, secondMock);

// 다음 코드는 firstMock이 secondMock 보다 먼저 실행되는 것을 확인한다.
inOrder.verify(firstMock).add("was called first");
inOrder.verify(secondMock).add("was called second");
```
mock들이 순서대로 실행되는지 검증하는 것은 유연한 검증 방식입니다. – 모든 method가 실행되는지 검증할 필요는 없고 관심 있는 method만 순서대로 실행되는지 검증하면 됩니다.

또, 검증하려고 하는 mock만 InOrder를 통해 생성한 뒤 차례대로 실행되는지 검사할 수 있습니다.

# 7. mock이 실행되지 않았는지 확인하기 #
```
// mock 사용하기 - mockOne만 호출된다.
mockOne.add("one");
 
// 일반적인 검증
verify(mockOne).add("one");
 
// 메소드가 호출되지 않았는지 검증
verify(mockOne, never()).add("two");
 
// 다른 mock이 호출되지 않았는지 검증
verifyZeroInteractions(mockTwo, mockThree);
```
# 8. 불필요하게 실행되는 코드 찾아내기 #
```
// mock 설정
mockedList.add("one");
mockedList.add("two");
 
verify(mockedList).add("one");

// 다음 구문은 실패한다.
verifyNoMoreInteractions(mockedList);
```
경고 : 고전적인 방법인 expect-run-verify 방식의  테스트를 하는 사람들은 verifyNoMoreInteractions()를 너무 자주, 심지어는 모든 테스트에서 사용하는 경향이 있습니다. verifyNoMoreInteractions()는 아무 테스트에서나 무분별하게 사용되지 않았으면 합니다. verifyNoMoreInteractions()는 실행 여부를 검사하는 테스트 툴에서 간편하게 사용될 수 있는 assertion 방법입니다. 정말 적절한 상황에서만 사용하세요. 이 method를 불필요하게 많이 사용하게 되면 유지보수가 힘든 테스트가 만들어집니다. 여기를 참조하면 더 많은 정보를 얻을 수 있습니다.

never()에 대해서도 읽어보세요. – 코드의 의도를 잘 드러낼 수 있는 method입니다.

# 9. 간단하게 mock 생성하기 - @Mock 어노테이션 #
  * 반복적인 mock 생성 코드를 줄여준다.
  * 테스트 클래스의 가독성을 높여준다.
  * 필드 이름으로 각각의 mock을 구분하기 때문에, 검증시에 발생하는 에러를 좀 더 읽기 쉽게 만들어준다.

```
public class ArticleManagerTest { 
@Mock private ArticleCalculator calculator;
@Mock private ArticleDatabase database;
@Mock private UserProvider userProvider;
     
private ArticleManager manager;
…
```

중요! 이 코드는 어떤 base class 또는 test 코드의 일부입니다.

MockitoAnnotations.initMocks(testClass);

내장 runner인 MockitoJUnitRunner를 이용하셔도 됩니다.

MockitoAnnotations 에 대해 더 많은 정보를 읽고 싶으면 여기를 읽어보세요.

# 10. 연속적인 콜 stubbing하기 #
테스트를 하다보면 가끔 같은 method 호출에 대해 다른 return 값이나 exception을 발생시켜야 하는 경우가 있습니다. 보통은 iterator를 mocking 해서 처리합니다. Mockito의 초기 버전은 단순한 mocking에 대해 알리기 위해 이러한 기능을 구현하지 않았습니다. 예를 들어 integrator 대신 interable collection을 사용하거나 또는 그냥 collection을 사용하면 됩니다. 이것이 자연스러운 stubbing 방법입니다. (예를 들어 실제 collection을 이용한다든지) 연속적인 호출에 대한 stubbing이 필요한 경우는 거의 없습니다.
```
when(mock.someMethod("some arg"))
.thenThrow(new RuntimeException())
.thenReturn("foo");
 
// 첫 번째 호출 : runtime exception을 던져라.
mock.someMethod("some arg");

// 두 번째 호출 : "foo"를 출력해라.
System.out.println(mock.someMethod("some arg"));

// 그 다음에 일어나는 모든 호출 : "foo"를 출력한다. (마지막 stubbing이 계속 발생한다.)
System.out.println(mock.someMethod("some arg"));

// 연속적인 호출에 대한 stubbing을 간략화할 수 있다.

when(mock.someMethod("some arg"))
.thenReturn("one", "two", "three");
```
# 11. callback을 stubbing 하기 #
generic Answer interface를 이용해 stubbing 하기

이 기능은 초기의 Mockito에는 포함되지 않았고 여전히 논쟁의 대상인 기능입니다. 우리는 단순한 stubbing인 thenReturn()이나 thenThrow()만 사용할 것을 추천합니다. 이 두 개의 method 만으로도 테스트와 테스트 주도로 만들어지는 코드를 깔끔하게 유지할 수 있습니다.
```
when(mock.someMethod(anyString())).thenAnswer(new Answer() {
Object answer(InvocationOnMock invocation) {
Object[] args = invocation.getArguments();
Object mock = invocation.getMock();
return "called with arguments: " + args;
}
});
 
// 다음의 문장은 "called with arguments: foo"를 출력합니다.
System.out.println(mock.someMethod("foo"));
```
# 12. void 메소드를 stubbing하기 위한 doThrow() | doAnswer() | doNothing() | doReturn() #
컴파일러가 괄호 안에 void 메소드가 들어가는 것을 좋아하지 않기 때문에 when(Object)와 같은 형태로 void 메소드의 호출을 stubbing 하기 위해서는 다른 접근방법을 사용해야 합니다.

doThrow(Throwable)은 stubVoid(Object) 메소드를 대신해서 사용할 수 있습니다. 이렇게 하는 주된 이유는 가독성을 높이고, doAnswer() 계열의 메소드와 일관성을 유지하기 위해서입니다.

void 메소드에서 예외를 던지도록 stubbing 하고 싶으면 doThrow() 사용하세요.
```
doThrow(new RuntimeException()).when(mockedList).clear();

// 다음의 코드는 RuntimeException을 던집니다.
mockedList.clear();
```
다른 메소드에 대해서도 확인해보세요.

  * doThrow(Throwable)
  * doAnswer(Answer)
  * doNothing()
  * doReturn(Object)

# 13. 실제 객체 감시하기 #
Mockito에서는 real 객체에 대한 스파이를 생성할 수 있습니다. 만일 스파이를 호출하게 되면 실제 method가 호출됩니다. (method가 stub 되지 않았을 경우)

스파이는 가끔씩만, 주의해서 사용해야 합니다. (예 : 레거시 코드 처리시)

real 객체를 감시하는 것은 "partial mocking"이라는 개념과 연관되어 있습니다. partial mocking은 code smell 의 일종이라고 여겨졌기 때문에 1.8 버전 이전에는 스파이는 mock의 일부분이 아니었습니다. 하지만 관점에 따라 partial mock의 사용이 적절하기도 하다는 것을 알게 되었습니다. (써드 파티 라이브러리 인터페이스, 레거시 코드를 리팩토링 하기 위한 코드.. 전체 아티클을 보기 위해서는 여기를 클릭하세요.)
```
List list = new LinkedList();
List spy = spy(list);
// 특정 메소드만 stub 하는 것이 가능하다.
when(spy.size()).thenReturn(100);
 
// 스파이를 이용해 real method를 호출하기
spy.add("one");
spy.add("two");
 
// 리스트의 첫 번째 element인 "one"을 출력해라
System.out.println(spy.get(0));
 
// size() 가 stub 되었으므로 100이 출력된다.
System.out.println(spy.size());
 
// 검증도 가능
verify(spy).add("one");
verify(spy).add("two");
```
real object를 감시하는 것에 대해 알아야 할 중요한 점

1. 가끔 stubbing된 스파이에 대해 when(Object)를 사용할 수 없습니다. 예를 들면:
```
   List list = new LinkedList();
   List spy = spy(list);
   
   // real method가 호출되면, spy.get(0)은 IndexOutOfBoundsException 예외를 발생시킨다. (list는 아직 비어있으므로)
   when(spy.get(0)).thenReturn("foo");
   
   // doReturn을 이용해 stubbing해야 한다.
   doReturn("foo").when(spy).get(0);
```

2. final method는 조심해야 합니다. Mockito는 final method를 mock 으로 만들지 않기 때문에 real object를 감시하면서 final 메소드를 stub하면 문제가 발생합니다. 실제 객체가 아닌 spy에 넘겨준 mock의  method가 호출됩니다. mock 객체는 필드를 초기화 하지 않았기 때문에 일반적으로 NullPointerException이 발생합니다.

# 14. stubbing 되지 않은 메소드에 기본 리턴값 설정하기 (1.7 이상) #
// Mockito를 이용하면 여러분은 mock의 리턴값에 여러가지 기법들을 정의할 수 있습니다. 이것은 매우 진보된 기능이지만, 좋은 테스트를 만들기 위해 꼭 필요한 기능은 아닙니다. 하지만 레거시 시스템에 대해 작업할 때 매우 유용하게 사용될 수 있습니다.

이것은 method 호출에 대한 기본적인 리턴값이므로 stub되지 않은 method가 호출될 때에만 리턴됩니다.

```
Foo mock = mock(Foo.class, Mockito.RETURNS_SMART_NULLS);
Foo mockTwo = mock(Foo.class, new YourOwnAnswer()); 
```
Answer의 구현에 대해 관심이 있으면 RETURNS\_SMART\_NULLS 를 읽어보세요.

# 15. 파라미터 검증하기 (1.8.0 이상) #
Mockito는 자연스러운 자바 스타일(equals() 같은 메소드를 이용하는 방법)로 파라미터를 검증합니다. 이렇게 함으로써 테스트가 깔끔하게 유지되므로 사용하는 것을 추천합니다. 하지만 어떤 상황에서는 특정 파라미터가 실제 method 호출이 된 이후에 비교되는 것이 더 유용합니다. 예를 들면 :
```
ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
verify(mock).doSomething(argument.capture());
assertEquals("John", argument.getValue().getName());
```
주의: ArgumentCaptor를 검증용으로만 사용해야지 stubbing 용으로는 사용하면 안 됩니다. ArgumentCaptor는 assert 블록 바깥쪽에서 만들어지기 때문에 stubbing할 때 ArgumentCaptor를 사용하면 테스트 가독성이 떨어지게 됩니다. 또, stubbing 된 method가 호출되지 않으면 아무런 파라미터도 잡히지 않기 때문에 결함의 범위를 넓히게 됩니다.

어떤 면에서는 ArgumentCaptor는 custom argument matcher와 관련이 있습니다. (ArgumentMatcher 클래스 자바독을 참조). 두 가지 모두 특정 파라미터가 mock에게 전달되었는지 확인하는 용도로 사용될 수 있습니다. 하지만, 다음과 같은 점 때문에 ArgumentCaptor가 더 유용합니다.
  * custom argument matcher 는 거의 재사용할 수 없다.
  * 완벽하게 검증하기 위해서는 파라미터의 값을 검증할 필요가 있다.

보통은 ArgumentMatcher를 통해 사용하는 custom argument matcher가 stubbing하는데 훨씬 유용합니다.

# 16. Real partial mocks #
결국 메일링 리스트를 통한 내부 토론을 거친 결과, partial mock 지원 기능이 Mockito에 추가되었습니다. 예전에 우리는 partial mock을 code smell로 여겼습니다. 하지만, 우리는 partial mock을 사용해야 하는 적당한 상황을 발견했습니다. - 좀 더 알고 싶다면 여기를 클릭

1.8버전 이전에는 spy()가 real partial mock을 만들지 않았고, 몇몇 사용자들을 혼란시켰습니다. 스파이에 대해 더 알고 싶으면 여기 또는 자바독의 spy(Object) 부분을 읽어보세요.
```
// spy() method를 이용해 partial mock을 생성할 수 있다.
List list = spy(new LinkedList());
    
// mock에 대해 선택적으로 partial mock 기능이 동작하게 만들 수 있다.
Foo mock = mock(Foo.class);
// 실제 구현이 안전하다는 것을 확신할 수 있다.
// 만일 실제 구현이 예외를 던지거나 객체의 특정 상태에 의존하고 있다면 문제가 생길 것이다.
when(mock.someMethod()).thenCallRealMethod();
```
# 17. mock 다시 설정하기 (1.8.0 이상) #
현명한 Mockito 사용자들은 이 기능의 사용이 안 좋은 테스트의 징조라는 것을 알기 때문에 이 기능을 거의 사용하지 않을 것입니다. 보통은 mock을 리셋해서 사용하지 않고 각각의 테스트를 위해 새로운 mock을 생성합니다.

reset()을 사용하기 보다는 지나치게 길거나 지나치게 자세한 테스트를 고쳐서 간결하고 한 가지만 테스트하는 테스트로 바꾸시길 바랍니다. code smell일 가능성이 제일 높은 경우는 테스트 method 중간에서 reset()을 사용한 경우입니다. 이것은 당신이 너무 많은 것을 테스트하려고 하고 있다는 것을 알려줍니다. 테스트 method가 하는 얘기를 잘 들어보세요.:"하나의 기능에만 집중가능하고, 사이즈가 작게 유지해주세요". mockito 메일링 리스트에 이에 대한 몇 개의 글이 있습니다.

우리가 reset()을 추가한 이유는 컨테이너가 주입한 mock이 있는 경우에도 작업이 가능해야 하기 때문입니다. (55번 이슈 또는 FAQ를 보세요)

자신에게 해가 되는 짓을 하지 마세요! 테스트 중간에 나오는 reset()은 code smell 입니다.
```
List mock = mock(List.class);
when(mock.size()).thenReturn(10);
mock.add(1);
   
reset(mock);
// 이 시점에서 mock에 기록된 모든 stubbing과 interaction은 사라집니다.
```

# 18. Troubleshooting & validating framework usage (1.8.0 이상) #
우선, 어떤 문제가 발생하더라도 우리는 여러분들이 Mockito FAQ를 읽어볼 것을 권장합니다. : http://code.google.com/p/mockito/wiki/FAQ

질문하고 싶은 것이 있다면 mockito 메일링 리스트에 남겨주세요. : http://groups.google.com/group/mockito

Mockito는 항상 당신이 올바르게 사용하고 있는지 검증해줍니다. 그렇지만, validateMockitoUsage()를 읽어주시면 감사하겠습니다.

# 19. Aliases for behavior driven development (1.8.0 이상) #
Behavior Driven Development 스타일의 테스트 작성방법은 테스트 method에 기본으로 //given //when //then 이라고 주석을 달아두는 것입니다. 이렇게 함으로써 우리가 어떻게 테스트를 작성해야 하고, 여러분이 이렇게 만들도록 장려할 수 있습니다.

BDD에 대해 배우고 싶다면 여기를 참조하세요. : http://en.wikipedia.org/wiki/Behavior_Driven_Development

문제는 when을 이용한 stubbing이 //given //when //then 주석에 잘 맞아떨어지지 않는다는 것입니다. 왜냐하면 stubbing이 when이 아닌 given에 속하기 때문입니다. 그러므로 BDDMockito 클래스는 stub을 할 때 사용할 메소드로 BDDMockito.given(Object)를 제공합니다. 이 method를 이용해 의미가 잘 통하는 BDD 스타일의 테스트를 만들 수 있게 되었습니다.

테스트가 어떻게 생겼는지 보여주는 예제가 있습니다.:
```
import static org.mockito.BDDMockito.*;
 
Seller seller = mock(Seller.class);
Shop shop = new Shop(seller);

public void shouldBuyBread() throws Exception {
//given  
given(seller.askForBread()).willReturn(new Bread());
  
//when
Goods goods = shop.buyBread();
  
//then
assertThat(goods, containBread());
}
```
# 20. mock 직렬화하기 (1.8.1 이상) #
Mock은 직렬화 가능합니다. 이러한 특징을 이용해 우리는 의존성을 직렬화 가능하게 만들어야 하는 상황에서도 mock을 사용할 수 있습니다.

경고 : 이 기능은 단위 테스트에서는 사용되지 않았으면 합니다.

이 기능은 믿을 수 없는 외부자원에 대한 의존성을 가진 BDD 스펙의 유즈 케이스를 지원하기 위해 구현되었습니다. 웹 환경이나 외부 자원에 대한 의존성을 가진 객체를 레이어 간에 주고 받기 위해 직렬화 해야 하는 경우입니다.

직렬화 가능한 mock을 생성하려면 MockSettings.serializable()을 사용하시면 됩니다.
```
List serializableMock = mock(List.class, withSettings().serializable());
```
직렬화하기 위한 모든 요구사항이 만족되었다는 가정 하에 이 mock은 직렬화 가능합니다.

진짜 객체의 스파이를 직렬화 가능하게 만드는 것은 spy(...) method에 MockSettings를 넘겨줄 수 있도록 오버로딩된 버전이 없기 때문에 조금 어렵습니다. 하지만 그런 기능을 거의 사용하지 않을 것이므로 걱정하지 않으셔도 됩니다.
```
List list = new ArrayList();
List spy = mock(ArrayList.class, withSettings()
.spiedInstance(list)
.defaultAnswer(CALLS_REAL_METHODS)
.serializable());
```
# 21. 새로운 어노테이션 : @Capture, @Spy, @InjectMocks (1.8.3 이상) #
1.8.3 버전부터 종종 유용하게 사용할 수 있는 annotation이 추가되었습니다.

  * @Captor 는 ArgumentCaptor 생성을 간략화 시켰습니다. – 잡아야 하는 파라미터가 generic 클래스 이고, 컴파일러 에러를 피하고 싶을 때 유용
  * @Spy - spy(Object)대신에 사용할 수 있습니다.
  * @InjectMocks - 테스트 될 객체에 mock을 자동으로 넣어줍니다.

새로운 annotation들은 오직 MockitoAnnotations.initMocks(Object)에서만 사용 가능합니다.

# 22. 타임아웃 확인하기 (1.8.5 이상) #
타임아웃을 검증하는 기능이 추가되었습니다. 프로그램이 동시에 실행되는 경우에 대해 테스트할 때 유용할 것이라고 생각됩니다.

이 기능은 거의 사용되지 않아야 하지만, 멀티 스레드 시스템을 테스트할 때에는 도움이 될 것입니다.

InOrder(순서대로 실행되는지 체크)를 이용한 검증은 아직 구현되지 않았습니다.
```
예제:
// 주어진 시간 안에 someMethod()가 호출되면 성공
verify(mock, timeout(100)).someMethod();
// 위의 코드는 아래와 같이 변경될 수 있다.
verify(mock, timeout(100).times(1)).someMethod();
   
// 주어진 시간 안에 someMethod()가 정확히 2번 호출되면 성공
verify(mock, timeout(100).times(2)).someMethod();

//주어진 시간 안에 someMethod()가 적어도 2번 호출되면 성공
verify(mock, timeout(100).atLeast(2)).someMethod();
   
// 주어진 시간 안에 someMethod()가 지정된 검증 방식을 통과하면 성공
// 자신만의 검증 방식이 만들어져 있다면 유용한 방식이다.
 verify(mock, new Timeout(100, yourOwnVerificationMode)).someMethod();
```
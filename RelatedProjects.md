# Related projects #

## Springockito ##
A Spring framework extension that simplifies creation of the mocks in the spring xml context files, ex: ` <mockito:mock class="java.util.Date" id="mockedDate" /> <mockito:spy id="beanToBeSpied" /> `
https://bitbucket.org/kubek2k/springockito/wiki/Home

## Springockito-annotations ##
An Spring framework extension that pushes former approach even further - all the configuration is annotations based - no need to create special mock overriding contexts. More info here: https://bitbucket.org/kubek2k/springockito/wiki/springockito-annotations

## Mockito-collections ##
Project to investigate the possibility of injecting objects into Collections using generics to determine which objects and mock especially can be placed into the Collections.
```
@InjectMocks private MyDelegate delegate;
@Mock private MyListener listener1;
@Mock private MyListener listener2;

@Before public void setup() { MockitoCollectionAnnotations.inject(this);}
```
See here to read more : https://github.com/jameskennard/mockito-collections/
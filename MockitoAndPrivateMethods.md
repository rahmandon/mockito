Why Mockito doesn't mock private methods?

Firstly, we are not dogmatic about mocking private methods. We just don't care about private methods because from the standpoint of testing private methods don't exist. Here are a couple of reasons Mockito doesn't mock private methods:

  1. It requires hacking of classloaders that is never bullet proof and it changes the api (you must use custom test runner, annotate the class, etc.).
  1. It is very easy to work around - just change the visibility of method from private to package-protected (or protected).
  1. It requires me to spend time implementing & maintaining it. And it does not make sense given point #2 and a fact that it is already implemented in different tool (powermock).
  1. **Finally...** Mocking private methods is a hint that there is something wrong with OO understanding. In OO you want objects (or roles) to collaborate, not methods. Forget about pascal & procedural code. Think in objects.
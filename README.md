# RxJavaGoT

This our example app of how we built a live dashboard for the Iron Bank of Braavos.  It contains examples of how to observe multiple streams and display them in varying ways through the use of RxJava functions.

Some of the functions used are:
- map
- filter
- dispose
- merge
- combineLatest
- distinctUntilChanged

The code is also built using Clean Architecture with Repositories, Providers, and View layers.  Dependency injection is managed using Dagger.

Our slides are limited, as we primarily live coded the talk.  However, here they are if you're interested!
https://docs.google.com/presentation/d/1PaPi1Tm7z5hoFKiX-W-AF1ICKMSYLUwtXgB72rrGeLs/edit?usp=sharing

Other helpful links are:
- http://rxmarbles.com/
- https://blog.mindorks.com/
- https://caster.io/
- https://github.com/aderington/RxExamples (Hopefully more to come, but this is an example of RetryWhen)

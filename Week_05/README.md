# 1. Spring Bean 的装配

代码加模块 `spring-bean`

1. `XmlBean` 使用 `bean` 标签, 在 `spring-service.xml` 里配置

2. `ComponentBean` 通过注解 `Component` 装配, 
spring-boot 会自动扫描 application 包下的类, 同样也可以在 xml 文件里配置自动扫描

3. `BeanAnnotationBean` 这个是通过 `Bean` 注解来装配的，可以理解为将 xml 变成了代码

4. 也可以弄个 BeanDefinition 然后往 BeanFactory 里仍

5. 也可以自定义 Scanner 进行扫描

这里简单写了，还有优先级、初始化方法、destroy 方法、在某个bean初始化之后执行等等

对于Bean的注入:

1. xml 里通过 ref 属性注入

2. Autowire 注入

3. Resource、Inject 注入, 推荐这个, 这是 JSR 标准

4. 也可以通过 ApplicationContextAware 等类拿到 BeanFactory, 然后拿到需要的类

# 2. 自动装配

代码模板是 `starter-parent`

简单步骤如下:

1. 建一个 `AutoConfiguration` 结尾的类, 当做一个不成文的约定吧

2. 加上注解 `Configuration`

3. 在 `META-INF/spring.factories` 里声明下

在实际的情况下, 会用到下面的技术

1. `ConditionalOnXXX` 这种注解, 用于各种条件判断，最常用的就是监测到类就自动启动，
但是这种很坑，有的时候只是引入了jar包，但没有使用，一是浪费资源，二是有的还有 heathcheck 检查，
默认情况下，一个 healthcheck 挂了整个应用就下线了，最终导致间接引入了一个没有用的jar却让服务下线了
 
2. 为了更灵活的能关闭，一般用 `ConditionalOnProperty` 注解，然后 `value` 置为 `enable`, 这个时候spring-boot会特殊处理，
当成一种开关。在配置文件里配置为 false 就能关闭

3. 有的时候还可以使用 `EnableConfigurationProperties` 等解析配置的注解，通过配置文件提供更灵活的配置方式。使用这种方式的时候，最好在配置文件
 `META_INF/additional-spring-configuration-metadata.json` 里声明一下有哪些配置项，这个是可选的，没有这个也可以跑，但是这个文件可以说明
 有哪些配置、配置的作用、配置的类型，而且 idea 也能做很好的提示。实际的坑：spring-cloud 有些配置项，未在配置文件里声明，导致要阅读全部的源码，才能知道哪些配置项才能用


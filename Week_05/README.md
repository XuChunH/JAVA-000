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
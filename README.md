# webflux_feign

#### 介绍
针对webflux实现类似@FeignClient、dubbo:service组件的功能，通过暴露访问的接口，生成spring的代理类，调用接口方法时，封装请求到服务端

#### 软件架构
软件架构说明
基于spingboot2+webflux+r2dbc-mysql+jdk动态代理实现的组件

工程总体结构如下图示，

    |-- webflux
        |-- webflux-server-annotation    @WebFluxClient注解包
        |-- webflux-server-invoke  
            核心，通过@EnableWebfluxClientInvoke注解开启WebfluxClientInvoke的功能，参数componentScan为扫描@WebFluxClient注解的包路径
            扫描@WebFluxClient注解并构造GenericBeanDefinition注入到容器，解析注解类通过代理工厂生产代理类，调用接口时代理类发起远程调用
        |-- webflux-server-api  暴露的接口api以及dto
        |-- webflux-server  webflux服务端
        |-- webflux-client  webflux客户端
        
webflux-client添加webflux-server-invoke依赖
 1.@EnableWebfluxClientInvoke启用WebfluxClientInvoke的功能，配置包扫描
 
 
    @EnableWebfluxClientInvoke(componentScan = "com.giggle.webflux.api")
    @SpringBootApplication
    public class WebfluxClientApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(WebfluxClientApplication.class, args);
        }
    
    }
  
 2.导入WebfluxImportBeanDefinitionRegister，获取包扫描信息
 
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Import({WebfluxImportBeanDefinitionRegister.class,FluxHandlerBeanDefinitionRegistry.class})
    public @interface EnableWebfluxClientInvoke {
    
        /**
         * 包扫描路径，扫描@WebFluxClient
         * @return
         */
        String componentScan()  default "";
    }



 3.导入FluxHandlerBeanDefinitionRegistry
     a.实现BeanDefinitionRegistryPostProcessor，扫描到特定注解的类
     b.实现FactoryBean，为添加注解的接口类生成代理对象，getObject的时候返回代理对象
     c.将FactoryBean设置到GenericBeanDefinition，注入到容器中
     
     
           @Override
              public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
                  /**
                   * 获取@WebFluxClient注解的接口，这些接口就需要通过动态代理提供默认实现
                   */
                  Set<Class<?>> classes = getAutoImplClasses();
                  for (Class<?> clazz : classes) {
                      /**生成GenericBeanDefinition 并注入到容器*/
                      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
                      GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
                      definition.getPropertyValues().add(INTERFACE_CLASS, clazz);
                      //设置factoryBean 用于生成代理类
                      definition.setBeanClass(HandlerInterfaceFactoryBean.class);
                      definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
                      //注册bean
                      beanDefinitionRegistry.registerBeanDefinition(decapitalize(clazz.getSimpleName()), definition);
                  }
              }
          
          
 4.调用接口方法执行代理对象的方法发起远程调用


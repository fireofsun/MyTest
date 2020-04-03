import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CGLIBTest {

    public static void main(String[] args) {
        //CGLIB enhancer增强类对象
        Enhancer enhancer = new Enhancer();
        //设置增强类的类型
        enhancer.setSuperclass(Man.class);
        //设置代理方法，需要用接口MethodInterceptor的实现类，重写intercept方法
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects,
                                    MethodProxy methodProxy) throws Throwable {
                //输出Object o的类类型，输出：class Man$$EnhancerByCGLIB$$c01d0622，
                //该类就是enhancer创建出的增强类本身，同下。
                //System.out.println(o.getClass());
                //得到调用方法名称，输出：show
                String methodName = method.getName();
                //System.out.println(methodName);
                //得到被代理类（父类）的方法的名称，输出：CGLIB$show$0
                String superName = methodProxy.getSuperName();
                //System.out.println(superName);

                if (methodName.equals("toString")) {
                    //调用父类方法，可以在此方法上下写入增强语句
                    System.out.println("你想看看我吗？");
                    return methodProxy.invokeSuper(o, objects);
                }
                if (methodName.equals("show")) {
                    System.out.println("我其实想穿女装");
                    return methodProxy.invokeSuper(o, objects);
                }
                return methodProxy.invokeSuper(o, objects);
            }
        });
        //创建GCLIB代理对象
        Man girlMan = (Man) enhancer.create();
        //输出：Man$$EnhancerByCGLIB$$c01d0622@244038d0，说明创建的对象是Man的子类
        //System.out.println(girlMan.toString());
        //调用加强方法
        girlMan.show();
        //输出：class Man$$EnhancerByCGLIB$$c01d0622，同上
        //System.out.println(girlMan.getClass());
    }

}
class Man {

    public void show() {
        System.out.println("我是男人");
    }

}
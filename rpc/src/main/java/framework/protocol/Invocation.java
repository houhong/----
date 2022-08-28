package framework.protocol;

/**
 * @program: algorithm-work
 * @description: 注入信息
 * @author: houhong
 * @create: 2022-08-28 18:26
 **/
public class Invocation {

    /**
     * 接口名字
     **/
    private String interfaceClass;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 参数类型
     **/
    private Class[] paramsTypes;

    /**
     * 参数
     **/
    private Object[] params;



    public Invocation() {
    }

    public Invocation(String interfaceClass, String methodName, Class[] paramsTypes, Object[] params) {
        this.interfaceClass = interfaceClass;
        this.methodName = methodName;
        this.paramsTypes = paramsTypes;
        this.params = params;
    }

    public String getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(String interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamsTypes() {
        return paramsTypes;
    }

    public void setParamsTypes(Class[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
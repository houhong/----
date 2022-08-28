package framework.registry;


/**
 * 注册表
 **/
public interface Registry {


    /**
     * @param impl            实现类
     * @param targetClassName 类名
     * @Description //TODO  用来进行注册
     **/
    public  void registry(String targetClassName, Class impl);

    /**
     * @param targetClassName 类名
     * @Description //TODO  获取实现类
     **/
    public  Class getClass(String targetClassName);

}

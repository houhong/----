package framework.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: algorithm-work
 * @description: 本地注册表
 * @author: houhong
 * @create: 2022-08-28 18:44
 **/
public class LocalRegistry  {

    private static  final  Map<String,Class> LOCAL_REGISTRY  = new ConcurrentHashMap<>(16);



    public  static  void registry(String targetClassName, Class impl) {

        LOCAL_REGISTRY.put(targetClassName, impl);
    }

    public static  Class   getClass(String targetClassName) {

        return LOCAL_REGISTRY.get(targetClassName);
    }
}
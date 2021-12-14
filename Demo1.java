public class ObjectAdapter {
    /**客户端*/
    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.setCapacitance(new Adapter(new PowerSupply220()));
        System.out.println("手机电量为 = " + phone.getCapacitance());
    }

}
/**src(source: 源)*/
public class PowerSupply220 {
    /**默认电压*/
    protected int voltage = 220;

    public int getVoltage() {
        System.out.println("电源提供了【" + voltage + "】伏电压");
        return this.voltage;
    }
}
/**手机充电器*/
public class Adapter implements IPowerSupply5 {
    /**聚合电源类，不再使用继承方式 */
    private PowerSupply220 powerSupply220;
    /**构造方法: 传入220V电压的电源 */
    public Adapter(PowerSupply220 powerSupply220) {
        this.powerSupply220 = powerSupply220;
    }

    @Override
    public int output5v() {
        if (this.powerSupply220 != null) {
            return powerSupply220.getVoltage() / 44;
        } else {
            throw new RuntimeException("请将适配器插上电源...");
        }
    }
}
/**dst(destination: 源)*/
public interface IPowerSupply5 {
    /**输出5伏的电压*/
    int output5v();
}
/**需要5v电压充电器(适配器)给手机充电*/
public class Phone {
    private int capacitance = 0;
    public void setCapacitance(IPowerSupply5 powerSupply5) {
        int dian = powerSupply5.output5v();
        if (dian <= 5) {
            this.capacitance = dian;
        } else {
            throw new RuntimeException("电压过高, 手机充不上电");
        }
    }
    public int getCapacitance() {
        if (capacitance == 0) {
            throw new RuntimeException("电量为0, 已关机");
        }
        return this.capacitance;
    }


    private static void getClassLoader() {
        // 获取系统类加载器；又叫应用类加载器
        ClassLoader applicationClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(applicationClassLoader); // 应用类加载器 sun.misc.Launcher$AppClassLoader@18b4aac2

        // 获取系统类加载器的上级加载器
        ClassLoader extendClassLoader = applicationClassLoader.getParent();
        System.out.println(extendClassLoader); // 扩展类加载器 sun.misc.Launcher$ExtClassLoader@574caa3f

        // 获取扩展类加载器的上级加载器：
        ClassLoader bootstrapClassLoader = extendClassLoader.getParent();
        System.out.println(bootstrapClassLoader); // 引导类加载器 null : 由于不是通过java实现的, 因此获取到的上级加载器结果为 null

        // 获取当前类的类加载器
        ClassLoader appClassLoader = ClassLoaderDemo.class.getClassLoader();
        System.out.println(appClassLoader); // 与系统类加载器地址值相同, 表明系统类加载器是单例模式, sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(appClassLoader == applicationClassLoader); // true

        // 结论: java.lang包下的类加载器均为引导类加载器
        ClassLoader stringClassLoader = String.class.getClassLoader();
        System.out.println(stringClassLoader); // null
        ClassLoader integerClassLoader = Integer.class.getClassLoader();
        System.out.println(integerClassLoader); // null

        // 系统类加载器 和 扩展类加载器 均由 引导类加载器加载进来的
        ClassLoader classLoader1 = appClassLoader.getClass().getClassLoader();
        System.out.println(classLoader1); // null
        ClassLoader classLoader2 = extendClassLoader.getClass().getClassLoader();
        System.out.println(classLoader2); // null
    }

    public static void getBootstrapClassLoader() {
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.getFile());
        }
        //jre/lib/resources.jar
        // jre/lib/rt.jar
        // jre/lib/sunrsasign.jar
        // jre/lib/jsse.jar
        // jre/lib/jce.jar
        // jre/lib/charsets.jar
        // jre/lib/jfr.jar
        // jre/classes
    }
public static void getExtClassLoader() {
    // 通过系统属性 "java.ext.dirs" 进行获取, 则可以获取到 扩展类加载器 能加载到的类
    String property = System.getProperty("java.ext.dirs");
    String[] paths = property.split(";");
    for (String path : paths) {
        System.out.println(path); 
    }
    // D:\tools\software\java\tools\jdk_1.8.0_231\jre\lib\ext
    // C:\WINDOWS\Sun\Java\lib\ext   
}

}
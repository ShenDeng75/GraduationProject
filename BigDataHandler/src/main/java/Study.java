import jdk.nashorn.internal.runtime.PropertyMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/3/1 23:05
 */
public class Study {

    public static void main(String[] args) {
        List<String> edus = new ArrayList<>(Arrays.asList("博士", "硕士", "本科", "大专", "高中", "中专"));
        List<String> strings = new ArrayList<>(Arrays.asList("阿瑟发", "asdf", "大是大非", "东方饭店"));
        boolean b = strings.retainAll(edus);
        System.out.println(b);
        System.out.println(strings);
    }
}

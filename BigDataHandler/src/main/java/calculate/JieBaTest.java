package calculate;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/4/17 11:36
 */
public class JieBaTest {

    // 前缀
    private static List<String> pre = new
            ArrayList<>(Arrays.asList("了解", "熟练", "掌握", "熟练掌握", "精通", "熟悉",
            "了解", "较强的", "良好的", ""));
    // 前缀尾
    private static List<String> pre_end = new
            ArrayList<>(Arrays.asList("等", ".", "。", ";", "；"));
    // 后缀
    private static List<String> pos = new
            ArrayList<>(Arrays.asList("经验", "优先", "能力"));
    // 后缀头
    private static List<String> pos_start = new
            ArrayList<>(Arrays.asList("的", ",", "，", ".", "。", "、", ":", "：", " "));
    // 结果
    private static List<String> result = new ArrayList<>();

    public static void nomal() {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        WordDictionary.getInstance().init(Paths.get("conf"));
        String sentences = "岗位职责： 1.审视、规划系统整体技术架构评，估外部技术与解决方案； 2.制定开发规范，接口规范，接口规范等。 2.主导系统架构设计和优化，以及性能调优； 3.架构设计保证系统安全，比如客户端安全，认证安全，脚本安全，防sql注入，数据库安全，SSL安全传输等。 4.参与核心模块的编码开发； 5.指导高级软件工程师的工作； 8.提升团队的技术分析，设计和编码能力。 任职要求： 1.五年以上Java开发经验；两年年以上架构设计经验，了解阿里云或者华为云相关技术，了解互联网的技术发展，了解微服务； 2.熟练掌握spring mvc，redis，mq，zookeeper，dubbo，logstash等互联网框架，并具有丰富的性能调优经验； 3.精通各种中间件的设计及应用如JEE，Messaging，Workflow,Cache，及数据层； 4.精通Linux 操作系统和mysql数据库；有较强的分析设计能力和方案整合能力； 5.了解敏捷开发，良好的沟通技能，团队合作能力。 6.有互联网项目设计经验，有大数据量，大并发经验优先。 7、3-7年工作经验； 8、2-3年电商／互联网工作经验。";
        List<String> strings = segmenter.sentenceProcess(sentences.toLowerCase());
        for (int i = 0; i < strings.size(); i++) {
            // 包含前缀
            if (pre.contains(strings.get(i))) {
                int j = i + 1;
                StringBuffer temp = new StringBuffer();
                // 包含前缀尾
                while (!pre_end.contains(strings.get(j)) && j < strings.size()) {
                    temp.append(strings.get(j++));
                }
                result.add(temp.toString());
                i = j + 1;
            }
            // 包含后缀
            if (pos.contains(strings.get(i))) {
                int j = i - 1;
                StringBuffer temp = new StringBuffer();
                // 包含后缀头
                while (!pos_start.contains(strings.get(j)) && j > 0) {
                    temp.insert(0, strings.get(j--));
                }
                result.add(temp.toString());
            }
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        nomal();
    }
}

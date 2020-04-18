package calculate;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;

import java.nio.file.Paths;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/4/17 11:36
 */
public class JieBaTest {

    public static void index(){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String[] sentences =
                new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
                        "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
        for (String sentence : sentences) {
            System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX).toString());
        }
    }

    public static void nomal(){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String sentences = "北京天威科技发展有限公司大庆车务段的装车数量";
        WordDictionary.getInstance().init(Paths.get("conf"));
        System.out.println(segmenter.sentenceProcess(sentences));
    }

    public static void main(String[] args) {
        nomal();
    }
}

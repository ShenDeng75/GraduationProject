package calculate;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/4/23 22:16
 */
public class CalReducer extends Reducer<CalKey, Text, Text, Text> {
    private Map<Integer, String> map;

    @Override
    protected void reduce(CalKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String url = key.getUrl();
        String needs = "NaN";
        for (Text value : values) {
            needs = value.toString();
        }
        String result = splitNeed(needs);
        context.write(new Text(url), new Text(result));
    }

    /**
     * 分词，获取需求关键字
     *
     * @param needs 招聘信息中的需求
     * @return 分词后的关键字
     */
    private String splitNeed(String needs) {
        String ans = "";
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokens = segmenter.process(needs.toLowerCase(), JiebaSegmenter.SegMode.INDEX);
        if (segTokens.size() == 0) {
            return ans;
        }
        // 构建索引
        map = new HashMap<>();
        index(segTokens);
        List<String> result = new ArrayList<>();
        // 获取关键字
        String pattern = "[a-z]+";
        SegToken last = null;
        for (SegToken curr : segTokens) {
            if (Pattern.matches(pattern, curr.word)) {
                if (last != null) {
                    if (isSequential(last, curr)) {
                        last.word = last.word + curr.word;
                        last.endOffset = curr.endOffset;
                    } else {
                        result.add(last.word);
                        last = curr;
                    }
                } else {
                    last = curr;
                }
            }
        }
        // 如果没有关键字
        if (last == null) {
            return "NaN";
        }
        result.add(last.word);
        //去重
        Set<String> set = new HashSet<>(result);
        result = new ArrayList<>(set);
        ans = String.join("\t", result);
        return ans;
    }

    /**
     * 构建索引
     *
     * @param segTokens 列表
     */
    private void index(List<SegToken> segTokens) {
        for (SegToken segToken : segTokens) {
            map.put(segToken.startOffset, segToken.word);
        }
    }

    /**
     * 判断两个单词是否连续, 并且中间是空格
     *
     * @param front 前一个
     * @param later 后一个
     * @return 是否连续
     */
    private boolean isSequential(SegToken front, SegToken later) {
        if (later.startOffset - front.endOffset != 1) {
            return false;
        }
        return " ".equals(map.get(front.endOffset));
    }
}

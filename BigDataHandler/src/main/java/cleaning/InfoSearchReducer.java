package cleaning;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/4/17 15:40
 */
public class InfoSearchReducer extends Reducer<RecruitInfo, NullWritable, RecruitInfo, NullWritable> {
    private List<String> edus = new ArrayList<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        edus.addAll(Arrays.asList("博士", "硕士", "本科", "大专", "高中", "中专"));
    }

    /**
     * 补全 education字段
     *
     * @param need_skill 技能需求
     * @return 从技能需求中提取的edu
     */
    private String getEdu(String need_skill) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> strings = segmenter.sentenceProcess(need_skill);
        strings.retainAll(edus);
        if (strings.size() != 0) {
            return strings.get(0);
        }
        return "缺失";
    }

    @Override
    protected void reduce(RecruitInfo key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        // 如果学历字段为脏数据
        if (!edus.contains(key.getEducation())) {
            String edu = getEdu(key.getNeed_skill());
            key.setEducation(edu);
            System.out.println(key.getUrl() + " : " + edu);
        }
        context.write(key, NullWritable.get());
    }
}

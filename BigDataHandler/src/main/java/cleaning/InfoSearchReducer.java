package cleaning;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.nio.file.Paths;
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
        edus.addAll(Arrays.asList("博士", "硕士", "本科", "专科", "大专", "高中", "中专", "大学本科", "大学专科"));
    }

    /**
     * 补全 education字段
     *
     * @param need_skill 技能需求
     * @return 从技能需求中提取的edu
     */
    private String getEdu(String need_skill) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        WordDictionary.getInstance().init(Paths.get("conf"));
        List<String> strings = segmenter.sentenceProcess(need_skill);
        strings.retainAll(edus);
        if (strings.size() != 0) {
            String edu = strings.get(0);
            if (edu.length() == 4){
                return edu.substring(2, 4);
            }
            return edu;
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

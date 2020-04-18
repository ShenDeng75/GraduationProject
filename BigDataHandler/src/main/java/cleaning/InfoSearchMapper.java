package cleaning;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/4/17 15:39
 */
public class InfoSearchMapper extends Mapper<LongWritable, Text, RecruitInfo, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String str = value.toString();
        String[] strInfo = str.split("\t");
        RecruitInfo recruitInfo = new RecruitInfo();

        recruitInfo.setCategory(strInfo[0]);
        recruitInfo.setTitle(strInfo[1]);
        recruitInfo.setSalary(strInfo[2]);
        recruitInfo.setPlace(strInfo[3]);
        recruitInfo.setExperience(strInfo[4]);
        recruitInfo.setEducation(strInfo[5]);
        recruitInfo.setNeed_persons(strInfo[6]);
        recruitInfo.setPublish_date(strInfo[7]);
        recruitInfo.setUrl(strInfo[8]);
        recruitInfo.setNeed_skill(strInfo[9]);

        context.write(recruitInfo, NullWritable.get());
    }
}

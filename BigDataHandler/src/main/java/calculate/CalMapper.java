package calculate;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/4/23 22:15
 */
public class CalMapper extends Mapper<LongWritable, Text, CalKey, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String str = value.toString();
        String[] strs = str.split("\t");
        CalKey calKey = new CalKey();
        calKey.setCategory(strs[0]);
        calKey.setUrl(strs[8]);

        context.write(calKey, new Text(strs[9]));
    }
}

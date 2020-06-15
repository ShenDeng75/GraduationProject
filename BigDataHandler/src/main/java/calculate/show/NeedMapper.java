package calculate.show;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/5/12 21:38
 */
public class NeedMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String val = value.toString();
        String[] words = val.split("\t");
        for (int i=1; i<words.length; i++){
            context.write(new Text(words[i]), new IntWritable(1));
        }
    }
}

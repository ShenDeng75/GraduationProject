package calculate.show;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/5/12 21:38
 */
public class NeedReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values){
            sum += value.get();
        }
        context.write(key, new IntWritable(sum));
    }
}

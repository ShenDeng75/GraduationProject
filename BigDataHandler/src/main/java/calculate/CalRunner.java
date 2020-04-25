package calculate;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import utils.FileHelper;

/**
 * @author 肖斌武.
 * @describe 数据计算
 * @datetime 2020/4/23 22:16
 */
public class CalRunner extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        FileHelper.deleteDir(args[1], conf);
        Job job = Job.getInstance(conf, "gradu_calculate");

        job.setMapperClass(CalMapper.class);
        job.setMapOutputKeyClass(CalKey.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(CalReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setNumReduceTasks(4);
        job.setPartitionerClass(Partition.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);
        return result?0:1;
    }

    public static void main(String[] args) throws Exception {
        args = new String[]{"D:\\temp\\inputs\\graduation\\clean", "D:\\temp\\outputs\\graduation\\clean"};
        int result = ToolRunner.run(new CalRunner(), args);
        System.exit(result);
    }
}

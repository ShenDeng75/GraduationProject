package cleaning;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import utils.FileHelper;

/**
 * @author 肖斌武.
 * @describe 根据url去重，并清洗education列
 * @datetime 2020/4/17 15:40
 */
public class InfoSearchRunner extends Configured implements Tool {
    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        FileHelper.deleteDir(args[1], conf);

        Job job = Job.getInstance(conf, "gradu_clean");
        job.setJarByClass(InfoSearchRunner.class);

        job.setMapperClass(InfoSearchMapper.class);
        job.setMapOutputKeyClass(RecruitInfo.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setReducerClass(InfoSearchReducer.class);
        job.setOutputKeyClass(RecruitInfo.class);
        job.setOutputValueClass(NullWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);
        return result ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
         args = new String[]{"D:\\temp\\inputs\\graduation\\clean", "D:\\temp\\outputs\\graduation\\clean2"};
        int result = ToolRunner.run(new InfoSearchRunner(), args);
        System.exit(result);
    }
}

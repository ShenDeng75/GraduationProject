package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/2/26 17:27
 */
public class FileHelper {
    public static void deleteDir(String output, Configuration conf) throws IOException {
        FileSystem fs = FileSystem.get(conf);
        Path outputPath = new Path(output);
        if (fs.exists(outputPath)) {
            fs.delete(outputPath, true);
            System.out.println("删除了已存在的输出文件：" + output);
        }
    }
}

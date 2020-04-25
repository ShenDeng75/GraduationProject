package calculate;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * @author 肖斌武.
 * @describe 根据语言类别Category分区
 * @datetime 2020/4/23 22:22
 */
public class Partition extends Partitioner<CalKey, Text> {
    private static HashMap<String, Integer> partMap = new HashMap<>();
    static {
        partMap.put("java", 0);
        partMap.put("python", 1);
        partMap.put("大数据", 2);
    }

    @Override
    public int getPartition(CalKey calKey, Text text, int numPartitions) {
        String cate = calKey.getCategory().toLowerCase();
        return partMap.getOrDefault(cate, 3);
    }
}

package calculate;

import lombok.Data;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/4/23 22:37
 */
@Data
public class CalKey implements WritableComparable<CalKey> {
    private String category;
    private String url;

    @Override
    public int compareTo(CalKey o) {
        return this.url.compareTo(o.url);
    }

    @Override
    public String toString() {
        return category + "\t" + url;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(category);
        dataOutput.writeUTF(url);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        category = dataInput.readUTF();
        url = dataInput.readUTF();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalKey calKey = (CalKey) o;

        if (category != null ? !category.equals(calKey.category) : calKey.category != null) return false;
        return url != null ? url.equals(calKey.url) : calKey.url == null;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}

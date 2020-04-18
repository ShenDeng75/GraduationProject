package cleaning;

import lombok.Data;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author 肖斌武.
 * @describe
 * @datetime 2020/4/17 15:45
 */
@Data
public class RecruitInfo implements WritableComparable<RecruitInfo> {
    private String category;
    private String title;
    private String salary;
    private String place;
    private String experience;
    private String education;
    private String need_persons;
    private String publish_date;
    private String url;
    private String need_skill;

    @Override
    public int compareTo(RecruitInfo o) {
        // 使用url作为标识
        return url.compareTo(o.url);
    }

    @Override
    public String toString() {
        return category + "\t" +
                title + "\t" +
                salary + "\t" +
                place + "\t" +
                experience + "\t" +
                education + "\t" +
                need_persons + "\t" +
                publish_date + "\t" +
                url + "\t" +
                need_skill;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(category);
        dataOutput.writeUTF(title);
        dataOutput.writeUTF(salary);
        dataOutput.writeUTF(place);
        dataOutput.writeUTF(experience);
        dataOutput.writeUTF(education);
        dataOutput.writeUTF(need_persons);
        dataOutput.writeUTF(publish_date);
        dataOutput.writeUTF(url);
        dataOutput.writeUTF(need_skill);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        category = dataInput.readUTF();
        title = dataInput.readUTF();
        salary = dataInput.readUTF();
        place = dataInput.readUTF();
        experience = dataInput.readUTF();
        education = dataInput.readUTF();
        need_persons = dataInput.readUTF();
        publish_date = dataInput.readUTF();
        url = dataInput.readUTF();
        need_skill = dataInput.readUTF();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecruitInfo that = (RecruitInfo) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (salary != null ? !salary.equals(that.salary) : that.salary != null) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (experience != null ? !experience.equals(that.experience) : that.experience != null) return false;
        if (education != null ? !education.equals(that.education) : that.education != null) return false;
        if (need_persons != null ? !need_persons.equals(that.need_persons) : that.need_persons != null) return false;
        if (publish_date != null ? !publish_date.equals(that.publish_date) : that.publish_date != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return need_skill != null ? need_skill.equals(that.need_skill) : that.need_skill == null;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (experience != null ? experience.hashCode() : 0);
        result = 31 * result + (education != null ? education.hashCode() : 0);
        result = 31 * result + (need_persons != null ? need_persons.hashCode() : 0);
        result = 31 * result + (publish_date != null ? publish_date.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (need_skill != null ? need_skill.hashCode() : 0);
        return result;
    }
}

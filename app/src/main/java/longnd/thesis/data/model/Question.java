package longnd.thesis.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import longnd.thesis.utils.Define;

@Entity(tableName = Define.Question.TABLE_NAME)
public class Question {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Define.Question.ID)
    private Integer id;

    @ColumnInfo(name = Define.Question.NUMBER_ID)
    private Integer numberId;

    @ColumnInfo(name = Define.Question.CONTENT)
    private String content;

    @ColumnInfo(name = Define.Question.IS_REVERSE)
    private Integer reverse;

    @ColumnInfo(name = Define.Question.TYPE)
    private Integer type;

    @ColumnInfo(name = Define.Question.KIND)
    private Integer kind;

    public Question() {
    }

    @Ignore
    public Question(Integer type, Integer numberId, String content, Integer reverse, Integer kind) {
        this.type = type;
        this.numberId = numberId;
        this.content = content;
        this.reverse = reverse;
        this.kind = kind;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberId() {
        return numberId;
    }

    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReverse() {
        return reverse;
    }

    public void setReverse(Integer reverse) {
        this.reverse = reverse;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }
}

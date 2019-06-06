package longnd.thesis.data.model;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import longnd.thesis.utils.Define;

@Entity(tableName = Define.ResultNeo.TABLE_NAME)
public class ResultNeo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Define.ResultNeo.ID)
    private Integer id;

    @ColumnInfo(name = Define.ResultNeo.NEUROTICISM)
    private Integer neuroticism;

    @ColumnInfo(name = Define.ResultNeo.EXTRAVERSION)
    private Integer extraversion;

    @ColumnInfo(name = Define.ResultNeo.OPENNESS)
    private Integer openness;

    @ColumnInfo(name = Define.ResultNeo.AGREEABLENESS)
    private Integer agreeableness;

    @ColumnInfo(name = Define.ResultNeo.CONSCIENTIOUSNESS)
    private Integer conscientiousness;

    public ResultNeo() {
    }

    @Ignore
    public ResultNeo( Integer neuroticism, Integer extraversion, Integer openness, Integer agreeableness, Integer conscientiousness) {
        this.neuroticism = neuroticism;
        this.extraversion = extraversion;
        this.openness = openness;
        this.agreeableness = agreeableness;
        this.conscientiousness = conscientiousness;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNeuroticism() {
        return neuroticism;
    }

    public void setNeuroticism(Integer neuroticism) {
        this.neuroticism = neuroticism;
    }

    public Integer getExtraversion() {
        return extraversion;
    }

    public void setExtraversion(Integer extraversion) {
        this.extraversion = extraversion;
    }

    public Integer getOpenness() {
        return openness;
    }

    public void setOpenness(Integer openness) {
        this.openness = openness;
    }

    public Integer getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(Integer agreeableness) {
        this.agreeableness = agreeableness;
    }

    public Integer getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(Integer conscientiousness) {
        this.conscientiousness = conscientiousness;
    }
}

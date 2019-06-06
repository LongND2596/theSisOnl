package longnd.thesis.data.model;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import longnd.thesis.utils.Define;

@Entity(tableName = Define.ResultRiasec.TABLE_NAME)
public class ResultRiasec implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Define.ResultRiasec.ID)
    private Integer id;

    @ColumnInfo(name = Define.ResultRiasec.RULE)
    private Integer rule;

    @ColumnInfo(name = Define.ResultRiasec.SOCIETY)
    private Integer society;

    @ColumnInfo(name = Define.ResultRiasec.DISCOVER)
    private Integer discover;

    @ColumnInfo(name = Define.ResultRiasec.REALITY)
    private Integer reality;

    @ColumnInfo(name = Define.ResultRiasec.ART)
    private Integer art;

    @ColumnInfo(name = Define.ResultRiasec.CONVINCE)
    private Integer convince;

    public ResultRiasec() {
    }

    @Ignore

    public ResultRiasec(Integer rule, Integer society, Integer discover, Integer reality, Integer art, Integer convince) {
        this.rule = rule;
        this.society = society;
        this.discover = discover;
        this.reality = reality;
        this.art = art;
        this.convince = convince;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRule() {
        return rule;
    }

    public void setRule(Integer rule) {
        this.rule = rule;
    }

    public Integer getSociety() {
        return society;
    }

    public void setSociety(Integer society) {
        this.society = society;
    }

    public Integer getDiscover() {
        return discover;
    }

    public void setDiscover(Integer discover) {
        this.discover = discover;
    }

    public Integer getReality() {
        return reality;
    }

    public void setReality(Integer reality) {
        this.reality = reality;
    }

    public Integer getArt() {
        return art;
    }

    public void setArt(Integer art) {
        this.art = art;
    }

    public Integer getConvince() {
        return convince;
    }

    public void setConvince(Integer convince) {
        this.convince = convince;
    }
}

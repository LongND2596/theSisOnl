package longnd.thesis.data.model;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import longnd.thesis.utils.Define;

@Entity(tableName = Define.Result.TABLE_NAME)
public class Result implements Serializable {
    @PrimaryKey()
    @ColumnInfo(name = Define.Result.ID)
    @NonNull
    private String id;

    @ColumnInfo(name = Define.Result.ID_CUSTOMER)
    private Integer idCustomer;

    @ColumnInfo(name = Define.Result.ID_MULTIPLE_CHOICE)
    private Integer id_multiple_choice;

    @ColumnInfo(name = Define.Result.TYPE)
    private Integer type;

    @ColumnInfo(name = Define.Result.TIME)
    private String aTime;

    public Result() {
    }

    @Ignore
    public Result(String id, Integer idCustomer, Integer id_multiple_choice, Integer type) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.id_multiple_choice = id_multiple_choice;
        this.type = type;
        aTime = String.valueOf(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getId_multiple_choice() {
        return id_multiple_choice;
    }

    public void setId_multiple_choice(Integer id_multiple_choice) {
        this.id_multiple_choice = id_multiple_choice;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getATime() {
        return aTime;
    }

    public void setATime(String aTime) {
        this.aTime = aTime;
    }
}

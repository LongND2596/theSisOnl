package longnd.thesis.data.model;

import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import longnd.thesis.utils.Define;

@Entity(tableName = Define.ResultPsycho.TABLE_NAME)
public class ResultPsychological implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Define.ResultPsycho.ID)
    private Integer id;

    @ColumnInfo(name = Define.ResultPsycho.loAu)
    private Integer loAu;

    @ColumnInfo(name = Define.ResultPsycho.tramCam)
    private Integer tramCam;

    @ColumnInfo(name = Define.ResultPsycho.stress)
    private Integer stress;

    @ColumnInfo(name = Define.ResultPsycho.khoTapTrung)
    private Integer khoTapTrung;

    @ColumnInfo(name = Define.ResultPsycho.tangDong)
    private Integer tangDong;

    @ColumnInfo(name = Define.ResultPsycho.kkGiaoTiepXaHoi)
    private Integer kkGiaoTiepXaHoi;

    @ColumnInfo(name = Define.ResultPsycho.kkHocTap)
    private Integer kkHocTap;

    @ColumnInfo(name = Define.ResultPsycho.kkDinhHuongNgheNghiep)
    private Integer kkDinhHuongNgheNghiep;

    @ColumnInfo(name = Define.ResultPsycho.kkQuanHeChaMe)
    private Integer kkQuanHeChaMe;

    @ColumnInfo(name = Define.ResultPsycho.kkQuanHeThayCo)
    private Integer kkQuanHeThayCo;

    @ColumnInfo(name = Define.ResultPsycho.kkQuanHeBanBe)
    private Integer kkQuanHeBanBe;

    @ColumnInfo(name = Define.ResultPsycho.hanhViChongDoi)
    private Integer hanhViChongDoi;

    @ColumnInfo(name = Define.ResultPsycho.roiLoanHanhVi)
    private Integer roiLoanHanhVi;

    @ColumnInfo(name = Define.ResultPsycho.gayHan)
    private Integer gayHan;

    public ResultPsychological() {
    }

    @Ignore
    public ResultPsychological(Integer loAu, Integer tramCam, Integer stress, Integer khoTapTrung, Integer tangDong, Integer kkGiaoTiepXaHoi, Integer kkHocTap, Integer kkDinhHuongNgheNghiep, Integer kkQuanHeChaMe, Integer kkQuanHeThayCo, Integer kkQuanHeBanBe, Integer hanhViChongDoi, Integer roiLoanHanhVi, Integer gayHan) {
        this.loAu = loAu;
        this.tramCam = tramCam;
        this.stress = stress;
        this.khoTapTrung = khoTapTrung;
        this.tangDong = tangDong;
        this.kkGiaoTiepXaHoi = kkGiaoTiepXaHoi;
        this.kkHocTap = kkHocTap;
        this.kkDinhHuongNgheNghiep = kkDinhHuongNgheNghiep;
        this.kkQuanHeChaMe = kkQuanHeChaMe;
        this.kkQuanHeThayCo = kkQuanHeThayCo;
        this.kkQuanHeBanBe = kkQuanHeBanBe;
        this.hanhViChongDoi = hanhViChongDoi;
        this.roiLoanHanhVi = roiLoanHanhVi;
        this.gayHan = gayHan;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoAu() {
        return loAu;
    }

    public void setLoAu(Integer loAu) {
        this.loAu = loAu;
    }

    public Integer getTramCam() {
        return tramCam;
    }

    public void setTramCam(Integer tramCam) {
        this.tramCam = tramCam;
    }

    public Integer getStress() {
        return stress;
    }

    public void setStress(Integer stress) {
        this.stress = stress;
    }

    public Integer getKhoTapTrung() {
        return khoTapTrung;
    }

    public void setKhoTapTrung(Integer khoTapTrung) {
        this.khoTapTrung = khoTapTrung;
    }

    public Integer getTangDong() {
        return tangDong;
    }

    public void setTangDong(Integer tangDong) {
        this.tangDong = tangDong;
    }

    public Integer getKkGiaoTiepXaHoi() {
        return kkGiaoTiepXaHoi;
    }

    public void setKkGiaoTiepXaHoi(Integer kkGiaoTiepXaHoi) {
        this.kkGiaoTiepXaHoi = kkGiaoTiepXaHoi;
    }

    public Integer getKkHocTap() {
        return kkHocTap;
    }

    public void setKkHocTap(Integer kkHocTap) {
        this.kkHocTap = kkHocTap;
    }

    public Integer getKkDinhHuongNgheNghiep() {
        return kkDinhHuongNgheNghiep;
    }

    public void setKkDinhHuongNgheNghiep(Integer kkDinhHuongNgheNghiep) {
        this.kkDinhHuongNgheNghiep = kkDinhHuongNgheNghiep;
    }

    public Integer getKkQuanHeChaMe() {
        return kkQuanHeChaMe;
    }

    public void setKkQuanHeChaMe(Integer kkQuanHeChaMe) {
        this.kkQuanHeChaMe = kkQuanHeChaMe;
    }

    public Integer getKkQuanHeThayCo() {
        return kkQuanHeThayCo;
    }

    public void setKkQuanHeThayCo(Integer kkQuanHeThayCo) {
        this.kkQuanHeThayCo = kkQuanHeThayCo;
    }

    public Integer getKkQuanHeBanBe() {
        return kkQuanHeBanBe;
    }

    public void setKkQuanHeBanBe(Integer kkQuanHeBanBe) {
        this.kkQuanHeBanBe = kkQuanHeBanBe;
    }

    public Integer getHanhViChongDoi() {
        return hanhViChongDoi;
    }

    public void setHanhViChongDoi(Integer hanhViChongDoi) {
        this.hanhViChongDoi = hanhViChongDoi;
    }

    public Integer getRoiLoanHanhVi() {
        return roiLoanHanhVi;
    }

    public void setRoiLoanHanhVi(Integer roiLoanHanhVi) {
        this.roiLoanHanhVi = roiLoanHanhVi;
    }

    public Integer getGayHan() {
        return gayHan;
    }

    public void setGayHan(Integer gayHan) {
        this.gayHan = gayHan;
    }
}

package longnd.thesis.utils;

import android.content.Context;

import longnd.thesis.R;
import longnd.thesis.data.model.ResultPsychological;

public class EvaluateUtils {
    /**
     * used to evaluate personality scales for men
     * type: 0 - Thấp, 1 - Trung bình, 2 - Cao
     *
     * @return
     */
    public static int[] evaluateNeoMale(int[] results) {
        int[] sResults = new int[5];
        // a
        if (results[0] < 18.1) {
            sResults[0] = 0;
        } else if (results[0] < 35.4) {
            sResults[0] = 1;
        } else {
            sResults[0] = 2;
        }
        // c
        if (results[1] < 20.3) {
            sResults[1] = 0;
        } else if (results[1] < 33.7) {
            sResults[1] = 1;
        } else {
            sResults[1] = 2;
        }
        // o
        if (results[2] < 23.5) {
            sResults[2] = 0;
        } else if (results[2] < 33.7) {
            sResults[2] = 1;
        } else {
            sResults[2] = 2;
        }
        // n
        if (results[3] < 19.8) {
            sResults[3] = 0;
        } else if (results[3] < 34.8) {
            sResults[3] = 1;
        } else {
            sResults[3] = 2;
        }
        // e
        if (results[4] < 23.5) {
            sResults[4] = 0;
        } else if (results[4] < 37.8) {
            sResults[4] = 1;
        } else {
            sResults[4] = 2;
        }
        return sResults;
    }

    /**
     * used to assess personality scales for women
     * type: 0 - Thấp, 1 - Trung bình, 2 - Cao
     *
     * @return
     */
    public static int[] evaluateNeoFemale(int[] results) {
        int[] sResults = new int[5];
        // a
        if (results[0] < 19.1) {
            sResults[0] = 0;
        } else if (results[0] < 35.4) {
            sResults[0] = 1;
        } else {
            sResults[0] = 2;
        }
        // c
        if (results[1] < 22.4) {
            sResults[1] = 0;
        } else if (results[1] < 32.5) {
            sResults[1] = 1;
        } else {
            sResults[1] = 2;
        }
        // o
        if (results[2] < 22.4) {
            sResults[2] = 0;
        } else if (results[2] < 33.8) {
            sResults[2] = 1;
        } else {
            sResults[2] = 2;
        }
        // n
        if (results[3] < 22.6) {
            sResults[3] = 0;
        } else if (results[3] < 38.2) {
            sResults[3] = 1;
        } else {
            sResults[3] = 2;
        }
        // e
        if (results[4] < 25.7) {
            sResults[4] = 0;
        } else if (results[4] < 39.8) {
            sResults[4] = 1;
        } else {
            sResults[4] = 2;
        }
        return sResults;
    }

    /**
     * used to convert numbers from numbers to corresponding words
     *
     * @param number
     * @return
     */
    public static String wordResults(int number) {
        switch (number) {
            case 0:
                return "Thấp";
            case 1:
                return "Trung bình";
            case 2:
                return "Cao";
            default:
                return "Thấp";
        }
    }

    public static String resultRiasecTitle(Context context, int number) {
        return context.getResources().getStringArray(R.array.results_riasec)[number];
    }

    public static String resultRiasecDetail(Context context, int number) {
        return context.getResources().getStringArray(R.array.results_riasec_array)[number];
    }

    /**
     * type: 0 - Không gặp vấn đề, 1 - Nguy cơ, 2 - Nên gặp chuyên gia
     */
    public static String[] evaluatePsycho(ResultPsychological result){
        String[] sResult = new String[14];
        // lo au
        if (result.getLoAu() < 7.867) {
            sResult[0] = "Không gặp vấn đề";
        } else if (result.getLoAu() <= 11.28) {
            sResult[0] = "Nguy cơ";
        } else {
            sResult[0] = "Nên gặp chuyên gia";
        }
        //tram cam
        if (result.getTramCam() < 8.854) {
            sResult[1] = "Không gặp vấn đề";
        } else if (result.getTramCam() <= 12.939) {
            sResult[1] = "Nguy cơ";
        } else {
            sResult[1] = "Nên gặp chuyên gia";
        }
        //stress
        if (result.getStress() < 11.517) {
            sResult[2] = "Không gặp vấn đề";
        } else if (result.getStress() <= 15.856) {
            sResult[2] = "Nguy cơ";
        } else {
            sResult[2] = "Nên gặp chuyên gia";
        }
        //kho tap trung
        if (result.getKhoTapTrung() < 12.119) {
            sResult[3] = "Không gặp vấn đề";
        } else if (result.getKhoTapTrung() <= 16.505) {
            sResult[3] = "Nguy cơ";
        } else {
            sResult[3] = "Nên gặp chuyên gia";
        }
        // tang dong
        if (result.getTangDong() < 10.113) {
            sResult[4] = "Không gặp vấn đề";
        } else if (result.getTangDong() <= 14.85) {
            sResult[4] = "Nguy cơ";
        } else {
            sResult[4] = "Nên gặp chuyên gia";
        }
        // kk giao tieo xa hoi
        if (result.getKkGiaoTiepXaHoi() < 19.338) {
            sResult[5] = "Không gặp vấn đề";
        } else if (result.getKkGiaoTiepXaHoi() <= 27.265) {
            sResult[5] = "Nguy cơ";
        } else {
            sResult[5] = "Nên gặp chuyên gia";
        }
        // kk hoc tap
        if (result.getKkHocTap() < 11.991) {
            sResult[6] = "Không gặp vấn đề";
        } else if (result.getKkHocTap() <= 16.163) {
            sResult[6] = "Nguy cơ";
        } else {
            sResult[6] = "Nên gặp chuyên gia";
        }
        // kk dinh huong nghe nghiep
        if (result.getKkDinhHuongNgheNghiep() < 123.741) {
            sResult[7] = "Không gặp vấn đề";
        } else if (result.getKkDinhHuongNgheNghiep() <= 164.416) {
            sResult[7] = "Nguy cơ";
        } else {
            sResult[7] = "Nên gặp chuyên gia";
        }
        // kk quan he cha me
        if (result.getKkQuanHeChaMe() < 11.043) {
            sResult[8] = "Không gặp vấn đề";
        } else if (result.getKkQuanHeChaMe() <= 15.034) {
            sResult[8] = "Nguy cơ";
        } else {
            sResult[8] = "Nên gặp chuyên gia";
        }
        // kk quan he thay co
        if (result.getKkQuanHeThayCo() < 8.17) {
            sResult[9] = "Không gặp vấn đề";
        } else if (result.getKkQuanHeThayCo() <= 11.584) {
            sResult[9] = "Nguy cơ";
        } else {
            sResult[9] = "Nên gặp chuyên gia";
        }
        // kk quan he ban be
        if (result.getKkQuanHeBanBe() < 11.914) {
            sResult[10] = "Không gặp vấn đề";
        } else if (result.getKkQuanHeBanBe() <= 17.03) {
            sResult[10] = "Nguy cơ";
        } else {
            sResult[10] = "Nên gặp chuyên gia";
        }
        // kk hanh vi chong doi
        if (result.getHanhViChongDoi() < 7.962) {
            sResult[11] = "Không gặp vấn đề";
        } else if (result.getHanhViChongDoi() <= 11.509) {
            sResult[11] = "Nguy cơ";
        } else {
            sResult[11] = "Nên gặp chuyên gia";
        }
        // kk roi loan hanh vi
        if (result.getRoiLoanHanhVi() < 5.192) {
            sResult[12] = "Không gặp vấn đề";
        } else if (result.getRoiLoanHanhVi() <= 8.34) {
            sResult[12] = "Nguy cơ";
        } else {
            sResult[12] = "Nên gặp chuyên gia";
        }
        // kk gay han
        if (result.getGayHan() < 15.354) {
            sResult[13] = "Không gặp vấn đề";
        } else if (result.getGayHan() <= 22.355) {
            sResult[13] = "Nguy cơ";
        } else {
            sResult[13] = "Nên gặp chuyên gia";
        }
        return sResult;
    }
}

package longnd.thesis.ui.evaluate;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class EvaluateViewModel extends ViewModel {
    private static final int NUMBER_CLASS = 6;
    private static final int NUMBER_RIASEC = 7;
    private int[] results;
    private int type;

    @Inject
    EvaluateViewModel() {

    }

    public void setResults(int[] results) {
        this.results = results;
    }

    public int[] getResults() {
        return results;
    }

    public int getMin() {
        int fmin = results[0];
        for (int i = 1; i < results.length; i++) {
            if (fmin > results[i]) {
                fmin = results[i];
            }
        }
        return fmin;
    }

    public int getMax() {
        int fmax = results[0];
        for (int i = 1; i < results.length; i++) {
            if (fmax < results[i]) {
                fmax = results[i];
            }
        }
        return fmax == 0 ? 1 : fmax;
    }

    public int getMaxInPosition() {
        for (int i = 1; i < results.length; i++) {
            if (getMax() == results[i]) {
                return i;
            }
        }
        return -1;
    }

    public float getSpaceNeo() {
        if (getMin() == 0 && getMax() == 1) {
            return 0.2f;
        }
        int gspace = getMax() - getMin() + 1;
        if (gspace > 1 && gspace <= NUMBER_CLASS) {
            if ((getMin() + 0.5f * NUMBER_CLASS) >= NUMBER_CLASS) {
                return 0.5f;
            }
            return 1.0f;
        }
        return (float) Math.ceil((double) gspace / (double) NUMBER_CLASS);
    }

    public int getSpaceRiasec() {
        int gspace = getMax() - getMin();
        if (gspace <= NUMBER_RIASEC) {
            return 1;
        }
        return (int) Math.ceil((double) gspace / (double) NUMBER_RIASEC);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

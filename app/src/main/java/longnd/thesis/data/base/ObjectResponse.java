package longnd.thesis.data.base;

import androidx.annotation.NonNull;
import longnd.thesis.utils.Define;
import io.reactivex.annotations.Nullable;

public class ObjectResponse<T> {
    private int status;
    @Nullable
    private T data;

    @Nullable
    private Throwable error;

    public ObjectResponse() {
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    private ObjectResponse(int status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public ObjectResponse<T> loading() {
        return new ObjectResponse<>(Define.ResponseStatus.LOADING, null, null);
    }

    public ObjectResponse<T> success(@NonNull T data) {
        return new ObjectResponse<>(Define.ResponseStatus.SUCCESS, data, null);
    }

    public ObjectResponse<T> error(@NonNull Throwable throwable) {
        return new ObjectResponse<>(Define.ResponseStatus.ERROR, null, throwable);
    }
}

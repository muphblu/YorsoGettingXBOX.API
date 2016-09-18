package ru.innopolis.yorsogettingxbox.repository.network;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import ru.innopolis.yorsogettingxbox.models.Document;
import ru.innopolis.yorsogettingxbox.models.DocumentsResponse;
import rx.Observable;

public interface DocumentsApi {
    @GET("deals/{id}/documents")
    Observable<List<Document>> documents(@Path("id") int id);

    @Multipart
    @POST("deals/{id}/documents")
    Observable<DocumentsResponse> upload(@Path("id") int id, @Part MultipartBody.Part file);
}

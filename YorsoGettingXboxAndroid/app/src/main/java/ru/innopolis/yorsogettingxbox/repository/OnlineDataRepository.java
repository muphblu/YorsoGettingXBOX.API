package ru.innopolis.yorsogettingxbox.repository;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ru.innopolis.yorsogettingxbox.models.Deal;
import ru.innopolis.yorsogettingxbox.models.Document;
import ru.innopolis.yorsogettingxbox.models.DocumentsResponse;
import ru.innopolis.yorsogettingxbox.repository.network.ServiceFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class OnlineDataRepository implements DataRepository {

    @NonNull
    @Override
    public Observable<List<Deal>> getDeals() {
        return ServiceFactory.getDealsApiService()
                .getDeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Deal> putDeal(Deal deal) {
        return ServiceFactory.getDealsApiService()
                .newDeal(deal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Document>> getDocuments(int dealId) {
        return ServiceFactory.getDocumentsApiService()
                .documents(dealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<DocumentsResponse> uploadDocument(int dealId, File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("document", file.getName(), requestFile);
        Timber.d("Everything ok");
        return ServiceFactory.getDocumentsApiService()
                .upload(dealId, multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

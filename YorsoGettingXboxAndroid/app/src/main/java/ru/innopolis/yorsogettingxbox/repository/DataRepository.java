package ru.innopolis.yorsogettingxbox.repository;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.List;

import ru.innopolis.yorsogettingxbox.models.Deal;
import ru.innopolis.yorsogettingxbox.models.Document;
import ru.innopolis.yorsogettingxbox.models.DocumentsResponse;
import rx.Observable;

public interface DataRepository {

    @NonNull
    Observable<List<Deal>> getDeals();

    Observable<Deal> putDeal(Deal deal);

    Observable<List<Document>> getDocuments(int dealId);

    Observable<DocumentsResponse> uploadDocument(int dealId, File file);
}

package ru.innopolis.yorsogettingxbox.presentation.documents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.innopolis.yorsogettingxbox.R;
import ru.innopolis.yorsogettingxbox.models.Document;
import ru.innopolis.yorsogettingxbox.presentation.common.DividerItemDecoration;
import ru.innopolis.yorsogettingxbox.presentation.deals.DealsActivity;
import ru.innopolis.yorsogettingxbox.presentation.signers.DocumentSignersActivity;
import ru.innopolis.yorsogettingxbox.repository.FileUtils;
import ru.innopolis.yorsogettingxbox.repository.RepositoryProvider;
import timber.log.Timber;

public class DocumentsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, DocumentsAdapter.OnItemClickListener {

    public static final String DOCUMENT_CODE = "document_code";

    private static final int PICK_FILE_REQUEST_CODE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_documents)
    RecyclerView recyclerDocuments;
    @BindView(R.id.swipe_refresh_documents)
    SwipeRefreshLayout swipeRefreshDocuments;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private int dealId = 1;
    private DocumentsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        ButterKnife.bind(this);
        dealId = getIntent().getIntExtra(DealsActivity.DEAL_ID_CODE, 1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Deal #" + dealId);

        init();
        swipeRefreshDocuments.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);

        adapter = new DocumentsAdapter(this, this);
        recyclerDocuments.setAdapter(adapter);
        recyclerDocuments.setLayoutManager(new LinearLayoutManager(this));
        recyclerDocuments.setItemAnimator(new DefaultItemAnimator());
        recyclerDocuments.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        swipeRefreshDocuments.setOnRefreshListener(this);
    }

    private void init() {
        RepositoryProvider.provideDataRepository().getDocuments(dealId)
                .doOnSubscribe(() -> swipeRefreshDocuments.setRefreshing(true))
                .doAfterTerminate(() -> swipeRefreshDocuments.setRefreshing(false))
                .subscribe(documents -> {
                    adapter.setDocuments(documents);
                }, Timber::e);
    }

    @OnClick(R.id.fab)
    void addDocument(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"), PICK_FILE_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_FILE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    File myFile = FileUtils.getFile(this, uri);
                    if (myFile == null) {
                        Timber.e("File is null");
                        return;
                    }
                    RepositoryProvider.provideDataRepository().uploadDocument(dealId, myFile)
                            .doAfterTerminate(() -> {
                                init();
                            })
                            .subscribe(msg -> {
                                        Timber.d(msg.toString());
                                    },
                                    Timber::e);
                }
                break;
            default:
                Timber.e("Unexpected request code: %s", requestCode);
                break;

        }

    }

    @Override
    public void onRefresh() {
        init();
    }

    @Override
    public void onItemClick(Document item) {
        Intent intent = new Intent(this, DocumentSignersActivity.class);
        intent.putExtra(DOCUMENT_CODE, item);
        startActivity(intent);
    }
}

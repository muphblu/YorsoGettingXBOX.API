package ru.innopolis.yorsogettingxbox.presentation.signers;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.yorsogettingxbox.R;
import ru.innopolis.yorsogettingxbox.models.Document;
import ru.innopolis.yorsogettingxbox.models.SignInfoEntity;
import ru.innopolis.yorsogettingxbox.presentation.common.DividerItemDecoration;
import ru.innopolis.yorsogettingxbox.presentation.documents.DocumentsActivity;

public class DocumentSignersActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.signers_toolbar)
    Toolbar signersToolbar;
    @BindView(R.id.recycler_signers)
    RecyclerView recyclerSigners;
    @BindView(R.id.swipe_refresh_signers)
    SwipeRefreshLayout swipeRefreshSigners;

    private Document document;
    private DocumentSignersAdapter docSignersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_signers);
        ButterKnife.bind(this);
        setSupportActionBar(signersToolbar);

        document = (Document) getIntent().getSerializableExtra(DocumentsActivity.DOCUMENT_CODE);

        swipeRefreshSigners.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);

        docSignersAdapter = new DocumentSignersAdapter(this);
        recyclerSigners.setAdapter(docSignersAdapter);
        recyclerSigners.setLayoutManager(new LinearLayoutManager(this));
        recyclerSigners.setItemAnimator(new DefaultItemAnimator());
        recyclerSigners.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        init();

        swipeRefreshSigners.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        init();
    }

    private void init() {
        List<SignInfoEntity> entities = new ArrayList<>();
    }
}

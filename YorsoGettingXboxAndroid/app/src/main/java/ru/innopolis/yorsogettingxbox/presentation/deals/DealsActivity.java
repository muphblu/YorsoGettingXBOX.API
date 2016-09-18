package ru.innopolis.yorsogettingxbox.presentation.deals;

import android.content.Intent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.innopolis.yorsogettingxbox.R;
import ru.innopolis.yorsogettingxbox.models.Deal;
import ru.innopolis.yorsogettingxbox.presentation.common.DividerItemDecoration;
import ru.innopolis.yorsogettingxbox.presentation.documents.DocumentsActivity;
import ru.innopolis.yorsogettingxbox.presentation.newdeal.AddingDealActivity;
import ru.innopolis.yorsogettingxbox.repository.RepositoryProvider;
import timber.log.Timber;

public class DealsActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, DealsAdapter.OnItemClickListener {
    public static final String DEAL_ID_CODE = "dealId";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_deals)
    RecyclerView recyclerDeals;
    @BindView(R.id.swipe_refresh_deals)
    SwipeRefreshLayout swipeRefreshDeals;
    @BindView(R.id.fab_deals)
    FloatingActionButton fab;

    DealsAdapter dealsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setupRecyclerLayout();
        init();

        swipeRefreshDeals.setColorSchemeResources(R.color.primary, R.color.primary_dark, R.color.accent);
        swipeRefreshDeals.setOnRefreshListener(this);
    }

    private void setupRecyclerLayout() {
        dealsAdapter = new DealsAdapter(this, this);
        recyclerDeals.setAdapter(dealsAdapter);
        recyclerDeals.setLayoutManager(new LinearLayoutManager(this));
        recyclerDeals.setItemAnimator(new DefaultItemAnimator());
        recyclerDeals.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void init() {
        RepositoryProvider.provideDataRepository().getDeals()
                .doOnSubscribe(() -> swipeRefreshDeals.setRefreshing(true))
                .doAfterTerminate(() -> swipeRefreshDeals.setRefreshing(false))
                .subscribe(dealsAdapter::setDeals, (t) -> {
                    Timber.e(t);
                });
    }

    @OnClick(R.id.fab_deals)
    void addDeal(View view) {
        Intent add = new Intent(this, AddingDealActivity.class);
        startActivityForResult(add, 1);
    }

    protected void onActivityResult(int RequestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        Deal deal = new Deal(data.getStringExtra("title"), data.getStringExtra("description"));
        dealsAdapter.addDeal(deal);
        RepositoryProvider.provideDataRepository().putDeal(deal)
                .subscribe(msg -> {
                }, Timber::e);
    }

    @Override
    public void onRefresh() {
        init();
    }

    void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Deal item) {
        Intent intent = new Intent(this, DocumentsActivity.class);
        intent.putExtra(DEAL_ID_CODE, item.getId());
        startActivity(intent);
    }
}

package ru.innopolis.yorsogettingxbox.presentation.documents;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.yorsogettingxbox.R;
import ru.innopolis.yorsogettingxbox.models.Document;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.DocumentViewHolder> {

    private List<Document> documents;
    private final LayoutInflater layoutInflater;
    private OnItemClickListener listener;

    public DocumentsAdapter(Activity activity, OnItemClickListener listener) {
        this(activity, listener, new ArrayList<>());
    }

    public DocumentsAdapter(Activity activity, OnItemClickListener listener, List<Document> documents) {
        this.documents = documents;
        this.layoutInflater = activity.getLayoutInflater();
        this.listener = listener;
    }

    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.item_document_layout, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DocumentViewHolder holder, int position) {
        Document document = documents.get(position);
        holder.viewDocumentName.setText(document.getName());
        holder.numberProgressBar.setProgress(document.getPercentDone());
        holder.viewDocumentBlockchain.setText(
                document.isChainStatus() ? R.string.text_in_blockchain : R.string.text_not_in_bllockchain);
        holder.rootView.setOnClickListener((view -> {listener.onItemClick(documents.get(position));}));
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public void setDocuments(List<Document> newDocuments) {
        documents = newDocuments;
        notifyDataSetChanged();
    }

    public void addDocument(Document item){
        documents.add(item);
        notifyItemInserted(documents.size() - 1);
    }

    public interface OnItemClickListener {
        void onItemClick(Document item);
    }

    public class DocumentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_document_name)
        TextView viewDocumentName;
        @BindView(R.id.text_document_blockchain)
        TextView viewDocumentBlockchain;
        @BindView(R.id.number_progress_bar)
        NumberProgressBar numberProgressBar;
        @BindView(R.id.view_document)
        View rootView;

        public DocumentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

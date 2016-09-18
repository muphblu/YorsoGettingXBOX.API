package ru.innopolis.yorsogettingxbox.presentation.signers;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.yorsogettingxbox.R;
import ru.innopolis.yorsogettingxbox.models.SignInfoEntity;

/**
 * Created by enspa on 18.09.2016.
 */
public class DocumentSignersAdapter extends RecyclerView.Adapter<DocumentSignersAdapter.DocumentSignersViewHolder> {

    private List<SignInfoEntity> signers;
    private final LayoutInflater layoutInflater;


    public DocumentSignersAdapter(Activity activity){this(activity,new ArrayList<SignInfoEntity>());}

    public DocumentSignersAdapter(Activity activity, List<SignInfoEntity> signers) {
        this.signers = signers;
        this.layoutInflater = activity.getLayoutInflater();
    }

    @Override
    public DocumentSignersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.item_signers_layout, parent, false);
        return new DocumentSignersViewHolder(view);
    }

    public void onBindViewHolder(DocumentSignersViewHolder holder, int position) {
        SignInfoEntity signer = signers.get(position);
        holder.signerName.setText(signer.getSigner().getName());
        holder.signs.setText(signer.isSigned ? R.string.signed : R.string.not_signed);
    }

    @Override
    public int getItemCount() {
        return signers.size();
    }

    public void setSigner(List<SignInfoEntity> signers){
        this.signers = signers;
        notifyDataSetChanged();
    }

    public class DocumentSignersViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.signer_name)
        TextView signerName;
        @BindView(R.id.signs)
        TextView signs;


        DocumentSignersViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

    }
}

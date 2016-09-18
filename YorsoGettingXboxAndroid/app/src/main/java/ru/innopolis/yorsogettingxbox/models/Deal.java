package ru.innopolis.yorsogettingxbox.models;

import android.support.annotation.Nullable;

import java.util.List;

public class Deal {

    private Integer id;
    private String title;
    private String description;
    @Nullable
    private String contractId;
    @Nullable
    private List<Document> documents;

    public Deal(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Deal(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Nullable
    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(@Nullable List<Document> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Deal{");
        sb.append("\n\t").append("id=").append(id);
        sb.append(", \n\t").append("title='").append(title).append('\'');
        sb.append(", \n\t").append("description='").append(description).append('\'');
        sb.append(", \n\t").append("contractId='").append(contractId).append('\'');
        sb.append(", \n\t").append("documents=").append(documents);
        sb.append("\n").append('}');
        return sb.toString();
    }
}

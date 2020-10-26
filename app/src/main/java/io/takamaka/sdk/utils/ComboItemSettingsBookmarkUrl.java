package io.takamaka.sdk.utils;

import java.util.Objects;

public class ComboItemSettingsBookmarkUrl implements Comparable<ComboItemSettingsBookmarkUrl> {

    private String textLabel;
    private String createUrl;
    private String retriveUrl;
    private String reqKey;

    public ComboItemSettingsBookmarkUrl(String textLabel, String createUrl, String retriveUrl) {
        this.textLabel = textLabel;
        this.createUrl = createUrl;
        this.retriveUrl = retriveUrl;
    }

    public ComboItemSettingsBookmarkUrl(String textLabel, String createUrl, String retriveUrl, String reqKey) {
        this.textLabel = textLabel;
        this.createUrl = createUrl;
        this.retriveUrl = retriveUrl;
        this.reqKey = reqKey;
    }

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public String getTextLabel() {
        return textLabel;
    }

    public void setTextLabel(String textLabel) {
        this.textLabel = textLabel;
    }

    public String getCreateUrl() {
        return createUrl;
    }

    public void setCreateUrl(String createUrl) {
        this.createUrl = createUrl;
    }

    public String getRetriveUrl() {
        return retriveUrl;
    }

    public void setRetriveUrl(String retriveUrl) {
        this.retriveUrl = retriveUrl;
    }

    @Override
    public String toString() {
        return textLabel;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.textLabel);
        hash = 23 * hash + Objects.hashCode(this.createUrl);
        hash = 23 * hash + Objects.hashCode(this.retriveUrl);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComboItemSettingsBookmarkUrl other = (ComboItemSettingsBookmarkUrl) obj;
        if (!Objects.equals(this.textLabel, other.textLabel)) {
            return false;
        }
        if (!Objects.equals(this.createUrl, other.createUrl)) {
            return false;
        }
        if (!Objects.equals(this.retriveUrl, other.retriveUrl)) {
            return false;
        }
        return true;
    }

    public String getComparableString() {
        return textLabel + createUrl + retriveUrl + "";
    }

    @Override
    public int compareTo(ComboItemSettingsBookmarkUrl o) {
        return getComparableString().compareTo(o.getComparableString());
    }

}

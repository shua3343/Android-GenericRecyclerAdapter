package com.example.genericrecycleradapter;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.genericrecycleradapter.GenericAdapterUtil.CHECK_SUFFIX;
import static com.example.genericrecycleradapter.GenericAdapterUtil.checkTag;
import static com.example.genericrecycleradapter.GenericAdapterUtil.checkTypeAndSetContent;
import static com.example.genericrecycleradapter.GenericAdapterUtil.obtainContentFromTag;

abstract class GenericAdapterConfiguration<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @LayoutRes
    private
    int itemView;

    @IdRes
    private List<Integer> ids;

    private List<T> contentObjects;
    private SparseArray<Object> array;

    GenericAdapterConfiguration(@LayoutRes int itemView, List<T> contentObjects) {
        this.contentObjects = contentObjects;
        this.itemView = itemView;
        init();
    }

    private void init() {
        ids = new ArrayList<>();
        array = new SparseArray<>();
    }

    void configureLayout(View itemView, int position) {
        for (int id : ids) {
            View view = itemView.findViewById(id);
            if (view.getTag() != null) {
                checkTypeAndSetContent(view, obtainContentFromTag(view, contentObjects.get(position)));
                setListener(view, id);
            }
        }
    }

    private void setListener(View view, @IdRes int id) {
        if (array.get(id, null) != null) {
            GenericAdapterUtil.IdentifyAndSetListener(view, array.get(id));
        }
    }

    int getContentObjectsSize() {
        return contentObjects.size();
    }

    @LayoutRes
    int getItemView() {
        return itemView;
    }

    boolean missingIds() {
        return ids.isEmpty();
    }

    void configureIds(ViewGroup viewGroup) throws NoTagsFoundedException {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (checkTag(viewGroup.getChildAt(i).getTag())) {
                ids.add(viewGroup.getChildAt(i).getId());
            }
        }
        if (ids.isEmpty()) {
            throw new NoTagsFoundedException("No views with the '" + CHECK_SUFFIX + "' text founded");
        }
    }

    public void addListener(@IdRes int id, Object listener) {
        array.put(id, listener);
    }

    @NonNull
    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    @Override
    public abstract int getItemCount();
}
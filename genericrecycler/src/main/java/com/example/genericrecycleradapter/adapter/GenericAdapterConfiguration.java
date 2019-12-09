package com.example.genericrecycleradapter.adapter;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.genericrecycleradapter.exceptions.NoTagsFoundedException;
import com.example.genericrecycleradapter.util.GenericAdapterUtil;

import java.util.ArrayList;
import java.util.List;

import static com.example.genericrecycleradapter.util.GenericAdapterUtil.CHECK_SUFFIX;
import static com.example.genericrecycleradapter.util.GenericAdapterUtil.checkTag;
import static com.example.genericrecycleradapter.util.GenericAdapterUtil.checkTypeAndSetContent;
import static com.example.genericrecycleradapter.util.GenericAdapterUtil.obtainContentFromTag;

abstract class GenericAdapterConfiguration<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @LayoutRes
    private
    int itemView;

    @IdRes
    private List<Integer> ids;

    private List<T> contentObjects;
    private SparseArray<Object> array;
    private SparseIntArray positions;
    private SparseArray<T> objects;

    GenericAdapterConfiguration(@LayoutRes int itemView, List<T> contentObjects) {
        this.contentObjects = contentObjects;
        this.itemView = itemView;
        init();
    }

    private void init() {
        ids = new ArrayList<>();
        array = new SparseArray<>();
        positions = new SparseIntArray();
        objects = new SparseArray<>();
    }

    void configureLayout(View itemView, int position) {
        positions.put(itemView.getId(), position);
        for (int id : ids) {
            View view = itemView.findViewById(id);
            if (view.getTag() != null) {
                objects.put(position, contentObjects.get(position));
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

    public void addListener(@IdRes Integer id, Object listener) {
        array.put(id, listener);
    }

    public Integer getPositionByViewId(@IdRes Integer id) {
        return positions.get(id);
    }

    public T getItemByViewId(@IdRes Integer id) {
        return objects.get(id);
    }

    @NonNull
    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    @Override
    public abstract int getItemCount();
}
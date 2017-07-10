package com.maximka.taskmanager.recycler.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Optional;
import com.maximka.taskmanager.utils.Assertion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListAdapter<T> extends RecyclerView.Adapter<ListAdapterViewHolder<T>> {
    @NonNull private final List<T> mDataList;
    @NonNull private final LayoutInflater mInflater;
    @NonNull private final ViewHolderSupplier<T> mHolderSupplier;
    @NonNull private final LayoutSupplier mLayoutSupplier;
    @NonNull private final Optional<OnItemClickListener<T>> mItemClickListener;
    @NonNull private final Optional<DiffCallbackSupplier<T>> mDiffCallbackSupplier;

    private ListAdapter(@NonNull final Context context,
                        @NonNull final List<T> dataList,
                        @NonNull final ViewHolderSupplier<T> holderSupplier,
                        @NonNull final LayoutSupplier layoutSupplier,
                        @NonNull final Optional<OnItemClickListener<T>> itemClickListener,
                        @NonNull final Optional<DiffCallbackSupplier<T>> diffCallbackSupplier) {

        Assertion.nonNull(context, dataList, layoutSupplier, holderSupplier, itemClickListener, diffCallbackSupplier);
        Assertion.nonNullContent(dataList);

        mInflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>(dataList);
        mHolderSupplier = holderSupplier;
        mLayoutSupplier = layoutSupplier;
        mItemClickListener = itemClickListener;
        mDiffCallbackSupplier = diffCallbackSupplier;
    }

    public void setDataList(@NonNull final List<T> newData) {
        Assertion.nonNullContent(newData);

        mDiffCallbackSupplier.executeIfPresent(
                diffCallbackSupplier ->
                        DiffUtil.calculateDiff(diffCallbackSupplier.getDiffCallback(Collections.unmodifiableList(mDataList),
                                                                                    Collections.unmodifiableList(newData)))
                                .dispatchUpdatesTo(this))
                .executeIfAbsent(this::notifyDataSetChanged);

        mDataList.clear();
        mDataList.addAll(newData);
    }

    @Override
    public ListAdapterViewHolder<T> onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View itemView = mInflater.inflate(mLayoutSupplier.getLayoutId(), parent, false);
        return mHolderSupplier.getViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListAdapterViewHolder<T> holder, final int position) {
        final T data = mDataList.get(position);
        holder.bind(data);
        holder.itemView
                .setOnClickListener(ignored ->
                                        mItemClickListener.ifPresent(listener ->
                                                                         listener.onItemClicked(position, data)));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public static final class Builder<T> {
        @NonNull private final Context mContext;
        @NonNull private final List<T> mDataList;
        @NonNull private final ViewHolderSupplier<T> mHolderSupplier;
        @NonNull private final LayoutSupplier mLayoutSupplier;
        @Nullable private OnItemClickListener<T> mItemClickListener;
        @Nullable private DiffCallbackSupplier<T> mDiffCallbackSupplier;

        public Builder(@NonNull final Context context,
                       @NonNull final List<T> dataList,
                       @NonNull final ViewHolderSupplier<T> holderSupplier,
                       @NonNull final LayoutSupplier layoutSupplier) {
            Assertion.nonNull(context, dataList, holderSupplier, layoutSupplier);
            Assertion.nonNullContent(dataList);

            mContext = context;
            mDataList = dataList;
            mHolderSupplier = holderSupplier;
            mLayoutSupplier = layoutSupplier;
        }

        @NonNull
        public Builder<T> withItemClickListener(@Nullable final OnItemClickListener<T> onItemClickListener) {
            mItemClickListener = onItemClickListener;
            return this;
        }

        @NonNull
        public Builder<T> withDiffCallback(@Nullable final DiffCallbackSupplier<T> diffCallbackSupplier) {
            mDiffCallbackSupplier = diffCallbackSupplier;
            return this;
        }

        @NonNull
        public ListAdapter<T> build() {
            return new ListAdapter<>(mContext,
                                     mDataList,
                                     mHolderSupplier,
                                     mLayoutSupplier,
                                     Optional.ofNullable(mItemClickListener),
                                     Optional.ofNullable(mDiffCallbackSupplier));
        }
    }
}

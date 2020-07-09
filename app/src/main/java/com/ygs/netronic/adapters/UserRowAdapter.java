package com.ygs.netronic.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ygs.netronic.R;
import com.ygs.netronic.databinding.UserRowItemBinding;
import com.ygs.netronic.interfaces.OnClickListener;
import com.ygs.netronic.models.ui.UserRowModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class UserRowAdapter extends RecyclerView.Adapter<UserRowAdapter.AdapterViewHolder> {

    private final List<UserRowModel> mDataSource;
    private transient OnClickListener<UserRowModel> mOnClickListener;

    public UserRowAdapter(
            @NonNull List<UserRowModel> dataSource,
            @NonNull OnClickListener<UserRowModel> onClickListener) {
        mDataSource = dataSource;
        mOnClickListener = onClickListener;
    }


    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UserRowItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.user_row_item, parent,
                false);
        return new AdapterViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        UserRowModel model = mDataSource.get(position);
        View root = holder.mBinding.getRoot();

        root.setOnClickListener(v -> {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick(v, model, position);
            }
        });


        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        private final UserRowItemBinding mBinding;

        public AdapterViewHolder(@NonNull UserRowItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(@NonNull UserRowModel item) {
            mBinding.setItem(item);
            mBinding.executePendingBindings();
        }
    }

}

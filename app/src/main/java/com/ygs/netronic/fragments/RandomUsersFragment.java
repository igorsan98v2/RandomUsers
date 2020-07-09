package com.ygs.netronic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ygs.netronic.R;
import com.ygs.netronic.activities.UserDetailsActivity;
import com.ygs.netronic.adapters.UserRowAdapter;
import com.ygs.netronic.annotations.GeneralString;
import com.ygs.netronic.annotations.RandomUserAnnotation;
import com.ygs.netronic.databinding.FragmentRandomUsersBinding;
import com.ygs.netronic.interfaces.OnClickListener;
import com.ygs.netronic.models.ui.UserRowModel;
import com.ygs.netronic.repositories.impl.RandomUsersRepositoryImpl;
import com.ygs.netronic.repositories.interfaces.RandomUsersRepository;
import com.ygs.netronic.viewmodels.RandomUsersFragmentsViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RandomUsersFragment extends Fragment implements OnClickListener<UserRowModel> {

    private UserRowAdapter mAdapter;
    private RandomUsersFragmentsViewModel mViewModel;
    private RandomUsersRepository mRepository;

    private Unbinder mUnbinder;
    private FragmentRandomUsersBinding mBinding;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.layout_progress)
    FrameLayout mLayoutProgress;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // inflate view


        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_random_users, container, false);
        View view = mBinding.getRoot();
        mUnbinder = ButterKnife.bind(this, view);

        // set layout manager for RecyclerView instance
        if (getContext() != null) {

            mRepository = RandomUsersRepositoryImpl.getInstance(getContext());
            mViewModel = new RandomUsersFragmentsViewModel(mRepository);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            mBinding.setViewModel(mViewModel);
            updateUserData();

        }
        //calls new list of random users
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isOfflinePossible()) {
            showCachedUserData();
        }

    }


    @Override
    public void onItemClick(@NonNull View view, @NonNull UserRowModel model, int position) {
        startUserDetailsActivity(model);
    }

    private boolean isOfflinePossible() {
        if (mViewModel.getOfflineUserList().getValue() != null) {
            return mViewModel.getOfflineUserList().getValue().size() == RandomUserAnnotation.DEFAULT_UPLOAD_ARR_SIZE;
        }
        return false;
    }

    private void showCachedUserData() {
        mViewModel.getOfflineUserList().observe(getViewLifecycleOwner(), userRowModels -> {
            if (userRowModels.size() != RandomUserAnnotation.DEFAULT_UPLOAD_ARR_SIZE) {
                mViewModel.loading.set(ViewGroup.VISIBLE);
            } else {
                mAdapter = new UserRowAdapter(userRowModels, this);
                mRecyclerView.setAdapter(mAdapter);
                mViewModel.loading.set(ViewGroup.GONE);
            }
        });
    }

    private void updateUserData() {
        mViewModel.updateUserList().observe(getViewLifecycleOwner(), userRowModels -> {
            if (userRowModels.size() != RandomUserAnnotation.DEFAULT_UPLOAD_ARR_SIZE) {
                mViewModel.loading.set(ViewGroup.VISIBLE);
            } else {
                mAdapter = new UserRowAdapter(userRowModels, this);
                mRecyclerView.setAdapter(mAdapter);
                mViewModel.loading.set(ViewGroup.GONE);
            }
        });
    }

    private void startUserDetailsActivity(@NonNull UserRowModel model) {
        if (getContext() != null) {

            Intent intent = new Intent(getContext(), UserDetailsActivity.class);
            intent.putExtra(GeneralString.EXTRA_USER_ID, model.getUserId());
            startActivity(intent);
        }
    }

    public static RandomUsersFragment createInstance() {
        Bundle args = new Bundle();
        RandomUsersFragment usersFragment = new RandomUsersFragment();
        usersFragment.setArguments(args);
        return usersFragment;
    }
}

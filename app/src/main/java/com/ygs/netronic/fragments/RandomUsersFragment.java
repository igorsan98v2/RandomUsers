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
import com.ygs.netronic.interfaces.OnClickListener;
import com.ygs.netronic.models.ui.LoadingView;
import com.ygs.netronic.models.ui.UserRowModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class RandomUsersFragment extends Fragment implements LoadingView, OnClickListener<UserRowModel> {

    private UserRowAdapter mAdapter;


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



        View view = inflater.inflate(R.layout.fragment_random_users, container, false);

        // set layout manager for RecyclerView instance
        if (getContext() != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
        }
        //calls new list of random users
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onLoadingBegin() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (mLayoutProgress != null) {
                    mLayoutProgress.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    @Override
    public void onLoadingEnd() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (mLayoutProgress != null) {
                    mLayoutProgress.setVisibility(View.GONE);
                }
            });
        }

    }


    @Override
    public void onItemClick(@NonNull View view, @NonNull UserRowModel model, int position) {
        startUserDetailsActivity(model);
    }

    private void startUserDetailsActivity(@NonNull UserRowModel model) {
        if (getContext() != null) {

            Intent intent = new Intent(getContext(), UserDetailsActivity.class);

            intent.putExtra(GeneralString.EXTRA_USER_ID, model.getUserId());
            startActivity(intent);
        }
    }

    public static RandomUsersFragment createInstance(){
        Bundle args = new Bundle();
        RandomUsersFragment usersFragment = new RandomUsersFragment();
        usersFragment.setArguments(args);
        return usersFragment;
    }
}

package com.ygs.netronic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ygs.netronic.R;
import com.ygs.netronic.annotations.GeneralString;
import com.ygs.netronic.databinding.FragmentUserDetailsBinding;
import com.ygs.netronic.models.ui.LoadingView;
import com.ygs.netronic.models.ui.UserDetailsModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserDetailsFragment extends Fragment implements LoadingView {
    private long mUserId;
    private Unbinder mUnbinder;
    private FragmentUserDetailsBinding mBinding;

    @BindView(R.id.layout_progress)
    FrameLayout mLayoutProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enterDataToFields(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // inflate view
        mBinding =
                DataBindingUtil.inflate(inflater,
                        R.layout.fragment_user_details, container, false);

        View view = mBinding.getRoot();
        mUnbinder = ButterKnife.bind(this, view);

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

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        enterDataToArgs(outState);
    }


    public void enterDataToFields(@NonNull Bundle args) {
        mUserId = args.getLong(GeneralString.EXTRA_USER_ID);
    }

    public void enterDataToArgs(@NonNull Bundle args) {
        args.putLong(GeneralString.EXTRA_USER_ID, mUserId);
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

    public static UserDetailsFragment createInstance(long userId) {
        Bundle args = new Bundle();
        args.putLong(GeneralString.EXTRA_USER_ID, userId);
        UserDetailsFragment instance = new UserDetailsFragment();
        instance.setArguments(args);
        return instance;
    }

}


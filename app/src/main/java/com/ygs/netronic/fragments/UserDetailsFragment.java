package com.ygs.netronic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ygs.netronic.R;
import com.ygs.netronic.annotations.GeneralString;
import com.ygs.netronic.databinding.FragmentUserDetailsBinding;
import com.ygs.netronic.interfaces.Restorable;
import com.ygs.netronic.repositories.impl.UserDetailsRepositoryImpl;
import com.ygs.netronic.repositories.interfaces.UserDetailsRepository;
import com.ygs.netronic.viewmodels.UserDetailsFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserDetailsFragment extends Fragment implements Restorable {
    private long mUserId;
    private Unbinder mUnbinder;
    private FragmentUserDetailsBinding mBinding;
    private UserDetailsRepository mRepository = UserDetailsRepositoryImpl.getInstance();
    private UserDetailsFragmentViewModel mViewModel;
    @BindView(R.id.layout_progress)
    FrameLayout mLayoutProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreState(savedInstanceState);
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

        mViewModel = new UserDetailsFragmentViewModel(mRepository);
        mBinding.setViewModel(mViewModel);
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
        mViewModel.getDetailsById(mUserId).observe(this, (details -> {
            mBinding.setUser(details);
            mViewModel.loading.set(ViewGroup.GONE);
        }));

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        enterDataToArgs(outState);
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            savedInstanceState = getArguments();
        }
        if (savedInstanceState != null) {
            enterDataToFields(savedInstanceState);
        }
    }

    @Override
    public void enterDataToFields(@NonNull Bundle args) {
        mUserId = args.getLong(GeneralString.EXTRA_USER_ID);
    }

    @Override
    public void enterDataToArgs(@NonNull Bundle args) {
        args.putLong(GeneralString.EXTRA_USER_ID, mUserId);
    }


    public static UserDetailsFragment createInstance(long userId) {
        Bundle args = new Bundle();
        args.putLong(GeneralString.EXTRA_USER_ID, userId);
        UserDetailsFragment instance = new UserDetailsFragment();
        instance.setArguments(args);
        return instance;
    }

}


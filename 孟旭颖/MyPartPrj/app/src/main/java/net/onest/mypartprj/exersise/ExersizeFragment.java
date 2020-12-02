package net.onest.mypartprj.exersise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import net.onest.mypartprj.R;

public class ExersizeFragment extends Fragment {
    private int mPosition;
    private TextView tvStem;
    private TextView tvOptA;
    private TextView tvOptB;
    private TextView tvOptC;
    private TextView tvOptD;
    private LinearLayout llOptA;
    private LinearLayout llOptB;
    private LinearLayout llOptC;
    private LinearLayout llOptD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.exercise_first,null);
        mPosition = getArguments().getInt("position");
        tvStem = view.findViewById(R.id.tv_stem);
        tvOptA = view.findViewById(R.id.tv_optA);
        tvOptB = view.findViewById(R.id.tv_optB);
        tvOptC = view.findViewById(R.id.tv_optC);
        tvOptD = view.findViewById(R.id.tv_optD);
        llOptA = view.findViewById(R.id.ll_optA);
        llOptB = view.findViewById(R.id.ll_optB);
        llOptC = view.findViewById(R.id.ll_optC);
        llOptD = view.findViewById(R.id.ll_optD);



        return view;
    }
}

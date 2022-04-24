package com.kolip.numberle.clasic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.kolip.numberle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassicStatisticDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassicStatisticDialog extends DialogFragment {

    // TODO: Rename and change types of parameters
    private static ClassicStatics statistics;
    private static boolean gameFinishedDialog;
    private static Runnable onFinished;
    private static long time;

    public ClassicStatisticDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClassicStatisticDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassicStatisticDialog newInstance(ClassicStatics classicStatics, boolean gameFinishedDialog,
                                                     Runnable onFinished, long time) {
        ClassicStatisticDialog fragment = new ClassicStatisticDialog();
        statistics = classicStatics;
        ClassicStatisticDialog.gameFinishedDialog = gameFinishedDialog;
        ClassicStatisticDialog.onFinished = onFinished;
        ClassicStatisticDialog.time = time;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_classic_statistic_dialog, container, false);
        ((TextView) customView.findViewById(R.id.best_of_day))
                .setText(CustomChronometer.formatElapsedTime(statistics.getDayHighScore()));
        ((TextView) customView.findViewById(R.id.best_of_week))
                .setText(CustomChronometer.formatElapsedTime(statistics.getWeekHighScore()));
        ((TextView) customView.findViewById(R.id.best_of_month))
                .setText(CustomChronometer.formatElapsedTime(statistics.getMonthHighScore()));
        ((TextView) customView.findViewById(R.id.best_of_year))
                .setText(CustomChronometer.formatElapsedTime(statistics.getYearHighScore()));

        if (gameFinishedDialog) {
            customView.findViewById(R.id.time_and_next_button).setVisibility(View.VISIBLE);
            ((TextView) customView.findViewById(R.id.your_score))
                    .setText(CustomChronometer.formatElapsedTime(time));
            customView.findViewById(R.id.next_button).setOnClickListener(v -> onFinished.run());
        }

        return customView;
    }
}
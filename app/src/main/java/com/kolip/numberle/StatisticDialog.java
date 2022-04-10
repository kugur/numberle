package com.kolip.numberle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class StatisticDialog extends AppCompatDialogFragment {

    Statitics statistic;

    public StatisticDialog(Statitics statistic) {
        this.statistic = statistic;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View customDialog = layoutInflater.inflate(R.layout.statistic_dialog, null);
        builder.setView(customDialog);

        ((TextView) customDialog.findViewById(R.id.total_game)).setText(statistic.getTotalGame());
        ((TextView) customDialog.findViewById(R.id.total_win)).setText(statistic.getSuccessRatio());
        ((TextView) customDialog.findViewById(R.id.strike)).setText(statistic.getStrike());
        ((TextView) customDialog.findViewById(R.id.max_strike)).setText(statistic.getMaxStrike());

        return builder.create();
    }
}

package com.kolip.numberle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class HelpDialog extends AppCompatDialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View customDialog = layoutInflater.inflate(R.layout.help_dialog, null);
        builder.setView(customDialog);

        ((ResultView) customDialog.findViewById(R.id.helper_row_2_result)).setAllColor(new NumberResult(1, 0, 3));
        ((ResultView) customDialog.findViewById(R.id.helper_row_1_result)).setAllColor(new NumberResult(0, 1, 3));
        ((ResultView) customDialog.findViewById(R.id.helper_row_3_result)).setAllColor(new NumberResult(1, 1, 2));
        ((ResultView) customDialog.findViewById(R.id.helper_row_4_result)).setAllColor(new NumberResult(4, 0, 0));
        return builder.create();
    }
}

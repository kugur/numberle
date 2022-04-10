package com.kolip.numberle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.function.Consumer;

public class GameFinishedDialog extends AppCompatDialogFragment {
    View customDialog;

    int showGiveLife;
    Statitics statistic;
    Spanned description;
    String title;

    Consumer<View> onGiveLifeHandle;
    Consumer<View> onNextHandle;

    public GameFinishedDialog(Consumer<View> onGiveLifeHandle, Consumer<View> onNextHandle,
                              Statitics statistic) {
        this.onGiveLifeHandle = onGiveLifeHandle;
        this.onNextHandle = onNextHandle;
        this.statistic = statistic;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        customDialog = layoutInflater.inflate(R.layout.dialog_game_finished, null);
        builder.setView(customDialog);

        customDialog.findViewById(R.id.give_life_on_game_finished)
                .setOnClickListener(v -> onGiveLifeHandle.accept(v));

        customDialog.findViewById(R.id.game_finished_button_on_dialog)
                .setOnClickListener(v -> onNextHandle.accept(v));

        customDialog.findViewById(R.id.give_life_on_game_finished)
                .setVisibility(showGiveLife);
        ((TextView) customDialog.findViewById(R.id.correct_word_on_finished_dialog))
                .setText(description);
        initializeStatics();
        return builder.create();
    }

    public void setStatistic(Statitics statistic) {
        this.statistic = statistic;
    }

    public void showGiveLife(boolean visibility) {
        showGiveLife = visibility ? View.VISIBLE : View.INVISIBLE;
    }

    public void setDescription(Spanned description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void initializeStatics() {
        ((TextView) customDialog.findViewById(R.id.dialog_game_finished_title)).setText(title);
        ((TextView) customDialog.findViewById(R.id.total_game)).setText(statistic.getTotalGame());
        ((TextView) customDialog.findViewById(R.id.total_win)).setText(statistic.getSuccessRatio());
        ((TextView) customDialog.findViewById(R.id.strike)).setText(statistic.getStrike());
        ((TextView) customDialog.findViewById(R.id.max_strike)).setText(statistic.getMaxStrike());
    }
}

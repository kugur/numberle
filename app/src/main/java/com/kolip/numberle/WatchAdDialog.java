package com.kolip.numberle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.function.Consumer;

public class WatchAdDialog extends AppCompatDialogFragment {

    private View customDialog;
    private AdManager adManager;
    private Consumer<Integer> onRewardAdsFinished;

    public WatchAdDialog(AdManager adManager, Consumer<Integer> onRewardAdsFinished) {
        this.adManager = adManager;
        this.onRewardAdsFinished = onRewardAdsFinished;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        customDialog = layoutInflater.inflate(R.layout.dialog_watch_ads, null);
        builder.setView(customDialog);

        customDialog.findViewById(R.id.play_ads_button).setOnClickListener(
                v -> adManager.showRewardAd(getActivity(), (diamond) -> {
                    onRewardAdsFinished.accept(diamond);
                    this.dismiss();
                }));
        return builder.create();
    }
}

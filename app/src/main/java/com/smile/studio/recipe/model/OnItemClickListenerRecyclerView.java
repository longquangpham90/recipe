package com.smile.studio.recipe.model;

import android.view.View;

public interface OnItemClickListenerRecyclerView {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}

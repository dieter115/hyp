package be.flashapps.hyp.adapters;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import be.flashapps.hyp.R;
import be.flashapps.hyp.activities.BaseActivity;
import be.flashapps.hyp.models.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class RecipeRealmAdapter extends RealmRecyclerViewAdapter<Recipe, RecyclerView.ViewHolder> {

    private OnRecipeClickListener mOnclickListener;
    private Activity mActivity;
    private Realm realm;

    String filter = "";
    private int expandedPosition = -1;
    LayoutTransition itemLayoutTransition;
    private final int EMPTY_VIEW = 255;

    public RecipeRealmAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Recipe> data, boolean autoUpdate, @NonNull Activity activity) {
        super(context, data, autoUpdate);
        mActivity = activity;
        realm = ((BaseActivity) mActivity).getRealm();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
            EmptyViewHolder cvh = new EmptyViewHolder(v);
            return cvh;
        }

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        RecipeViewHolder vh = new RecipeViewHolder(v, mOnclickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof RecipeViewHolder) {
            RecipeViewHolder recipeViewHolder=((RecipeViewHolder)holder);
            Recipe recipe = getData().get(position);

            recipeViewHolder.tvRecipeName.setText(recipe.getNaam());

            Glide.with(mActivity)
                    .load(recipe.getAfbeelding())
                    .into(recipeViewHolder.image);
        }
        else{
            EmptyViewHolder emptyViewHolder=(EmptyViewHolder)holder;
            emptyViewHolder.tvEmptyMessage.setText("Er werden geen gerechten gevonden");
        }
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_recipe_name)
        TextView tvRecipeName;
        @BindView(R.id.image)
        ImageView image;
        public RecipeViewHolder(View itemView, OnRecipeClickListener onClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(final View view) {

        }
    }

    public void setOnRecipeClickListener(OnRecipeClickListener onRecipeClickListener) {
        this.mOnclickListener = onRecipeClickListener;
    }

    public interface OnRecipeClickListener {
        void onRecipeClick(View view, Recipe Recipe);
    }

    @Override
    public int getItemCount() {
        return getData().size() > 0 ? getData().size() : 1;
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_empty_message)
        TextView tvEmptyMessage;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (getData().size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }
}
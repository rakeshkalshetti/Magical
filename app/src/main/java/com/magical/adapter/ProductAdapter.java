package com.magical.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.magical.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rakesh on 26/06/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements View.OnClickListener {


    private int[] resource = {
            R.drawable.moto, R.drawable.asus, R.drawable.mi, R.drawable.coolpad, R.drawable.honor,
            R.drawable.iphone, R.drawable.lenovo, R.drawable.htc, R.drawable.lg, R.drawable.mocromax, R.drawable.sony, R.drawable.samsung, R.drawable.vivo
    };

    private ItemDelegate itemDelegate;
    private int count = 0;
    private ArrayList<ProductItem> productItemArrayList;

    public ProductAdapter(ArrayList<ProductItem> productItemArrayList, ItemDelegate itemDelegate) {
        this.productItemArrayList = productItemArrayList;
        this.itemDelegate = itemDelegate;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductItem productItem = productItemArrayList.get(position);
        productItem.setId(position + 1);
        holder.nameTextView.setText(productItem.getName());
        holder.priceTextView.setText(productItem.getPrice() + " USD");
        holder.productImageView.setImageResource(resource[position]);
        holder.addToCart.setTag(productItem);
        holder.addToCart.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return productItemArrayList.size();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            ProductItem productItem = (ProductItem) v.getTag();

            if (!productItem.isProductState()) {
                productItem.setProductState(true);
                count++;
                ((TextView) v).setText("Remove from cart");
            } else {
                count--;
                productItem.setProductState(false);
                ((TextView) v).setText("Add to cart");
            }
        }
    }

    public interface ItemDelegate {
        public void onItemSelected(int count);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productImageView)
        ImageView productImageView;

        @BindView(R.id.productName)
        TextView nameTextView;

        @BindView(R.id.productPrice)
        TextView priceTextView;

        @BindView(R.id.addToCart)
        TextView addToCart;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

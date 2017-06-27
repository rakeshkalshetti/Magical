package com.magical;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.magical.adapter.ProductAdapter;
import com.magical.adapter.ProductItem;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends BaseActivity implements ProductAdapter.ItemDelegate {

    @BindView(R.id.productListView)
    RecyclerView recyclerView;

    private ArrayList<ProductItem> productItemArrayList;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

    private static final String CONFIG_CLIENT_ID = "credentials from developer.paypal.com";

    private static final int REQUEST_CODE_PAYMENT = 1;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        productItemArrayList = new ArrayList<>();
        ProductItem productItem;

        productItem = new ProductItem("Moto", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("Asus", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("MI", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("Coolpad", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("Honor", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("iPhone", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("Lenovo", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("HTC", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("LG", 115);
        productItemArrayList.add(productItem);

        productItem = new ProductItem("Micromax", 115);
        productItemArrayList.add(productItem);

        ProductAdapter productAdapter = new ProductAdapter(productItemArrayList, this);
        recyclerView.setAdapter(productAdapter);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int count = 0;
        for (ProductItem productItem : productItemArrayList) {
            if (productItem.isProductState()) {
                amount = amount + productItem.getPrice();
                count++;
            }
        }

        if (count == 0) {
            Toast.makeText(this, "Please add product to cart", Toast.LENGTH_SHORT).show();
        } else {
            onBuyPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBuyPressed() {

        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(ProductListActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal(amount), "USD", "sample item",
                paymentIntent);
    }

    @Override
    public void onItemSelected(int count) {

    }
}

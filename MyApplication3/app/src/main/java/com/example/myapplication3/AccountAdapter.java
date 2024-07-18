package com.example.myapplication3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.swagger.client.model.Account;
import io.swagger.client.model.AccountVO;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {
    private List<MyAccount> myaccountList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        public ImageView bankLogo;
        public TextView accountName;
        public TextView accountNumber;
        public TextView cardType;
        public TextView balance;
        public TextView account_id;

        public AccountViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            bankLogo = itemView.findViewById(R.id.bankLogo);
            accountName = itemView.findViewById(R.id.accountName);
            accountNumber = itemView.findViewById(R.id.accountNumber);
            cardType = itemView.findViewById(R.id.cardType);
            balance = itemView.findViewById(R.id.balance);
            account_id = itemView.findViewById(R.id.account_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public AccountAdapter(List<MyAccount> myaccountList) {
        this.myaccountList = myaccountList;
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        MyAccount currentAccount = myaccountList.get(position);
        holder.accountName.setText("账户名称:" + currentAccount.getAccountName());
        holder.accountNumber.setText("账户号码:" + currentAccount.getAccountNumber());
        holder.cardType.setText("账户类型:" + currentAccount.getCardType());
        holder.balance.setText("余额：" + currentAccount.getBalance());
        holder.account_id.setText("账户ID：" + currentAccount.getAccount_Id());
        Picasso.get().load(currentAccount.getBankLogoURL()).into(holder.bankLogo);
    }

    @Override
    public int getItemCount() {
        return myaccountList.size();
    }
}

package com.example.myapplication3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FinanceRecordAdapter extends RecyclerView.Adapter<FinanceRecordAdapter.FinanceRecordViewHolder> {

    private List<MyFinanceRecord> financeRecordList;

    public FinanceRecordAdapter(List<MyFinanceRecord> financeRecordList) {
        this.financeRecordList = financeRecordList;
    }

    @NonNull
    @Override
    public FinanceRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financerecord, parent, false);
        return new FinanceRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FinanceRecordViewHolder holder, int position) {
        MyFinanceRecord financeRecord = financeRecordList.get(position);
        holder.tvFinanceRecordId.setText("交易记录ID："+financeRecord.getFinanceRecordId());
        holder.tvTransactionDesc.setText("交易记录描述："+financeRecord.getTransactionDesc());
        holder.tvAccountId.setText("账户ID："+financeRecord.getAccountId());
        holder.tvAmount.setText("金额："+financeRecord.getAmount());
        holder.tvTransactionType.setText("交易类型："+financeRecord.getTransactionType());
        holder.tvCategory.setText("交易记录类别："+financeRecord.getCategory());
        holder.tvTransactionTime.setText("交易时间:"+financeRecord.getTransactionTime());
    }

    @Override
    public int getItemCount() {
        return financeRecordList.size();
    }

    public static class FinanceRecordViewHolder extends RecyclerView.ViewHolder {
        TextView tvFinanceRecordId, tvTransactionDesc, tvAccountId, tvAmount, tvTransactionType, tvCategory, tvTransactionTime;

        public FinanceRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFinanceRecordId = itemView.findViewById(R.id.tvFinanceRecordId);
            tvTransactionDesc = itemView.findViewById(R.id.tvTransactionDesc);
            tvAccountId = itemView.findViewById(R.id.tvAccountId);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvTransactionType = itemView.findViewById(R.id.tvTransactionType);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvTransactionTime = itemView.findViewById(R.id.tvTransactionTime);
        }
    }
}

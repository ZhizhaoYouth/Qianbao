package com.example.myapplication3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    private List<MyBudget> myBudgetList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class BudgetViewHolder extends RecyclerView.ViewHolder {
        public TextView budget_id;
        public TextView budgetdesc;
        public TextView account_id;
        public TextView budget;
        public TextView bgt_category;
        public TextView startDate;
        public TextView endDate;

        public BudgetViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            budget_id = itemView.findViewById(R.id.budget_id);
            budgetdesc = itemView.findViewById(R.id.budgetdesc);
            account_id = itemView.findViewById(R.id.account_id);
            budget = itemView.findViewById(R.id.budget);
            bgt_category = itemView.findViewById(R.id.bgt_category);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);

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

    public BudgetAdapter(List<MyBudget> myBudgetList) {
        this.myBudgetList = myBudgetList;
    }

//    @NonNull
//    @Override
//    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget, parent, false);
//        return new BudgetViewHolder(itemView);
//    }
    @Override
    public BudgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        MyBudget currentBudget = myBudgetList.get(position);
        holder.budget_id.setText("预算ID：" + currentBudget.getBudget_Id());
        holder.budgetdesc.setText("预算描述：" + currentBudget.getBudgetdesc());
        holder.account_id.setText("账户ID：" + currentBudget.getAccount_Id());
        holder.budget.setText("预算金额：" + currentBudget.getBudget());
        holder.bgt_category.setText("预算类别：" + currentBudget.getBgt_category());
        holder.startDate.setText("开始日期：" + currentBudget.getStartDate());
        holder.endDate.setText("结束日期：" + currentBudget.getEndDate());
    }

    @Override
    public int getItemCount() {
        return myBudgetList.size();
    }
}

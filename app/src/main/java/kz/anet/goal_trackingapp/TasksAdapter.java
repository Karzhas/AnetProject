package kz.anet.goal_trackingapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder>{

    private List<Task> tasks;
    private Context mContext;
    public TasksAdapter(Context context) {
        tasks = new ArrayList<>();
        mContext = context;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_task, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int pos) {
        Task currentTask = tasks.get(pos);
        holder.title.setText(currentTask.getTitle());
        holder.createdAtDate.setText(currentTask.getCreatedAtDate());
        holder.createdAtTime.setText(currentTask.getCreatedAtTime());
        if(currentTask.getDone()){
            holder.done.setImageResource(R.drawable.done);
        }else{
            Drawable drawable = ContextCompat.getDrawable(mContext,R.drawable.circle_done);
            holder.done.setBackground(drawable);
        }
        // done
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView createdAtDate;
        ImageButton done;
        ImageButton calendar;
        ImageButton time;
        TextView createdAtTime;
        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            createdAtDate = itemView.findViewById(R.id.txt_createdAt);
            done = itemView.findViewById(R.id.btn_done);
            calendar = itemView.findViewById(R.id.img_calendar);
            time = itemView.findViewById(R.id.img_time);
            createdAtTime = itemView.findViewById(R.id.txt_created_time);
        }
    }
}

package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Courses;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

public class OLDCourseAdapter extends RecyclerView.Adapter<OLDCourseAdapter.ViewHolder> {

    private List<Courses> coursesList;
    private Context mContext;

    public OLDCourseAdapter(List<Courses> coursesList, Context mContext) {
        this.coursesList = coursesList;
        this.mContext = mContext;
    }

    // Work whenever viewHolder created.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_times_template, parent, false);

        return new ViewHolder(listView);

    }


    // Binds the data to the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder courseViewHolder, int position) {
        Courses course = coursesList.get(position);

        //courseViewHolder.time.setText("14:30-16:20");
        //courseViewHolder.code.setText("SE314(2)");
        //courseViewHolder.name.setText("OPERATING SYSTEMS AND NETWORKING");
        //courseViewHolder.classroom.setText("Y216(65)");
        //courseViewHolder.lecturer.setText("MEHMET SÜLEYMAN ÜNLÜTÜRK");
    }



    // Return list size
    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView time;
        TextView code;
        TextView name;
        TextView classroom;
        TextView lecturer;

        public ViewHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.course_time);
            code = itemView.findViewById(R.id.course_code);
            name = itemView.findViewById(R.id.course_name);
            classroom = itemView.findViewById(R.id.course_class);
            lecturer = itemView.findViewById(R.id.course_lecturer);
        }
    }
}

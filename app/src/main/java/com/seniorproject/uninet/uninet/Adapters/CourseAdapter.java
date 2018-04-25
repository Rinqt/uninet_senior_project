package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.Courses;
import com.seniorproject.uninet.uninet.R;

import java.util.List;

import static com.seniorproject.uninet.uninet.Courses.COURSE_TYPE;
import static com.seniorproject.uninet.uninet.Courses.FRIDAY;
import static com.seniorproject.uninet.uninet.Courses.MONDAY;
import static com.seniorproject.uninet.uninet.Courses.THURSDAY;
import static com.seniorproject.uninet.uninet.Courses.TUESDAY;
import static com.seniorproject.uninet.uninet.Courses.WEDNESDAY;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Courses> mList;
    private Context mContext;

    public CourseAdapter(Context context, List<Courses> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MONDAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_days, parent, false);
                return new DateViewHolder(view);
            case TUESDAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_days, parent, false);
                return new DateViewHolder(view);
            case WEDNESDAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_days, parent, false);
                return new DateViewHolder(view);
            case THURSDAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_days, parent, false);
                return new DateViewHolder(view);
            case FRIDAY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_days, parent, false);
                return new DateViewHolder(view);
            case COURSE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_times_template, parent, false);
                return new CourseViewHolder(view);

        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Courses object = mList.get(position);
        if (object != null) {
            switch (object.getType()) {
                case MONDAY:
                    ((DateViewHolder) holder).day.setText(R.string.monday);
                    break;
                case TUESDAY:
                    ((DateViewHolder) holder).day.setText(R.string.tuesday);
                    break;
                case WEDNESDAY:
                    ((DateViewHolder) holder).day.setText(R.string.wednesday);
                    break;
                case THURSDAY:
                    ((DateViewHolder) holder).day.setText(R.string.thursday);
                    break;
                case FRIDAY:
                    ((DateViewHolder) holder).day.setText(R.string.friday);
                    break;
                case COURSE_TYPE:

                    if (object.getIsTheory().equals("False"))
                    {
                        int bgColor = ContextCompat.getColor(mContext, R.color.theory_class);
                        ((CourseViewHolder) holder).time.setBackgroundColor(bgColor);
                        ((CourseViewHolder) holder).code.setBackgroundColor(bgColor);
                        ((CourseViewHolder) holder).name.setBackgroundColor(bgColor);
                        ((CourseViewHolder) holder).classroom.setBackgroundColor(bgColor);
                        ((CourseViewHolder) holder).lecturer.setBackgroundColor(bgColor);
                    }
                    ((CourseViewHolder) holder).time.setText(object.getTime());
                    ((CourseViewHolder) holder).code.setText(object.getCode());
                    ((CourseViewHolder) holder).name.setText(object.getName());
                    ((CourseViewHolder) holder).classroom.setText(object.getClassroom());
                    ((CourseViewHolder) holder).lecturer.setText(object.getLecturer());
                    break;



            }
        }
    }
    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (mList != null) {
            Courses object = mList.get(position);
            if (object != null) {
                return object.getType();
            }
        }
        return 0;
    }


    // Holder Classes
    public static class DateViewHolder extends RecyclerView.ViewHolder {
        private TextView day;
        public DateViewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.course_days);
        }
    }
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        TextView code;
        TextView name;
        TextView classroom;
        TextView lecturer;

        public CourseViewHolder(View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.course_time);
            code = itemView.findViewById(R.id.course_code);
            name = itemView.findViewById(R.id.course_name);
            classroom = itemView.findViewById(R.id.course_class);
            lecturer = itemView.findViewById(R.id.course_lecturer);
        }
    }
}
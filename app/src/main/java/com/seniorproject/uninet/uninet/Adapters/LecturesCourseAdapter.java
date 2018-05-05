package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.ConstructorClasses.LecturesSystemCourses;
import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.TranscriptActivity;

import java.util.List;

public class LecturesCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private List<LecturesSystemCourses> mLecturesCourses;
    private Context mContext;

    public LecturesCourseAdapter(Context context, List<LecturesSystemCourses> lecturesCourses) {
        this.mContext = context;
        this.mLecturesCourses = lecturesCourses;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View lecturesView;

        lecturesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lectures_course_template, parent, false);
        return new LectureList(lecturesView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        LecturesSystemCourses lecturesCourse = mLecturesCourses.get(position);

        if(lecturesCourse != null)
        {
            ((LectureList) holder).lectureCode.setText(lecturesCourse.getLectureCode());
            ((LectureList) holder).lectureName.setText(lecturesCourse.getLectureName());
            ((LectureList) holder).goToLecture.setImageResource(R.drawable.ic_enter_arrow);

            ((LectureList) holder).lectureCode.setOnClickListener(this);
            ((LectureList) holder).lectureName.setOnClickListener(this);
            ((LectureList) holder).goToLecture.setOnClickListener(this);
        }

    }

    @Override
    public int getItemCount() {
        if (mLecturesCourses == null)
            return 0;
        return mLecturesCourses.size();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, TranscriptActivity.class);
        mContext.startActivity(intent);
    }

    public static class LectureList extends RecyclerView.ViewHolder
    {
        private TextView lectureCode;
        private TextView lectureName;
        private ImageView goToLecture;

        LectureList(View itemView) {
            super(itemView);

            lectureCode = itemView.findViewById(R.id.lecture_code);
            lectureName = itemView.findViewById(R.id.lecture_name);
            goToLecture = itemView.findViewById(R.id.go_to_lecture_button);
        }
    }
}

package com.seniorproject.uninet.uninet.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seniorproject.uninet.uninet.R;
import com.seniorproject.uninet.uninet.TranscriptRecords;

import java.util.List;

import static com.seniorproject.uninet.uninet.TranscriptRecords.COURSE;
import static com.seniorproject.uninet.uninet.TranscriptRecords.FALL;
import static com.seniorproject.uninet.uninet.TranscriptRecords.HEADER;
import static com.seniorproject.uninet.uninet.TranscriptRecords.SPRING;
import static com.seniorproject.uninet.uninet.TranscriptRecords.SUMMARY;
import static com.seniorproject.uninet.uninet.TranscriptRecords.SUMMARYTITLE;

public class TranscriptAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<TranscriptRecords> mList;
    private Context mContext;

    public TranscriptAdapter(Context context, List<TranscriptRecords> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case FALL:
                View fallSemester;
                fallSemester = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcript_course_year_template, parent, false);
                return new SemesterHeaderViewHolder(fallSemester);

            case SPRING:
                View springSemester;
                springSemester = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcript_course_year_template, parent, false);
                return new SemesterHeaderViewHolder(springSemester);

            case HEADER:
                View transcriptHeaders;
                transcriptHeaders = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcript_records_titles_template, parent, false);
                return new TranscriptRecordsViewHolder(transcriptHeaders);


            case COURSE:
                View transcriptCourses;
                transcriptCourses = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcript_records_template, parent, false);
                return new TranscriptRecordsViewHolder(transcriptCourses);

            case SUMMARYTITLE:
                View summaryTitle;
                summaryTitle = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcript_records_semester_summary_template, parent, false);
                return new SummaryViewHolder(summaryTitle);

            case SUMMARY:
                View summary;
                summary = LayoutInflater.from(parent.getContext()).inflate(R.layout.transcript_records_semester_summary_template, parent, false);
                return new SummaryViewHolder(summary);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        Resources resources = mContext.getResources();
        TranscriptRecords object = mList.get(position);

        if (object != null)
        {
            switch (object.getType()) {

                case FALL:

                    String getFallSemester = resources.getString(R.string.tr_semester_fall, object.getEducationYear());
                    ((SemesterHeaderViewHolder) holder).trSemesterYear.setText(getFallSemester);
                    break;

                case SPRING:
                    String getSpringSemester = resources.getString(R.string.tr_semester_spring, object.getEducationYear());
                    ((SemesterHeaderViewHolder) holder).trSemesterYear.setText(getSpringSemester);
                    break;

                case HEADER:

                    ((TranscriptRecordsViewHolder) holder).trUnit.setText(R.string.tr_title_course_unit);
                    ((TranscriptRecordsViewHolder) holder).trUnitTitle.setText(R.string.tr_title_course_unit_title);
                    ((TranscriptRecordsViewHolder) holder).trSuccessGrade.setText(R.string.tr_title_success_notes);
                    ((TranscriptRecordsViewHolder) holder).trEcts.setText(R.string.tr_title_etcs);
                    ((TranscriptRecordsViewHolder) holder).trPoint.setText(R.string.tr_title_point);
                    break;

                case COURSE:

                    ((TranscriptRecordsViewHolder) holder).trUnit.setText(object.getUnit());
                    ((TranscriptRecordsViewHolder) holder).trUnitTitle.setText(object.getUnitTitle());
                    ((TranscriptRecordsViewHolder) holder).trSuccessGrade.setText(object.getSuccessGrade());
                    ((TranscriptRecordsViewHolder) holder).trEcts.setText(object.getEcts());
                    ((TranscriptRecordsViewHolder) holder).trPoint.setText(object.getPoint());
                    break;

                case SUMMARYTITLE:

                    ((SummaryViewHolder) holder).trSemesterGpa.setText(R.string.tr_summary_title_semester_gpa);
                    ((SummaryViewHolder) holder).trCumulativeGpa.setText(R.string.tr_summary_title_cumulative_gpa);
                    ((SummaryViewHolder) holder).trSemesterTotalEcts.setText(R.string.tr_summary_title_semester_ects);
                    ((SummaryViewHolder) holder).trSemesterTotalPoints.setText(R.string.tr_summary_title_semester_points);
                    ((SummaryViewHolder) holder).trTotalEcts.setText(R.string.tr_summary_title_total_ects);
                    ((SummaryViewHolder) holder).trTotalPoints.setText(R.string.tr_summary_title_total_points);
                    break;

                case SUMMARY:

                    ((SummaryViewHolder) holder).trSemesterGpa.setText(" ");
                    ((SummaryViewHolder) holder).trCumulativeGpa.setText(" ");

                    ((SummaryViewHolder) holder).trSemesterTotalEcts.setText(" ");

                    ((SummaryViewHolder) holder).trSemesterTotalPoints.setText(" ");

                    ((SummaryViewHolder) holder).trTotalEcts.setText(" ");

                    ((SummaryViewHolder) holder).trTotalPoints.setText(" ");
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
            TranscriptRecords object = mList.get(position);
            if (object != null) {
                return object.getType();
            }
        }
        return 0;
    }

    // Holder Class
    public static class TranscriptRecordsViewHolder extends RecyclerView.ViewHolder {

        private TextView trUnit;
        private TextView trUnitTitle;
        private TextView trSuccessGrade;
        private TextView trEcts;
        private TextView trPoint;


        TranscriptRecordsViewHolder(View itemView) {
            super(itemView);

            trUnit = itemView.findViewById(R.id.course_unit);
            trUnitTitle = itemView.findViewById(R.id.course_unit_title);
            trSuccessGrade = itemView.findViewById(R.id.course_success_grade);
            trEcts = itemView.findViewById(R.id.course_ects);
            trPoint = itemView.findViewById(R.id.course_point);
        }
    }

    public static class SemesterHeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView trSemesterYear;


        SemesterHeaderViewHolder(View itemView) {
            super(itemView);

            trSemesterYear = itemView.findViewById(R.id.semester_year);

        }
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {

        private TextView trSemesterGpa;
        private TextView trCumulativeGpa;
        private TextView trSemesterTotalEcts;
        private TextView trSemesterTotalPoints;
        private TextView trTotalEcts;
        private TextView trTotalPoints;


        SummaryViewHolder(View itemView) {
            super(itemView);

            trSemesterGpa = itemView.findViewById(R.id.semester_gpa);
            trCumulativeGpa = itemView.findViewById(R.id.cumulative_gpa);
            trSemesterTotalEcts = itemView.findViewById(R.id.semester_total_ects);
            trSemesterTotalPoints = itemView.findViewById(R.id.semester_total_points);
            trTotalEcts = itemView.findViewById(R.id.total_ects);
            trTotalPoints = itemView.findViewById(R.id.total_points);
        }
    }


}


package com.example.storageapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.storageapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class ReportsFragment extends Fragment {

    LineChart lineChart;

    public ReportsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reports, container, false);

        lineChart = (LineChart) rootView.findViewById(R.id.lineChart);

        ArrayList<Entry> resultados = new ArrayList<>();
        resultados.add(new Entry(2014, 420));
        resultados.add(new Entry(2015, 589));
        resultados.add(new Entry(2016, 700));
        resultados.add(new Entry(2017, 205));
        resultados.add(new Entry(2018, 490));
        resultados.add(new Entry(2019, 999));
        resultados.add(new Entry(2020, 120));

        LineDataSet lineDataSet = new LineDataSet(resultados, "Resultados");
        lineDataSet.setColor(R.color.red);
        lineDataSet.setValueTextColor(R.color.black);
        lineDataSet.setValueTextSize(16f);

        LineData lineData = new LineData(lineDataSet);

        lineChart.setData(lineData);

        return rootView;
    }
}
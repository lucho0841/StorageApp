package com.example.storageapp.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.storageapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class ReportsFragment extends Fragment {

    BarChart barChart;
    String[] xValues = {};
    int productoId, valorProducto;

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

        //Toast.makeText(getContext(), "Productos: " + gridProducts, Toast.LENGTH_LONG).show();

        barChart = (BarChart) rootView.findViewById(R.id.barChart);

        ArrayList<BarEntry> productos = new ArrayList<>();
        productos.add(new BarEntry(1, 420));
        productos.add(new BarEntry(2, 589));
        productos.add(new BarEntry(3, 700));

        BarDataSet barDataSet = new BarDataSet(productos, "Productos ordenados por precio");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.animateY(2000);

        return rootView;
    }
}
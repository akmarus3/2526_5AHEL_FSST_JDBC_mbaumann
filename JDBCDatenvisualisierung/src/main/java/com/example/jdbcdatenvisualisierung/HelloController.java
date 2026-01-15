package com.example.jdbcdatenvisualisierung;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.util.*;

public class HelloController {

    @FXML
    private ListView<String> countryList;

    @FXML
    private PieChart pieChart;

    private final Map<String,String> countryMap = new HashMap<>();

    @FXML
    public void initialize() {
        try {
            countryMap.putAll(CountryDAO.loadCountries());
            countryList.getItems().addAll(countryMap.keySet());
            countryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadChart() {
        try {
            List<String> selected = countryList.getSelectionModel().getSelectedItems();
            if (selected.isEmpty()) return;

            List<String> codes = new ArrayList<>();
            for (String name : selected) {
                codes.add(countryMap.get(name));
            }

            List<LanguageStat> stats = CountryDAO.loadLanguages(codes);
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

            for (LanguageStat stat : stats) {
                PieChart.Data d = new PieChart.Data(stat.getLanguage(), stat.getPercentage());
                data.add(d);

                d.nodeProperty().addListener((obs, old, node) -> {
                    if (node != null && stat.isOfficial()) {
                        node.setStyle("-fx-pie-color: red;");
                    }
                });
            }

            pieChart.setData(data);
            pieChart.setTitle("Sprachen – Durchschnitt über " + selected.size() + " Länder");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

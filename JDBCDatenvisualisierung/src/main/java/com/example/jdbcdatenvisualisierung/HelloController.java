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

    private final Map<String, String> countryMap = new HashMap<>();

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
            if (selected.isEmpty()) {
                pieChart.setData(FXCollections.observableArrayList());
                pieChart.setTitle("");
                return;
            }

            List<String> codes = new ArrayList<>();
            for (String name : selected) {
                codes.add(countryMap.get(name));
            }

            List<LanguageStat> stats = CountryDAO.loadLanguages(codes);

            /* =====================================================
               SONDERFALL: GENAU EINE SPRACHE
               ===================================================== */
            if (stats.size() == 1) {
                LanguageStat stat = stats.get(0);

                PieChart.Data single = new PieChart.Data(stat.getLanguage(), 1);

                pieChart.setData(FXCollections.observableArrayList(single));
                pieChart.setLegendVisible(true);
                pieChart.setLabelsVisible(false);
                pieChart.setTitle("Sprachen – Durchschnitt über " + selected.size() + " Land");

                single.nodeProperty().addListener((obs, o, n) -> {
                    if (n != null && stat.isOfficial()) {
                        n.setStyle("-fx-pie-color: red;");
                    }
                });

                return; // 🔴 extrem wichtig
            }

            /* =====================================================
               NORMALFALL: MEHRERE SPRACHEN
               ===================================================== */
            ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

            for (LanguageStat stat : stats) {
                PieChart.Data d = new PieChart.Data(
                        stat.getLanguage(),
                        stat.getPercentage()
                );
                data.add(d);

                d.nodeProperty().addListener((obs, old, node) -> {
                    if (node != null && stat.isOfficial()) {
                        node.setStyle("-fx-pie-color: red;");
                    }
                });
            }

            pieChart.setLegendVisible(true);
            pieChart.setLabelsVisible(true);
            pieChart.setData(data);
            pieChart.setTitle(
                    "Sprachen – Durchschnitt über " + selected.size() + " Länder"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

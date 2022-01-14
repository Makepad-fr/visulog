/*
	22015094 - Idil Saglam
*/
package up.visulog.chartbuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class BarChartSample extends Application {
    static final String daily = "Daily";
    static final String weekly = "Weekly";
    static final String monthly = "Per month";
    static final String year = "Per year";
    static final String overAll = "Average";

    @Override
    public void start(Stage stage) {
        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Commits");
        xAxis.setLabel("Username");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Idil");
        series1.getData().add(new XYChart.Data(daily, 25601.34));
        series1.getData().add(new XYChart.Data(weekly, 20148.82));
        series1.getData().add(new XYChart.Data(monthly, 10000));
        series1.getData().add(new XYChart.Data(year, 35407.15));
        series1.getData().add(new XYChart.Data(overAll, 12000));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Kaan");
        series2.getData().add(new XYChart.Data(daily, 57401.85));
        series2.getData().add(new XYChart.Data(weekly, 41941.19));
        series2.getData().add(new XYChart.Data(monthly, 45263.37));
        series2.getData().add(new XYChart.Data(year, 117320.16));
        series2.getData().add(new XYChart.Data(overAll, 14845.27));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Dudukovic");
        series3.getData().add(new XYChart.Data(daily, 45000.65));
        series3.getData().add(new XYChart.Data(weekly, 44835.76));
        series3.getData().add(new XYChart.Data(monthly, 18722.18));
        series3.getData().add(new XYChart.Data(year, 17557.31));
        series3.getData().add(new XYChart.Data(overAll, 92633.68));

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1, series2, series3);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

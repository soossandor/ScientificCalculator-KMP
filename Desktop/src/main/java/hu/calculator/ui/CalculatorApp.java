package hu.calculator.ui;

import hu.calculator.core.ast.ExpressionNode;
import hu.calculator.core.parser.ExpressionParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    private TextField display;
    private ExpressionParser parser;
    private ListView<String> historyList;

    @Override
    public void start(Stage primaryStage) {
        parser = new ExpressionParser();

        // --- BAL OLDAL: Kijelző és Fülek ---
        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setStyle("-fx-background-color: #2b2b2b;");
        // Hagyjuk, hogy a bal panel kitöltse az üres helyet, ha megnő az ablak
        HBox.setHgrow(leftPanel, Priority.ALWAYS);

        display = new TextField();
        display.setFont(Font.font("Consolas", 28));
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setEditable(true);
        display.setPrefHeight(60);
        display.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: #a9b7c6; -fx-border-color: #555555; -fx-border-radius: 5; -fx-background-radius: 5;");
        display.setOnAction(e -> calculateResult());

        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: #2b2b2b;");
        // A fülek is nyúljanak meg lefelé!
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        // --- 1. FÜL: Alapműveletek ---
        Tab basicTab = new Tab("Alapműveletek");
        basicTab.setClosable(false);
        String[][] basicLabels = {
                {"C", "(", ")", "Del"},
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"", "0", ".", "+"},
                {"", "", "", "="}
        };
        basicTab.setContent(createButtonGrid(basicLabels));

// --- 2. FÜL: Tudományos ---
        Tab sciTab = new Tab("Tudományos");
        sciTab.setClosable(false);
        String[][] sciLabels = {
                {"sin(", "cos(", "tan(", "Del"},
                {"log(", "abs(", "sqrt(", "C"},
                {"pi", "e", "^", "/"},
                {"7", "8", "9", "*"},
                {"4", "5", "6", "-"},
                {"1", "2", "3", "+"},
                {"(", "0", ")", "="}
        };
        sciTab.setContent(createButtonGrid(sciLabels));

        // --- 3. FÜL: Függvényrajzoló ---
        Tab plotTab = new Tab("Függvényrajzoló");
        plotTab.setClosable(false);

        VBox plotBox = new VBox(15);
        plotBox.setPadding(new Insets(15));
        plotBox.setStyle("-fx-background-color: #2b2b2b;");

        HBox topControlBox = new HBox(30);
        topControlBox.setAlignment(Pos.TOP_LEFT);

        GridPane axisSettings = new GridPane();
        axisSettings.setHgap(10); axisSettings.setVgap(8);

        String labelStyle = "-fx-text-fill: #a9b7c6; -fx-font-size: 14px;";
        String inputStyle = "-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-pref-width: 50px;";

        Label xLabel = new Label("X tengely:"); xLabel.setStyle(labelStyle);
        TextField xMinIn = new TextField("-10"); xMinIn.setStyle(inputStyle);
        TextField xMaxIn = new TextField("10"); xMaxIn.setStyle(inputStyle);
        TextField xTickIn = new TextField("1"); xTickIn.setStyle(inputStyle);

        Label yLabel = new Label("Y tengely:"); yLabel.setStyle(labelStyle);
        TextField yMinIn = new TextField("-5"); yMinIn.setStyle(inputStyle);
        TextField yMaxIn = new TextField("5"); yMaxIn.setStyle(inputStyle);
        TextField yTickIn = new TextField("1"); yTickIn.setStyle(inputStyle);

        Label l1 = new Label("Min:"); l1.setStyle(labelStyle); Label l2 = new Label("Max:"); l2.setStyle(labelStyle); Label l3 = new Label("Lépés:"); l3.setStyle(labelStyle);
        Label l4 = new Label("Min:"); l4.setStyle(labelStyle); Label l5 = new Label("Max:"); l5.setStyle(labelStyle); Label l6 = new Label("Lépés:"); l6.setStyle(labelStyle);

        axisSettings.addRow(0, xLabel, l1, xMinIn, l2, xMaxIn, l3, xTickIn);
        axisSettings.addRow(1, yLabel, l4, yMinIn, l5, yMaxIn, l6, yTickIn);

        VBox funcBox = new VBox(8);
        String funcInputStyle = "-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-pref-width: 140px; -fx-font-family: 'Consolas';";

        HBox fRow = new HBox(5); fRow.setAlignment(Pos.CENTER_LEFT);
        Label fLbl = new Label("f(x) ="); fLbl.setStyle("-fx-text-fill: #ff6347; -fx-font-weight: bold;");
        TextField fIn = new TextField("sin(x)"); fIn.setStyle(funcInputStyle);
        fRow.getChildren().addAll(fLbl, fIn);

        HBox gRow = new HBox(5); gRow.setAlignment(Pos.CENTER_LEFT);
        Label gLbl = new Label("g(x) ="); gLbl.setStyle("-fx-text-fill: #4682b4; -fx-font-weight: bold;");
        TextField gIn = new TextField("cos(x)"); gIn.setStyle(funcInputStyle);
        gRow.getChildren().addAll(gLbl, gIn);

        HBox hRow = new HBox(5); hRow.setAlignment(Pos.CENTER_LEFT);
        Label hLbl = new Label("h(x) ="); hLbl.setStyle("-fx-text-fill: #32cd32; -fx-font-weight: bold;");
        TextField hIn = new TextField(""); hIn.setStyle(funcInputStyle);
        hRow.getChildren().addAll(hLbl, hIn);

        funcBox.getChildren().addAll(fRow, gRow, hRow);
        topControlBox.getChildren().addAll(axisSettings, funcBox);

        // --- ÚJ: Gombok egymás mellett ---
        HBox actionBox = new HBox(15);
        actionBox.setAlignment(Pos.CENTER);

        Button drawBtn = new Button("Grafikon Frissítése");
        drawBtn.setStyle("-fx-background-color: #cc7832; -fx-text-fill: white; -fx-font-weight: bold; -fx-pref-width: 200px;");

        Button fullscreenBtn = new Button("⛶ Teljes képernyő");
        fullscreenBtn.setStyle("-fx-background-color: #4b4d4e; -fx-text-fill: white; -fx-font-weight: bold; -fx-pref-width: 150px;");

        // Teljes képernyő váltó logika
        fullscreenBtn.setOnAction(e -> {
            primaryStage.setFullScreen(!primaryStage.isFullScreen());
        });

        actionBox.getChildren().addAll(drawBtn, fullscreenBtn);

        javafx.scene.chart.NumberAxis xAxis = new javafx.scene.chart.NumberAxis();
        xAxis.setAutoRanging(false);
        javafx.scene.chart.NumberAxis yAxis = new javafx.scene.chart.NumberAxis();
        yAxis.setAutoRanging(false);

        javafx.scene.chart.LineChart<Number, Number> lineChart = new javafx.scene.chart.LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(true);
        lineChart.setAnimated(false);
        VBox.setVgrow(lineChart, Priority.ALWAYS); // A grafikon nyúljon meg maximálisan!

        drawBtn.setOnAction(e -> {
            try {
                xAxis.setLowerBound(Double.parseDouble(xMinIn.getText()));
                xAxis.setUpperBound(Double.parseDouble(xMaxIn.getText()));
                xAxis.setTickUnit(Double.parseDouble(xTickIn.getText()));

                yAxis.setLowerBound(Double.parseDouble(yMinIn.getText()));
                yAxis.setUpperBound(Double.parseDouble(yMaxIn.getText()));
                yAxis.setTickUnit(Double.parseDouble(yTickIn.getText()));

                lineChart.getData().clear();

                TextField[] inputs = {fIn, gIn, hIn};
                for (TextField input : inputs) {
                    String expr = input.getText();
                    if (expr.isEmpty()) continue;

                    ExpressionNode rootNode = parser.parse(expr);
                    javafx.scene.chart.XYChart.Series<Number, Number> series = new javafx.scene.chart.XYChart.Series<>();
                    series.setName(expr);

                    double minX = xAxis.getLowerBound();
                    double maxX = xAxis.getUpperBound();
                    double step = (maxX - minX) / 1000.0; // Még finomabb vonalak nagy felbontásnál

                    for (double x = minX; x <= maxX; x += step) {
                        // ÚJ: A Kotlin Companion object meghívása Java-ból
                        hu.calculator.core.ast.VariableNode.setX(x);
                        try {
                            // ÚJ: Az asDouble() segédfüggvény meghívása
                            double y = ((hu.calculator.core.types.RealNumber) rootNode.evaluate()).asDouble();

                            if (y >= yAxis.getLowerBound() - 10 && y <= yAxis.getUpperBound() + 10) {
                                series.getData().add(new javafx.scene.chart.XYChart.Data<>(x, y));
                            }
                        } catch (Exception ignored) {}
                    }                    lineChart.getData().add(series);
                }
                drawBtn.setText("Grafikon Frissítése");
                drawBtn.setStyle("-fx-background-color: #cc7832; -fx-text-fill: white; -fx-font-weight: bold; -fx-pref-width: 200px;");
            } catch (Exception ex) {
                drawBtn.setText("Hiba a kifejezésben!");
                drawBtn.setStyle("-fx-background-color: #8b0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-pref-width: 200px;");
            }
        });

        plotBox.getChildren().addAll(topControlBox, actionBox, lineChart);
        plotTab.setContent(plotBox);

        tabPane.getTabs().addAll(basicTab, sciTab, plotTab);
        leftPanel.getChildren().addAll(display, tabPane);

        // --- JOBB OLDAL: Előzmények ---
        VBox historyBox = new VBox(10);
        historyBox.setPadding(new Insets(20, 20, 20, 0));
        historyBox.setStyle("-fx-background-color: #2b2b2b;");

        Label historyLabel = new Label("Előzmények");
        historyLabel.setFont(Font.font("Arial", 16));
        historyLabel.setStyle("-fx-text-fill: #a9b7c6; -fx-font-weight: bold;");

        historyList = new ListView<>();
        historyList.setPrefWidth(220);
        VBox.setVgrow(historyList, Priority.ALWAYS); // Az előzmények is nyúljanak meg lefelé
        historyList.setStyle("-fx-control-inner-background: #3c3f41; -fx-background-color: #2b2b2b; -fx-text-fill: white;");

        historyList.setOnMouseClicked(event -> {
            String selectedItem = historyList.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                String[] parts = selectedItem.split(" = ");
                if (parts.length > 0) {
                    display.setText(parts[0]);
                    display.positionCaret(parts[0].length());
                    display.requestFocus();
                }
            }
        });

        historyBox.getChildren().addAll(historyLabel, historyList);

        HBox mainRoot = new HBox();
        mainRoot.setStyle("-fx-background-color: #2b2b2b;");
        mainRoot.getChildren().addAll(leftPanel, historyBox);

        Scene scene = new Scene(mainRoot, 700, 650); // Kicsit szélesebb alapból

        String css = "data:text/css," +
                ".list-cell { -fx-text-fill: #a9b7c6; -fx-background-color: #3c3f41; } " +
                ".list-cell:filled:selected:focused, .list-cell:filled:selected { -fx-background-color: #4b4d4e; } " +
                ".tab-pane .tab-header-area .tab-header-background { -fx-background-color: #2b2b2b; } " +
                ".tab-pane .tab { -fx-background-color: #3c3f41; } " +
                ".tab-pane .tab:selected { -fx-background-color: #cc7832; } " +
                ".tab-pane .tab .tab-label { -fx-text-fill: white; } " +
                ".chart-plot-background { -fx-background-color: #3c3f41; } " +
                ".chart-vertical-grid-lines, .chart-horizontal-grid-lines { -fx-stroke: #555555; } " +
                ".axis { -fx-tick-label-fill: #a9b7c6; } " +
                ".axis-label { -fx-text-fill: white; -fx-font-weight: bold; } " +
                ".chart-legend { -fx-background-color: transparent; } " +
                ".chart-legend-item { -fx-text-fill: #a9b7c6; }";
        scene.getStylesheets().add(css);

        primaryStage.setTitle("Tudományos Számológép");
        primaryStage.setScene(scene);

        // ENGEDÉLYEZZÜK AZ ABLAK ÁTMÉRETEZÉSÉT!
        primaryStage.setResizable(true);
        primaryStage.show();

        Platform.runLater(() -> display.requestFocus());
    }

    private GridPane createButtonGrid(String[][] buttonLabels) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(15, 0, 0, 0));

        for (int row = 0; row < buttonLabels.length; row++) {
            for (int col = 0; col < buttonLabels[row].length; col++) {
                String text = buttonLabels[row][col];
                if (text.isEmpty()) continue;

                Button btn = new Button(text);
                btn.setPrefSize(65, 65);

                String fontSize = text.length() > 3 ? "15px" : "20px";

                if (text.matches("[0-9\\.]")) {
                    btn.setStyle("-fx-font-size: " + fontSize + "; -fx-font-family: 'Arial'; -fx-background-color: #4b4d4e; -fx-text-fill: white; -fx-background-radius: 8;");
                } else if (text.equals("=") || text.equals("C") || text.equals("Del")) {
                    btn.setStyle("-fx-font-size: " + fontSize + "; -fx-font-family: 'Arial'; -fx-background-color: #cc7832; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8;");
                } else {
                    btn.setStyle("-fx-font-size: " + fontSize + "; -fx-font-family: 'Arial'; -fx-background-color: #3c3f41; -fx-text-fill: #ffc66d; -fx-font-weight: bold; -fx-background-radius: 8;");
                }

                btn.setOnMouseEntered(e -> btn.setOpacity(0.8));
                btn.setOnMouseExited(e -> btn.setOpacity(1.0));
                btn.setOnAction(e -> handleButtonClick(text));

                grid.add(btn, col, row);
            }
        }
        return grid;
    }

    private void handleButtonClick(String value) {
        if (value.equals("C")) {
            display.clear();
            display.requestFocus();
        } else if (value.equals("Del")) {
            int caretPos = display.getCaretPosition();
            if (caretPos > 0) {
                display.deleteText(caretPos - 1, caretPos);
                display.positionCaret(caretPos - 1);
            }
            display.requestFocus();
        } else if (value.equals("=")) {
            calculateResult();
        } else {
            int caretPos = display.getCaretPosition();
            display.insertText(caretPos, value);
            display.positionCaret(caretPos + value.length());
            display.requestFocus();
        }
    }

    private void calculateResult() {
        String expression = display.getText();
        if (expression.isEmpty()) return;

        try {
            ExpressionNode rootNode = parser.parse(expression);
            String result = rootNode.evaluate().toString();

            historyList.getItems().add(0, expression + " = " + result);
            display.setText(result);
            display.positionCaret(result.length());
        } catch (Exception ex) {
            display.setText("Hiba!");
        }
        display.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
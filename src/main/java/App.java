import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class App {

    public static String alphaCode = "\u03B1" + ":";
    public static String betaCode = "\u03B2" + ":";
    public static String roCode = "\u03C1" + ":";
    public static String lyambdaCode = "\u03BB" + ":";
    public static String tauCode = "\u03C4" + ":";
    public static String phiCode = "\u03D5" + ":";
    public static String deltaCode = "\u03B4" + ":";
    private double x0;
    private double y0;
    private double x;
    private double y;
    private double alpha;
    private double beta;
    private double ro;
    private double lyambda;
    private double delta;
    private double phi;
    private double A;
    private double B;
    private double C;
    private double D;
    private double tau;
    private int N;
    private int clickAmount = 0;
    Function<Double> functionX = (x,y) -> alpha*Math.pow(x,2) + beta*y + ro;
    Function<Double> functionY = (x,y) -> lyambda*x*(delta*y + phi*x);
    @FXML
    private LineChart<Double, Double> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private VBox vBoxAlpha;

    @FXML
    private Label labelAlpha;

    @FXML
    private TextField textAlpha;

    @FXML
    private VBox vBoxAlpha1;

    @FXML
    private Label labelBeta;

    @FXML
    private TextField textBeta;

    @FXML
    private VBox vBoxAlpha2;

    @FXML
    private Label labelRo;

    @FXML
    private TextField textRo;

    @FXML
    private VBox vBoxAlpha3;

    @FXML
    private Label labelLyambda;

    @FXML
    private TextField textLyambda;

    @FXML
    private Label labelDelta;

    @FXML
    private TextField textDelta;

    @FXML
    private Label labelPhi;

    @FXML
    private TextField textPhi;

    @FXML
    private Label labelA;

    @FXML
    private TextField textA;

    @FXML
    private Label labelB;

    @FXML
    private TextField textB;

    @FXML
    private Label labelC;

    @FXML
    private TextField textC;

    @FXML
    private Label labelX0;

    @FXML
    private Label labelD;

    @FXML
    private TextField textD;

    @FXML
    private Label labelY0;

    @FXML
    private Label labelTau;

    @FXML
    private TextField textTau;

    @FXML
    private Button setValuesButton;

    @FXML
    private Label labelN;

    @FXML
    private TextField textN;

    @FXML
    private Button resetButton;

    @FXML
    private void initialize() {
        labelAlpha.setText(alphaCode);
        labelBeta.setText(betaCode);
        labelRo.setText(roCode);
        labelLyambda.setText(lyambdaCode);
        labelDelta.setText(deltaCode);
        labelPhi.setText(phiCode);
        setValues(new ActionEvent());
        lineChart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setXY(event);
                calcNextFuncValues();
            }
        });
    }


    @FXML
    void resetGraphics(ActionEvent event) {
        lineChart.getData().clear();
    }

    private void setXY(MouseEvent event){
        this.x0 = event.getX();
        this.y0 = event.getY();
        labelX0.setText(Double.toString(this.x0));
        labelY0.setText(Double.toString(this.y0));
    }

    private void calcNextFuncValues() {
        ObservableList<XYChart.Data> data = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> dataBack = FXCollections.observableArrayList();
        XYChart.Series series = new XYChart.Series();
        XYChart.Series seriesback = new XYChart.Series();
        double x = x0, y = y0;
        data.add(new XYChart.Data<Double, Double>(x0, y0));
        for (int i = 0; i < N; i++) {
            x = x + tau * functionX.apply(x, y);
            y = y + tau * functionY.apply(x, y);
            data.add(new XYChart.Data<Double, Double>(x, y));
        }
        //x = x0;
        //y = y0;
        for (int i = 0; i < N; i++) {
            x = x - tau * functionX.apply(x, y);
            y = y - tau * functionY.apply(x, y);
            //data.add(new XYChart.Data<Double, Double>(x, y));
            dataBack.add(new XYChart.Data<Double, Double>(x, y));
        }

        series.setData(data);
        seriesback.setData(dataBack);
        //lineChart.getData().add(series);
        lineChart.getData().addAll(series,seriesback);
    }

    @FXML
    void setValues(ActionEvent event) {
        try {
            setFieldValues();
        } catch (NumberFormatException e) {
            showMessage("Error while filling in fields or empty fields");
        }
    }

    private void setFieldValues() throws NumberFormatException{
        alpha = Double.parseDouble(textAlpha.getText());
        beta = Double.parseDouble(textBeta.getText());
        ro = Double.parseDouble(textRo.getText());
        lyambda = Double.parseDouble(textLyambda.getText());
        delta = Double.parseDouble(textDelta.getText());
        phi = Double.parseDouble(textPhi.getText());
        A = Double.parseDouble(textA.getText());
        B = Double.parseDouble(textB.getText());
        C = Double.parseDouble(textC.getText());
        D = Double.parseDouble(textD.getText());
        tau = Double.parseDouble(textTau.getText());
        N = Integer.parseInt(textN.getText());
        x0=0;
        y0=0;
        xAxis.setLowerBound(A);
        xAxis.setUpperBound(B);
        xAxis.setTickUnit(tau);
        yAxis.setLowerBound(C);
        yAxis.setUpperBound(D);
    }

    private void showMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}



package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.PixelReader;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Controller {
    public ColorPicker cp;
    public Slider sl;
    @FXML
    Canvas canvas;
    Model model;
    Points points;
    

    Image bgImage;
    double bgX, bgY, bgW = 300.0, bgH = 300.0;
    public void initialize(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        model = new Model();
        SliderTol();
    }
    public void SliderTol() {//толщина линии
        sl.setMin(3);
        sl.setMax(10);
        sl.setValue(3);

        flag =NewLine.getId();
    }

    public void clik_canvas(MouseEvent mouseEvent) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        model = new Model();
        model.addPoint(new Points((int) mouseEvent.getX(), (int) mouseEvent.getY()));
        for (int i = 0; i < model.getPointCount(); i++) {

            gc.fillOval(model.getPoint(i).getX(), model.getPoint(i).getY(), model.getPoint(i).getwP(), model.getPoint(i).gethP());
        }

    }

    public void open(ActionEvent actionEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        FileChooser fileChooser = new FileChooser();//класс работы с диалоговым окном
        fileChooser.setTitle("Выберите изображениe...");//заголовок диалога
//задает фильтр для указанного расшиерения
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Изображение", "*.jpg","*.png"),
                new FileChooser.ExtensionFilter("Изображение", "*.bmp"));

        File loadImageFile = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        if (loadImageFile != null) {
            //Open
            System.out.println("Процесс открытия файла");
            initDraw(gc, loadImageFile);
        }
    }


    private void initDraw(GraphicsContext gc, File file) {

        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();


        bgImage = new Image(file.toURI().toString());
        bgX = canvasWidth/4 ;
        bgY = canvasHeight/7 ;
        gc.drawImage(bgImage, bgX, bgY, bgW, bgH);

    }

    public  String flag;
    public Button NewLine;

    public void update(Model model) {
        GraphicsContext gc = canvas.getGraphicsContext2D();


        for (int i = 0; i < model.getPointCount(); i++) {
            //gc.setFill(cp.getValue());
            gc.fillOval(model.getPoint(i).getX(),model.getPoint(i).getY(),model.getPoint(i).getwP() ,model.getPoint(i).gethP());
        }
    }

    public void print(MouseEvent mouseEvent) {//для неприрывной линии
        //update();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Points points = new Points((int) mouseEvent.getX(), (int) mouseEvent.getY());
        if (flag == NewLine.getId()) {
            points.setSizePoint(sl.getValue(), sl.getValue());

            model.addPoint(points);
        } else {
            model.removePoint(points);
        }
        update(model);
    }









    public void save(ActionEvent actionEvent) throws IOException {

        WritableImage wim=new WritableImage(700,700);
        SnapshotParameters spa= new SnapshotParameters();
        spa.setTransform(Transform.scale(2,2));
        canvas.snapshot(spa,wim);
        //c2.snapshot(spa,wim);
        //c3.snapshot(spa,wim);
        File file=new File("Результат.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void lastik(ActionEvent actionEvent) {
        GraphicsContext gr = canvas.getGraphicsContext2D();
        model = new Model();
        gr.setFill(Color.WHITESMOKE);

        for (int i = 0; i < model.getPointCount(); i++) {

            gr.clearRect(model.getPoint(i).getX(), model.getPoint(i).getY(), model.getPoint(i).getwP(), model.getPoint(i).gethP());
        }


    }

    public void kar(ActionEvent actionEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        model = new Model();
        gc.setFill(Color.BLACK);

        for (int i = 0; i < model.getPointCount(); i++) {

            gc.clearRect(model.getPoint(i).getX(), model.getPoint(i).getY(), model.getPoint(i).getwP(), model.getPoint(i).gethP());
        }


    }

    public void act(ActionEvent actionEvent) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(cp.getValue());

    }

    public void click2(MouseEvent mouseEvent) {



    }
}


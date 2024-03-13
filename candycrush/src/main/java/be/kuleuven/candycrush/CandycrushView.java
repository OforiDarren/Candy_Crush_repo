package be.kuleuven.candycrush;

import be.kuleuven.candycrush.CandycrushModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.Iterator;

public class CandycrushView extends Region {
    private CandycrushModel model;
    private int widthCandy;
    private int heigthCandy;

    public CandycrushView(CandycrushModel model) {
        this.model = model;
        widthCandy = 50;
        heigthCandy = 50;
        update();
    }

    public void update(){
        getChildren().clear();
        int i = 0;
        int height = 0;
        Iterator<Integer> iter = model.getSpeelbord().iterator();
        while(iter.hasNext())
        {
            int candy = iter.next();
            Rectangle rectangle = new Rectangle(i * widthCandy, height * heigthCandy, widthCandy,heigthCandy);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);
            Text text = new Text("" + candy);
            text.setX(rectangle.getX() + (rectangle.getWidth() - text.getBoundsInLocal().getWidth()) / 2);
            text.setY(rectangle.getY() + (rectangle.getHeight() + text.getBoundsInLocal().getHeight()) / 2);
            getChildren().addAll(rectangle,text);

            if (i == model.getWidth() - 1) {
                i = 0;
                height++;
            } else {
                i++;
            }
        }
    }

    public int getIndexOfClicked(MouseEvent me){
        int index = -1;
        int row = (int) me.getY()/heigthCandy;
        int column = (int) me.getX()/widthCandy;
        System.out.println(me.getX()+" - "+me.getY()+" - "+row+" - "+column);
        if (row < model.getWidth() && column < model.getHeight()){
            index = model.getIndexFromRowColumn(row,column);
            System.out.println(index);
        }
        return index;
    }
}
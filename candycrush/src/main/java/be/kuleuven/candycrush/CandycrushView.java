package be.kuleuven.candycrush;

import  be.kuleuven.candycrush.Candy.*;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CandycrushView extends Region {
    private CandycrushModel model;
    private Position position;
    private int widthCandy;
    private int heigthCandy;

    public CandycrushView(CandycrushModel model) {
        this.model = model;
        widthCandy = 50;
        heigthCandy = 50;
        update();
    }
    public Node makeCandyShape(Position position, Candy candy) {
        return switch (candy) {
            case Candy.ZureMat zm -> createRectangle(Color.GREEN, position);
                case Candy.Drop d -> createRectangle(Color.BLACK, position);
            case Candy.Zuurtjes zu -> createRectangle(Color.ORANGE, position);
            case Candy.Spekjes sp -> createRectangle(Color.PINK, position);
            case Candy normalCandy -> createCircle(normalCandy.color(), position);
        };
    }
    private Node createCircle(int color, Position position) {
        Circle circle = new Circle();
        int circleCenterXOffset = 25;
        circle.setCenterX((position.colOfIndex() * widthCandy)+ circleCenterXOffset); // Voorbeeld: elke snoepje is 50 pixels breed
        int circleCenterYOffset = 25;
        circle.setCenterY((position.rowOfIndex() * heigthCandy)+ circleCenterYOffset); // Voorbeeld: elke snoepje is 50 pixels hoog
        circle.setRadius(20); // Voorbeeld: straal van 20 pixels
        switch (color) {
            case 0:
                circle.setFill(Color.RED);
                break;
            case 1:
                circle.setFill(Color.ROSYBROWN);
                break;
            case 2:
                circle.setFill(Color.BLUE);
                break;
            case 3:
                circle.setFill(Color.YELLOW);
                break;
            default:
                throw new IllegalArgumentException("Ongeldige kleur voor Normaal Snoepje: " + color);
        }
        return circle;
    }
    private Node createRectangle(Color color, Position position) {
        Rectangle rectangle = new Rectangle();
        rectangle.setX((position.colOfIndex()) * widthCandy); // Voorbeeld: elke snoepje is 50 pixels breed
        rectangle.setY((position.rowOfIndex()) * heigthCandy); // Voorbeeld: elke snoepje is 50 pixels hoog
        rectangle.setWidth(widthCandy); // Voorbeeld: breedte van 40 pixels
        rectangle.setHeight(heigthCandy); // Voorbeeld: hoogte van 40 pixels
        rectangle.setFill(color);
        return rectangle;
    }
    public void update(){
        getChildren().clear();
        Iterator<Candy> iter = model.getSpeelbord().iterator();
        for (int i = 0;iter.hasNext() && i < model.getBoardsize().rows()*model.getBoardsize().columns(); i++){
            Candy candyInBoard = iter.next();
            if(candyInBoard != null){
                List<Position> positionList = (List<Position>) model.getBoardPositionsOfElement(candyInBoard);
                for(Position p : positionList){
                    getChildren().addAll(makeCandyShape(p, candyInBoard));
                }
            }
            //zet hier get positions of elements functie van Board
        } //fix this #error here!
    }

    public Position getIndexOfClicked(MouseEvent me){
        int row = (int) me.getY()/heigthCandy;
        int column = (int) me.getX()/widthCandy;
        model.setPosition(row, column);
        System.out.println(me.getX()+" - "+me.getY()+" - "+row+" - "+column);
        System.out.println(model.getPosition().toIndex());
        return model.getPosition();
    }
}
module be.kuleuven.candycrush {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires be.kuleuven.CheckNeighboursInGrid;

    opens be.kuleuven.candycrush to javafx.fxml;
    exports be.kuleuven.candycrush;
}
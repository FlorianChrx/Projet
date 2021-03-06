package application;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Entities.Personnage;
import Entities.Tueur;
import Structures.Lieu;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import util.Game;

public class Controller {
	@FXML
	private VBox vBox;
	@FXML
	private Menu file;
	@FXML
	private Menu help;
	@FXML
	private Menu edit;
	@FXML
	private ImageView image;
	@FXML
	private HBox hBox;
	@FXML
	private Button enquettez;
	@FXML
	private Button capacite;
	@FXML
	private Label label;
	@FXML
	private Pane pane;
	private List<Lieu> voisin;
	private List<Rectangle> rectVoisin;
	private Rectangle tueurRectangle;
	private Game game;
	
	public void initialize() throws IOException, ClassNotFoundException{
		image = new ImageView(new Image(new FileInputStream("DATA/village.jpg"))); //instanciation de l'image
		vBox.getChildren().clear();										//reinitialisation de la vBox car sinon image invisible
		vBox.getChildren().addAll(image,hBox);
		
		tueurRectangle = new Rectangle(Double.MAX_VALUE, Double.MAX_VALUE);
		tueurRectangle.setOpacity(0.5);
		tueurRectangle.setFill(Color.valueOf("000000"));
		
		game = new Game(Main.village, Main.tueur, Main.enqueteur, Main.ia);
		
		rectVoisin = new ArrayList<>();
		voisin = new ArrayList<Lieu>();
		
		montrerLieux(game.getActualPlayer());
	}
	
	
	
	public void doEnquettez() {
		label.setText(game.resultatEvenement(game.getActualPlayer().getLieu()));
		clearVoisin();
		montrerLieux(game.getActualPlayer());
	}
	
	public void doCapacite() {
		
	}
	
	public void imageClicked(MouseEvent e) {
		
	}

	public Rectangle montrerLieu(Lieu l, Paint fill, Paint stroke) {
		Rectangle r = new Rectangle(l.getSurface().getOrigine().getX(),l.getSurface().getOrigine().getY(),l.getSurface().getWidth(),l.getSurface().getHeight());
		r.setFill(fill);
		r.setStroke(stroke);
		r.setOpacity(0.25);
		pane.getChildren().add(r);
		r.setOnMouseClicked(new RectangleHandler());
		return r;
	}
	
	public void montrerVoisinPlayer(Personnage p) {
		Lieu l = p.getLieu();
		for(Lieu lieu : l.getVoisins()) {
			rectVoisin.add(montrerLieu(lieu, Color.valueOf("ffffff"), Color.valueOf("ffffff")));
			voisin.add(lieu);
		}
	}
	
	public void montrerLieux(Personnage p) {
		montrerLieu(p.getLieu(), Color.valueOf("ffffff"), Color.valueOf("000000"));
		montrerVoisinPlayer(p);
	}
	
	public void clearVoisin() {
		pane.getChildren().removeAll(rectVoisin);
		pane.getChildren().remove(pane.getChildren().size()-1);
		rectVoisin.clear();
		voisin.clear();
	}
	
	public class RectangleHandler implements EventHandler<MouseEvent>{

		@Override
		public void handle(MouseEvent e) {
			if(rectVoisin.contains((Rectangle) e.getSource())) {
				label.setText(game.resultatEvenement(voisin.get(rectVoisin.indexOf((Rectangle) e.getSource()))));
				clearVoisin();
				montrerLieux(game.getActualPlayer());
			}
			clearVoisin();
			montrerLieux(game.getActualPlayer());
			if(game.getActualPlayer() instanceof Tueur) {
				image.setEffect(new ColorAdjust(0,0,-0.6,0));
				setRanctangleOpacity(0.3);
			} else {
				image.setEffect(new ColorAdjust(0,0,0,0));
				setRanctangleOpacity(0.5);
			}
		}
	}
	
	public void setRanctangleOpacity(double value) {
		clearVoisin();
		montrerLieux(game.getActualPlayer());
	}
}
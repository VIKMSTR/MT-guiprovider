package components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import cz.commons.utils.ViewSwitchButton;
import cz.commons.utils.dialogs.Dialog;
import cz.commons.utils.dialogs.HTMLDialog;
import exceptions.common.DuplicateException;

/**
 * @author Viktor
 */
public class CommonControls {
	private Button createButton;
	private Button searchButton;
	private Button insertButton;
	private Button removeButton;
	private Button nextStepButton;
	private Button prevStepButton;
	private Button playPauseButton;
	private Button debugButton;
	private Button resetButton;
	private Button helpButton;
	private final CheckBox stepCheckBox = new CheckBox("Krokovat");
	private final Label elementLabel = new Label("Prvek:");
	private final TextField elementText = new TextField();
	private final TextField elementText2 = new TextField();
	private final Label speedLabel = new Label("Rychlost:");
	private final Slider speedSlider = new Slider();
	private final ViewSwitchButton viewSwitchButton = new ViewSwitchButton();

	private final TextArea descriptions = new TextArea();
	private boolean stepping = false;
	protected ToolBar tb;
	private boolean debug;
	private boolean twoD;
	private Controller controller;
	ScrollPane sp;

	/**
	 * @param debug
	 *            Show debug button
	 * @param twoD
	 *            Show view switch button and 2D input
	 */
	public CommonControls(boolean debug, boolean twoD) {
		createButton = new Button("Vzory");
		searchButton = new Button("Najít");
		insertButton = new Button("Vložit");
		removeButton = new Button("Odstranit");
		nextStepButton = new Button(">>");
		prevStepButton = new Button("<<");
		playPauseButton = new Button("Play/Pause");
		resetButton = new Button("Reset");
		helpButton = new Button("?");
		/*
		 * elementText.addEventHandler(KeyEvent.KEY_PRESSED, new
		 * NumberValidationHandler(3));
		 * elementText.addEventHandler(KeyEvent.KEY_PRESSED, new
		 * NumberValidationHandler(3));
		 */
		this.debug = debug;
		this.twoD = twoD;
		if (debug) {
			debugButton = new Button("!!!");
		}
		if (twoD) {
			elementText.setPrefWidth(50);
			elementText2.setPrefWidth(50);
		}
		speedSlider.setMin(0.3);
		speedSlider.setMax(2.5);
		speedSlider.setValue(1);
	}

	public void setController(Controller c) {
		controller = c;

		setHandlers();

	}

	public int getInputValue() throws NumberFormatException {

		Integer retval = Integer.parseInt(elementText.getText());
		return retval;

	}

	public Point2D getInputValue2D() throws NumberFormatException, OutOfRangeException {

		double d1 = Double.parseDouble(elementText.getText());
		double d2 = Double.parseDouble(elementText2.getText());
		if (d1 < 0 || d2 < 0 || d1 > 100 || d2 > 100) {
			throw new OutOfRangeException("Jedna či více ze zadaných hodnot leží mimo rozsah (0-100)");
		}
		Point2D retval = new Point2D(d1, d2);
		return retval;

	}

	public ToolBar getStandardToolbarLayout() {

		if (twoD) {


			return new ToolBar(elementLabel, elementText, elementText2, new Separator(Orientation.VERTICAL),
					insertButton, removeButton, searchButton, new Separator(Orientation.VERTICAL), viewSwitchButton,
					new Separator(Orientation.VERTICAL), stepCheckBox, prevStepButton, nextStepButton, new Separator(
							Orientation.VERTICAL), speedLabel, speedSlider, playPauseButton, createButton, resetButton,
					helpButton);
			
		}
		return new ToolBar(elementLabel, elementText, new Separator(Orientation.VERTICAL), insertButton, removeButton,
				searchButton, new Separator(Orientation.VERTICAL), stepCheckBox, prevStepButton, nextStepButton,
				new Separator(Orientation.VERTICAL), speedLabel, speedSlider, playPauseButton, createButton,
				resetButton, helpButton);

	}

	public void setSteppingCheckBoxHandler(ChangeListener<Boolean> clb) {
		stepCheckBox.selectedProperty().addListener(clb);
	}

	public void setSpeedSliderHandler(ChangeListener<Number> cln) {
		speedSlider.valueProperty().addListener(cln);
	}

	public void setCreateHandler(EventHandler<ActionEvent> e) {
		createButton.setOnAction(e);
	}

	public void setInsertHandler(EventHandler<ActionEvent> e) {
		insertButton.setOnAction(e);
	}

	public void setRemoveHandler(EventHandler<ActionEvent> e) {
		removeButton.setOnAction(e);
	}

	public void setNextStepHandler(EventHandler<ActionEvent> e) {
		nextStepButton.setOnAction(e);
	}

	public void setPrevStepHandler(EventHandler<ActionEvent> e) {
		prevStepButton.setOnAction(e);
	}

	public void setPlayPauseHandler(EventHandler<ActionEvent> e) {
		playPauseButton.setOnAction(e);
	}

	public void setDebugHandler(EventHandler<ActionEvent> e) {
		debugButton.setOnAction(e);
	}

	public void setSearchHandler(EventHandler<ActionEvent> e) {
		searchButton.setOnAction(e);
	}

	public Point2D get2DInputValue() {
		Point2D point;
		try {
			double x = Double.valueOf(elementText.getText());
			double y = Double.valueOf(elementText2.getText());
			point = new Point2D(x, y);
		} catch (NumberFormatException ne) {
			point = new Point2D(0, 0);
		}
		return point;

	}

	public void setViewSwitchHandler(EventHandler<ActionEvent> e) {
		viewSwitchButton.addEventHandler(ActionEvent.ACTION, e);
	}

	public boolean isGridView() {
		return viewSwitchButton.isGridView();
	}

	public BorderPane arrangeScene(AnimationSettings as) {
		return arrangeScene(as, false);
	}

	public BorderPane arrangeScene(AnimationSettings as, boolean addDescriptionArea) {
		BorderPane bp = new BorderPane();
		bp.setTop(getStandardToolbarLayout());

		// DoubleLayerPane dp = as.drawPane;
		if (twoD) {

			viewSwitchButton.addEventHandler(ActionEvent.ACTION, setSwitchablePanels(bp, as));

			addPaneToScene(bp, as.drawPane.bottomLayer());
			addPaneToScene(bp, as.drawPane.topLayer());
			addPaneToScene(bp, as.drawPane.bottomLayer());
		} else {
			sp = new ScrollPane();
			System.out.println(as.pane.getWidth());
			// = new DoubleLayerPane();
			sp.setContent(new Group(as.pane));
			// sp.setContent(as.pane);
			sp.setPannable(true);
			// sp.setHvalue(0.5);

			sp.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");
			bp.setCenter(sp);

			// TESTING: description area
			if (addDescriptionArea) {

				descriptions.setMaxHeight(50);
				descriptions.setPrefHeight(50);
				descriptions.setEditable(false);
				// descriptions.setText("Something \nSomething else \nSomething else \nSomething else \n");

				bp.setBottom(descriptions);
			}
			// as.drawPane = dp;
		}

		return bp;

	}

	private void addPaneToScene(BorderPane parentPane, Pane addingPane) {
		sp = new ScrollPane();
		// System.out.println();
		// = new DoubleLayerPane();

		sp.setContent(new Group(addingPane));
		// sp.setContent(as.pane);
		sp.setPannable(true);
		// sp.setHvalue(0.5);

		// sp.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;");
		parentPane.setCenter(sp);

	}

	private EventHandler<? super ActionEvent> setSwitchablePanels(final BorderPane bp, final AnimationSettings as) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if (!viewSwitchButton.isGridView()) {
					addPaneToScene(bp, as.drawPane.topLayer());

				} else {
					addPaneToScene(bp, as.drawPane.bottomLayer());
				}
			}
		};
	}

	public void centerifyScrollPane() {
		sp.setHvalue(0.5);
	}

	private void setHandlers() {
		removeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				try {
					if (twoD) {
						controller.remove(getInputValue2D());
					} else {
						controller.remove(getInputValue());
					}

				} catch (NumberFormatException nfe) {
					HTMLDialog.show("Prvek nelze odstranit",
							"Špatný formát odstraňovaného prvku <b> (není celým číslem) </b>.", Dialog.Icon.ERROR,
							Dialog.Buttons.OK, 400, 150);
				} catch (OutOfRangeException oe) {
					HTMLDialog.show("Prvek nelze odstranit", oe.getMessage() + "</b>.", Dialog.Icon.ERROR,
							Dialog.Buttons.OK, 400, 150);
				}
			}
		});

		insertButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				try {
					if (twoD) {

						controller.insert(getInputValue2D());
					} else {
						controller.insert(getInputValue());
					}
				} catch (DuplicateException ex) {
					HTMLDialog.show("Prvek nelze vložit", "Tento prvek již ve struktuře <b> existuje </b>.",
							Dialog.Icon.ERROR, Dialog.Buttons.OK, 400, 150);
				} catch (NumberFormatException ne) {
					HTMLDialog.show("Prvek nelze vložit",
							"Špatný formát vkládaného prvku <b> (není celým číslem) </b>.", Dialog.Icon.ERROR,
							Dialog.Buttons.OK, 400, 150);
				}

				catch (OutOfRangeException oe) {
					HTMLDialog.show("Prvek nelze vložit", oe.getMessage() + "</b>.", Dialog.Icon.ERROR,
							Dialog.Buttons.OK, 400, 150);
				}
			}
		});

		createButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {

				controller.showPresets();

			}
		});

		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				try {
					if (twoD) {
						controller.search(getInputValue2D());
					} else {
						controller.search(getInputValue());
					}
				} catch (NumberFormatException nfe) {
					HTMLDialog.show("Prvek nelze nalézt",
							"Špatný formát hledaného prvku <b> (není celým číslem) </b>.", Dialog.Icon.ERROR,
							Dialog.Buttons.OK, 400, 150);
				} catch (OutOfRangeException oe) {
					HTMLDialog.show("Prvek nelze nalézt", oe.getMessage() + "</b>.", Dialog.Icon.ERROR,
							Dialog.Buttons.OK, 400, 150);
				}
			}
		});

		setSteppingCheckBoxHandler(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
				controller.setStepping(t1);
			}
		});

		playPauseButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				controller.playPause();
			}
		});
		nextStepButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				controller.nextStep();
			}
		});

		prevStepButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				controller.prevStep();
			}
		});
		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
				Double d = (Double) t1;
				controller.setRate(d);
			}

		});

		resetButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				controller.reset();

			}
		});

		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				HTMLDialog.show("Nápověda", controller.provideHelp(), Dialog.Icon.INFORMATION, Dialog.Buttons.OK, 600,
						300);

			}
		});
	}

	public TextArea getDescriptions() {
		return descriptions;
	}

}

package application;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class ShowDialog {
	private Dialog<String> dialog = new Dialog<>();
	
	public ShowDialog(String str) {
        dialog.setTitle("Attention");

        DialogPane dialogPane = new DialogPane();
        dialogPane.setHeaderText("Warning");
        dialogPane.setContent(new Label(str));

        dialog.setDialogPane(dialogPane);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.show();
	}

}

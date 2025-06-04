package it.polimi.ingsw.Client.UI.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public interface ImageLoader {

    // i created this method for last year project
    /**
     * method for image loading
     * @param path path
     * @return imageview of this image
     */
    static ImageView imageLoader(String path){
        URL url = ClassLoader.getSystemResource(path);

        Image image = null;

        image = new Image(path);


        return new ImageView(image);

    }
}

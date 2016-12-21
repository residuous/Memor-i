
/**
 * Copyright 2016 SciFY.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scify.memori.screens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.scify.memori.MainOptions;
import org.scify.memori.helper.MemoriConfiguration;
import org.scify.memori.helper.UTF8Control;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainScreen extends Application {
    private double mWidth = Screen.getPrimary().getBounds().getWidth();
    private double mHeight = Screen.getPrimary().getBounds().getHeight();
    private Rectangle graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();


    public MainScreen() {
        System.err.println("SELF:" + this.toString() + " from " + Thread.currentThread().toString());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MemoriConfiguration configuration = new MemoriConfiguration();
        Locale locale = new Locale(configuration.getProjectProperty("APP_LANG"));
        addPath(configuration.getUserDir() + "memori_data");
        //Load fxml file (layout xml) for first screen

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/first_screen.fxml"),
                ResourceBundle.getBundle("languages.strings", locale, new UTF8Control()));
        Parent root = loader.load();
        MainScreenController controller = loader.getController();

        // set as width and height the screen width and height
        MainOptions.mWidth = graphicsEnvironment.getWidth() - 10;
        MainOptions.mHeight = graphicsEnvironment.getHeight() - 10;
        // construct the scene (the content of the stage)
        Scene primaryScene = new Scene(root, mWidth, mHeight);
        primaryStage.setTitle("Memor-i");
        controller.setParameters(primaryStage, primaryScene);
        System.err.println(configuration.getUserDir() + "data_packs");
    }

    public static void addPath(String s) throws Exception {
        URL u = new File(s).toURI().toURL();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[]{u});
    }

}

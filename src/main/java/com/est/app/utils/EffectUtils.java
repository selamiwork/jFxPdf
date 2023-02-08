package com.est.app.utils;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;

@SuppressWarnings("Duplicates")
public class EffectUtils {
    public static void setEffect(ImageView imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );

        setOpacityProperty(imageView);
    }

    public static void setEffect(Text imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );

        setOpacityProperty(imageView);
    }
    public static void setEffect(Button imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );

        setOpacityProperty(imageView);
    }
    public static void setEffect(ToggleButton imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );

        setOpacityProperty(imageView);
    }

    public static void setEffect(Pane imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );

        setOpacityProperty(imageView);
    }

    public static void setEffect(Parent imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );

        setOpacityProperty(imageView);
    }

    public static void setEffect(CheckBox imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );

        setOpacityProperty(imageView);
    }

    public static void setEffectWithoutOpacity(Pane imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );
    }

    public static void setEffectWithoutOpacity(ImageView imageView) {
        Effect shadow = new DropShadow(5, Color.CORNFLOWERBLUE);
        Effect shadowPressed = new DropShadow(5, Color.DARKBLUE);
        Effect blur = new BoxBlur(1, 1, 3);
        Effect shadowHover = new DropShadow(5, Color.CORNSILK);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(30.0f);
        light.setElevation(35.0f);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(0.0f);


        imageView.effectProperty().bind(
                Bindings.when(imageView.pressedProperty())
                        .then(shadow)
                        .otherwise(Bindings.when(imageView.hoverProperty())
                                .then(shadowPressed)
                                .otherwise(shadowHover))
        );
    }

    public static void setOpacityProperty(Node node){
        if(node.isDisabled()){ Platform.runLater(() -> node.setOpacity(0.35)); }
        node.disableProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> node.setOpacity(newValue ? 0.35 : 1.0)));
    }

    public static void setLightEffect(Node node, double azimuth,double elevation, double surfaceScale){
        Light.Distant light = new Light.Distant();
        light.setAzimuth(azimuth);
        light.setElevation(elevation);
        Lighting lighting = new Lighting(light);
        lighting.setSurfaceScale(surfaceScale);

        node.setEffect(lighting);
    }

    public static void setDisabledEffect(Node node){
        node.disableProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> node.setOpacity(newValue ? 0.35 : 1.0)));
    }

    public static void setTransition(Node node, double duration){
        if(node == null){
            return;
        }
        //duration += duration < 5 ? 0.15 : 0;
        TranslateTransition tt = new TranslateTransition(Duration.seconds(duration), node);
        tt.setToX(0);
        tt.setOnFinished(e2 -> node.setClip(null));
        tt.play();
    }

    public static void setTransition(Node node, double duration, ReadOnlyBooleanProperty focusedProperty){
        if(node == null){
            return;
        }
        //duration += duration < 5 ? 0.15 : 0;
        TranslateTransition tt = new TranslateTransition(Duration.seconds(duration), node);
        tt.setToX(0);
        tt.setOnFinished(e2 -> node.setClip(null));
        ChangeListener<Boolean> changeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    tt.play();
                }
            }
        };
        if(focusedProperty != null){
            focusedProperty.addListener(changeListener);
        }else{
            node.focusedProperty().addListener(changeListener);
        }
    }

    public static synchronized void setRounding(Node node){
        setRounding(node, 2000, 360, Animation.INDEFINITE);
    }

    public static synchronized void setRounding(Node node, long durationInMs, double angle){
        setRounding(node, durationInMs, angle, 1);
    }

    public static synchronized void setRounding(Node node, long durationInMs, double angle, int cyleCount){
        RotateTransition rt = new RotateTransition(Duration.millis(durationInMs), node);
        rt.setByAngle(angle);
        rt.setCycleCount(cyleCount);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }

    public static synchronized void setToAngel(Node node, long durationInMs, double angle){
        setToAngel(node, durationInMs, angle, 1);
    }

    public static synchronized void setToAngel(Node node, long durationInMs, double angle, int cyleCount){
        RotateTransition rt = new RotateTransition(Duration.millis(durationInMs), node);
        rt.setToAngle(angle);
        rt.setCycleCount(cyleCount);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }

}

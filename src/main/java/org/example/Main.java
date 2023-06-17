package org.example;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Main {

    private final static Map<String, Integer> inputCodes = Map.of(
            "space", KeyEvent.VK_SPACE,
            "→", KeyEvent.VK_D,
            "↑", KeyEvent.VK_W,
            "←", KeyEvent.VK_A,
            "↓", KeyEvent.VK_S
    );

    public static void main(String[] args) {

        try (InputStream is = Main.class.getResourceAsStream("/inputs.json")) {

            ObjectMapper objectMapper = new ObjectMapper();
            List<LevelInputs> levelInputs = objectMapper.readValue(is, new TypeReference<>() {
            });

            ProcessBuilder processBuilder = new ProcessBuilder("steam", "steam://rungameid/718330");

            Process start = processBuilder.start();

            Thread.sleep(10000);

            Robot robot = new Robot();
            robot.mouseMove(111, 580);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            Thread.sleep(1500);

            robot.keyPress(KeyEvent.VK_SPACE);
            Thread.sleep(1500);
            robot.keyRelease(KeyEvent.VK_SPACE);

            for (LevelInputs levelInput : levelInputs) {

                System.out.println(levelInput.getLevel());

                for (String input : levelInput.getInputs()) {

                    Integer keycode = inputCodes.get(input);
                    robot.keyPress(keycode);
                    Thread.sleep(250);
                    robot.keyRelease(keycode);
                    Thread.sleep(1000);

                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}


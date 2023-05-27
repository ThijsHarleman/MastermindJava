package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Author: Thijs Harleman
 * Created at 14:15 on 07 Apr 2023
 * Purpose: This program runs the game Mastermind.
 */

public class MastermindLauncher {
    // Display settings
    public static JFrame window;
    public static Container container;
    public static JPanel gameTitlePanel, startButtonPanel, gameMainPanel, gameHeaderPanel, optionsButtonPanel,
            gameRulesPanel, optionsHeaderPanel, difficultyTextPanel, difficultyButtonsPanel,
            customDifficultyPanel, aboutPanel, optionsReturnButtonPanel;
    public static JLabel gameTitleLabel, gameHeaderLabel, optionsHeaderLabel, difficultyTextLabel,
            customDifficultyTextLabel1, customDifficultyTextLabel2, customPasscodeLengthLabel,
            customAttemptsNumberLabel;
    public static JButton startButton, optionsButton, difficultyButton1, difficultyButton2, difficultyButton3,
            difficultyButton4, difficultyButton5, customDifficultyMinusButton1, customDifficultyMinusButton2,
            customDifficultyPlusButton1, customDifficultyPlusButton2, optionsReturnButton;
    public static JTextArea gameMainArea, gameRulesArea, aboutArea;

    // Font settings
    public static Font mainTitleFont = new Font("Rockwell Condensed", Font.PLAIN, 72);
    public static Font startButtonFont = new Font("Rockwell", Font.PLAIN, 36);
    public static Font gameTitleFont = new Font("Rockwell Condensed", Font.PLAIN, 42);
    public static Font gameMainFont = new Font("DejaVu Sans Mono", Font.PLAIN, 24);
    public static Font gameRulesFont = new Font("Century Gothic", Font.PLAIN, 14);
    public static Font optionsTitleFont = new Font("Rockwell", Font.PLAIN, 42);
    public static Font difficultyButtonsFont = new Font("Rockwell", Font.PLAIN, 20);
    public static Font optionsReturnButtonFont = new Font("Rockwell", Font.PLAIN, 24);

    // Display colors
    public static Color backgroundColor = Color.decode("#1a1a1a");
    public static Color textColor = Color.decode("#ffffe6");
    public static Color darkTextColor = Color.decode("#404040");

    // Handlers
    public static KeyHandler keyHandler = new KeyHandler();
    public static TitleScreenHandler titleScreenHandler = new TitleScreenHandler();
    public static OptionsButtonHandler optionsButtonHandler = new OptionsButtonHandler();
    public static DifficultyButtonsHandler difficultyButtonsHandler = new DifficultyButtonsHandler();
    public static OptionsReturnButtonHandler optionsReturnButtonHandler = new OptionsReturnButtonHandler();
    public static CustomPasscodeLengthHandler customPasscodeLengthHandler = new CustomPasscodeLengthHandler();
    public static CustomAttemptsNumberHandler customAttemptsNumberHandler = new CustomAttemptsNumberHandler();

    // Button names
    public static final String DIFFICULTY_MINUS_BUTTON_1 = "difficultyMinusButton1";
    public static final String DIFFICULTY_PLUS_BUTTON_1 = "difficultyPlusButton1";
    public static final String DIFFICULTY_MINUS_BUTTON_2 = "difficultyMinusButton2";
    public static final String DIFFICULTY_PLUS_BUTTON_2 = "difficultyPlusButton2";
    public static final String PLUS = "+";
    public static final String MINUS = "-";

    // Screen names
    public static final String TITLE_SCREEN = "titleScreen";
    public static final String MAIN_SCREEN = "mainScreen";
    public static final String OPTIONS_SCREEN = "optionsScreen";

    // Difficulty names
    public static final String DIFFICULTY_1 = "1) Beginner";
    public static final String DIFFICULTY_2 = "2) Geavanceerd";
    public static final String DIFFICULTY_3 = "3) Expert";
    public static final String DIFFICULTY_4 = "4) Geluksvogel";
    public static final String DIFFICULTY_5 = "5) Aangepast:";

    // Game variables
    public static String displayedScreen = TITLE_SCREEN;
    public static boolean gameRunning = false;
    public static String difficulty = DIFFICULTY_3;
    public static int passcodeLength = 5;
    public static int maxNumberOfAttempts = 8;
    public static String passcode;
    public static String[] guessHistory = new String[maxNumberOfAttempts];
    public static String guess = "";
    public static int customPasscodeLength = 5;
    public static int customAttemptsNumber = 8;

    public static void main(String[] args) {
        initializeGameWindow();
        createTitleScreen();
    }

    public static void initializeGameWindow() {
        final int WINDOW_WIDTH = 800;
        final int WINDOW_HEIGHT = 600;

        window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(backgroundColor);
        window.setLayout(null);
        window.setVisible(true);
        window.addKeyListener(keyHandler);
        window.setFocusable(true);

        container = window.getContentPane();
    }

    public static JPanel createPanel(int[] mpPosition, int[] mpSize) {
        JPanel panel = new JPanel();
        panel.setBounds(mpPosition[0], mpPosition[1], mpSize[0], mpSize[1]);
        panel.setBackground(backgroundColor);
        panel.setFocusable(false);

        return panel;
    }

    public static JLabel createLabel(String mpText, Font mpFont) {
        JLabel label = new JLabel(mpText);
        label.setForeground(textColor);
        label.setFont(mpFont);
        label.setFocusable(false);

        return label;
    }

    public static JButton createButton(String mpText, Font mpFont, ActionListener mpActionListener) {
        JButton button = new JButton(mpText);
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(mpFont);
        button.addActionListener(mpActionListener);

        return button;
    }

    public static JTextArea createTextArea(int[] mpPosition, int[] mpSize, Font mpFont) {
        JTextArea textArea = new JTextArea();
        textArea.setBounds(mpPosition[0], mpPosition[1], mpSize[0], mpSize[1]);
        textArea.setBackground(backgroundColor);
        textArea.setForeground(textColor);
        textArea.setFont(mpFont);
        textArea.setEditable(false);
        textArea.setFocusable(false);

        return textArea;
    }

    public static class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            toggleHideTitleScreenElements();
            setupGameScreen();
            startGame();
        }
    }

    public static class OptionsButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            toggleHideMainScreenElements();
            setupOptionsScreen();
        }
    }

    public static class DifficultyButtonsHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            toggleHideOptionsScreenElements();
            toggleHideMainScreenElements();
            setDifficulty(event.getActionCommand());
            startGame();
        }
    }

    public static class OptionsReturnButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            toggleHideOptionsScreenElements();
            toggleHideMainScreenElements();
        }
    }

    public static class CustomPasscodeLengthHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setCustomPasscodeLength(event.getActionCommand());
        }
    }

    public static class CustomAttemptsNumberHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            setCustomAttemptsNumber(event.getActionCommand());
        }
    }

    public static class KeyHandler implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent key) {
            switch (displayedScreen) {
                case (TITLE_SCREEN):
                    handleKeyPressOnTitleScreen(key);
                    break;
                case (MAIN_SCREEN):
                    handleKeyPressOnMainScreen(key);
                    break;
                case (OPTIONS_SCREEN):
                    handleKeyPressOnOptionsScreen(key);
                    break;
                default:
                    System.out.printf("Error KeyHandler: pressed key = %s on screen = %s\n",
                            key.getKeyCode(), displayedScreen);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    public static void handleKeyPressOnTitleScreen(KeyEvent mpKey) {
        if (mpKey.getKeyCode() == KeyEvent.VK_ESCAPE) {
            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
        } else if (mpKey.getKeyCode() == KeyEvent.VK_ENTER) {
            toggleHideTitleScreenElements();
            setupGameScreen();
            startGame();
        }
    }

    public static void handleKeyPressOnMainScreen(KeyEvent mpKey) {
        final String NUMBERS = "1234567890";

        if (mpKey.getKeyCode() == KeyEvent.VK_ESCAPE) {
            toggleHideMainScreenElements();
            toggleHideTitleScreenElements();
        } else if (!gameRunning && mpKey.getKeyCode() == KeyEvent.VK_ENTER) {
            startGame();
        } else if (mpKey.getKeyCode() == KeyEvent.VK_O) {
            toggleHideMainScreenElements();
            setupOptionsScreen();
        } else if (gameRunning && NUMBERS.contains(String.valueOf(mpKey.getKeyChar()))) {
            enterDigit(String.valueOf(mpKey.getKeyChar()));
            updateGameBoard();
        } else if (gameRunning && mpKey.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            deleteDigit();
            updateGameBoard();
        } else if (guess.length() == passcodeLength && mpKey.getKeyCode() == KeyEvent.VK_ENTER) {
            enterGuess();
            determineGameOver();
            updateGameBoard();
        }
    }

    public static void handleKeyPressOnOptionsScreen(KeyEvent mpKey) {
        final String BUTTON_NUMBERS = "12345";

        if (mpKey.getKeyCode() == KeyEvent.VK_ESCAPE) {
            toggleHideOptionsScreenElements();
            toggleHideMainScreenElements();
        } else if (BUTTON_NUMBERS.contains(String.valueOf(mpKey.getKeyChar()))) {
            switch (mpKey.getKeyChar()) {
                case '1':
                    setDifficulty(DIFFICULTY_1);
                    break;
                case '2':
                    setDifficulty(DIFFICULTY_2);
                    break;
                case '3':
                    setDifficulty(DIFFICULTY_3);
                    break;
                case '4':
                    setDifficulty(DIFFICULTY_4);
                    break;
                case '5':
                    setDifficulty(DIFFICULTY_5);
                    break;
                default:
                    System.out.printf("Error handleKeyPressOnOptionsScreen: mpKey.getKeyChar = %c",
                            mpKey.getKeyChar());
            }
            toggleHideOptionsScreenElements();
            toggleHideMainScreenElements();
            startGame();
        }
    }

    public static void createTitleScreen() {
        final int[] TITLE_PANEL_POS = {100, 100}; // {x, y}
        final int[] TITLE_PANEL_SIZE = {600, 150}; // {width, height}
        final int[] START_PANEL_POS = {300, 350}; // {x, y}
        final int[] START_PANEL_SIZE = {200, 100}; // {width, height}

        gameTitlePanel = createPanel(TITLE_PANEL_POS, TITLE_PANEL_SIZE);
        container.add(gameTitlePanel);

        gameTitleLabel = createLabel("Mastermind", mainTitleFont);
        gameTitlePanel.add(gameTitleLabel);

        startButtonPanel = createPanel(START_PANEL_POS, START_PANEL_SIZE);
        container.add(startButtonPanel);

        startButton = createButton("START", startButtonFont, titleScreenHandler);
        startButtonPanel.add(startButton);
    }

    public static void toggleHideTitleScreenElements() {
        switchScreen(TITLE_SCREEN);

        gameTitlePanel.setVisible(!gameTitlePanel.isVisible());
        startButtonPanel.setVisible(!startButtonPanel.isVisible());
    }

    public static void switchScreen(String mpScreen) {
        if (!displayedScreen.equals(mpScreen)) {
            displayedScreen = mpScreen;
        }
    }

    public static void setupGameScreen() {
        displayedScreen = MAIN_SCREEN;
        createGameHeaderSection();
        createGameMainSection();
        createOptionsButtonSection();
        createGameRulesSection();
        displayRulesText();
    }

    public static void createGameHeaderSection() {
        final int[] HEADER_PANEL_POS = {100, 40}; // {x, y}
        final int[] HEADER_PANEL_SIZE = {300, 70}; // {width, height}

        gameHeaderPanel = createPanel(HEADER_PANEL_POS, HEADER_PANEL_SIZE);
        gameHeaderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(gameHeaderPanel);

        gameHeaderLabel = createLabel("Mastermind", gameTitleFont);
        gameHeaderPanel.add(gameHeaderLabel);
    }

    public static void createGameMainSection() {
        final int[] MAIN_PANEL_POS = {100, 120}; // {x, y}
        final int[] MAIN_PANEL_SIZE = {250, 400}; // {width, height}

        gameMainPanel = createPanel(MAIN_PANEL_POS, MAIN_PANEL_SIZE);
        gameMainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(gameMainPanel);

        gameMainArea = createTextArea(MAIN_PANEL_POS, MAIN_PANEL_SIZE, gameMainFont);
        gameMainPanel.add(gameMainArea);
    }

    public static void createOptionsButtonSection() {
        final int[] OPTIONS_PANEL_POS = {600, 40}; // {x, y}
        final int[] OPTIONS_PANEL_SIZE = {100, 70}; // {width, height}

        optionsButtonPanel = createPanel(OPTIONS_PANEL_POS, OPTIONS_PANEL_SIZE);
        container.add(optionsButtonPanel);

        optionsButton = createButton("Opties", gameMainFont, optionsButtonHandler);
        optionsButton.setForeground(darkTextColor);
        optionsButtonPanel.add(optionsButton);
    }

    public static void createGameRulesSection() {
        final int[] RULES_PANEL_POS = {400, 120}; // {x, y}
        final int[] RULES_PANEL_SIZE = {300, 350}; // {width, height}

        gameRulesPanel = createPanel(RULES_PANEL_POS, RULES_PANEL_SIZE);
        container.add(gameRulesPanel);

        gameRulesArea = createTextArea(RULES_PANEL_POS, RULES_PANEL_SIZE, gameRulesFont);
        gameRulesPanel.add(gameRulesArea);
    }

    public static void displayRulesText() {
        gameRulesArea.setLineWrap(true);
        gameRulesArea.setWrapStyleWord(true);
        gameRulesArea.setText("Kraak de code!\n\nVoer " + passcodeLength + " cijfers in en druk op [Enter] " +
                "om een code te toetsen.\n\nJe code verschijnt bovenin het scherm, met ernaast twee getallen. " +
                "Het linker getal geeft weer weer hoeveel van de cijfers in de getoetste code op de juiste " +
                "plaats zijn ingevoerd. Het rechter getal geeft weer hoeveel van de cijfers in de getoetste " +
                "code juist, maar op de verkeerde plaats zijn ingevoerd.\n\nJe hebt " + maxNumberOfAttempts +
                " pogingen om de code te kraken.\n\nVeel succes!");
    }

    public static void toggleHideMainScreenElements() {
        switchScreen(MAIN_SCREEN);

        gameHeaderPanel.setVisible(!gameHeaderPanel.isVisible());
        gameMainPanel.setVisible(!gameMainPanel.isVisible());
        optionsButtonPanel.setVisible(!optionsButtonPanel.isVisible());
        gameRulesPanel.setVisible(!gameRulesPanel.isVisible());
    }

    public static void setupOptionsScreen() {
        displayedScreen = OPTIONS_SCREEN;
        createOptionsHeaderSection();
        createDifficultyTextSection();
        createDifficultyButtonsSection();
        createCustomDifficultySettingsSection();
        createAboutSection();
        createOptionsReturnButtonSection();
    }

    public static void createOptionsHeaderSection() {
        final int[] OPTIONS_HEADER_PANEL_POS = {100, 40}; // {x, y}
        final int[] OPTIONS_HEADER_PANEL_SIZE = {300, 70}; // {width, height}

        optionsHeaderPanel = createPanel(OPTIONS_HEADER_PANEL_POS, OPTIONS_HEADER_PANEL_SIZE);
        optionsHeaderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(optionsHeaderPanel);

        optionsHeaderLabel = createLabel("Opties", optionsTitleFont);
        optionsHeaderPanel.add(optionsHeaderLabel);
    }

    public static void createDifficultyTextSection() {
        final int[] DIFFICULTY_TEXT_PANEL_POS = {100, 150}; // {x, y}
        final int[] DIFFICULTY_TEXT_PANEL_SIZE = {300, 40}; // {width, height}

        difficultyTextPanel = createPanel(DIFFICULTY_TEXT_PANEL_POS, DIFFICULTY_TEXT_PANEL_SIZE);
        difficultyTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(difficultyTextPanel);

        difficultyTextLabel = createLabel("Kies je moeilijkheidsgraad:", gameRulesFont);
        difficultyTextPanel.add(difficultyTextLabel);
    }

    public static void createDifficultyButtonsSection() {
        final int[] DIFFICULTY_BUTTONS_PANEL_POS = {120, 200}; // {x, y}
        final int[] DIFFICULTY_BUTTONS_PANEL_SIZE = {200, 220}; // {width, height}

        difficultyButtonsPanel = createPanel(DIFFICULTY_BUTTONS_PANEL_POS, DIFFICULTY_BUTTONS_PANEL_SIZE);
        difficultyButtonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(difficultyButtonsPanel);

        difficultyButton1 = createButton(DIFFICULTY_1, difficultyButtonsFont, difficultyButtonsHandler);
        difficultyButton2 = createButton(DIFFICULTY_2, difficultyButtonsFont, difficultyButtonsHandler);
        difficultyButton3 = createButton(DIFFICULTY_3, difficultyButtonsFont, difficultyButtonsHandler);
        difficultyButton4 = createButton(DIFFICULTY_4, difficultyButtonsFont, difficultyButtonsHandler);
        difficultyButton5 = createButton(DIFFICULTY_5, difficultyButtonsFont, difficultyButtonsHandler);
        difficultyButtonsPanel.add(difficultyButton1);
        difficultyButtonsPanel.add(difficultyButton2);
        difficultyButtonsPanel.add(difficultyButton3);
        difficultyButtonsPanel.add(difficultyButton4);
        difficultyButtonsPanel.add(difficultyButton5);
    }

    public static void setDifficulty(String mpClickedDifficulty) {
        final int EASY_PASSCODE_LENGTH = 4;
        final int MEDIUM_PASSCODE_LENGTH = 5;
        final int HARD_PASSCODE_LENGTH = 6;
        final int EASY_ATTEMPT_AMOUNT = 10;
        final int MEDIUM_ATTEMPT_AMOUNT = 8;
        final int HARD_ATTEMPT_AMOUNT = 6;

        difficulty = mpClickedDifficulty;

        switch (difficulty) {
            case DIFFICULTY_1:
                passcodeLength = EASY_PASSCODE_LENGTH;
                maxNumberOfAttempts = EASY_ATTEMPT_AMOUNT;
                break;
            case DIFFICULTY_2:
                passcodeLength = EASY_PASSCODE_LENGTH;
                maxNumberOfAttempts = MEDIUM_ATTEMPT_AMOUNT;
                break;
            case DIFFICULTY_3:
                passcodeLength = MEDIUM_PASSCODE_LENGTH;
                maxNumberOfAttempts = MEDIUM_ATTEMPT_AMOUNT;
                break;
            case DIFFICULTY_4:
                passcodeLength = HARD_PASSCODE_LENGTH;
                maxNumberOfAttempts = HARD_ATTEMPT_AMOUNT;
                break;
            case DIFFICULTY_5:
                passcodeLength = customPasscodeLength;
                maxNumberOfAttempts = customAttemptsNumber;
                break;
            default:
                System.out.printf("Error setDifficulty: difficulty = %s\n", difficulty);
        }

        displayRulesText();
    }

    public static void createCustomDifficultySettingsSection() {
        final int[] CUSTOM_BUTTONS_PANEL_POS = {140, 410}; // {x, y}
        final int[] CUSTOM_BUTTONS_PANEL_SIZE = {240, 80}; // {width, height}

        customDifficultyPanel = createPanel(CUSTOM_BUTTONS_PANEL_POS, CUSTOM_BUTTONS_PANEL_SIZE);
        customDifficultyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        container.add(customDifficultyPanel);

        customDifficultyTextLabel1 = createLabel("Code lengte:", gameRulesFont);
        customDifficultyTextLabel2 = createLabel("Pogingen:", gameRulesFont);

        customDifficultyMinusButton1 = createPlusMinusButton(
                MINUS, DIFFICULTY_MINUS_BUTTON_1, customPasscodeLengthHandler);
        customDifficultyPlusButton1 = createPlusMinusButton(
                PLUS, DIFFICULTY_PLUS_BUTTON_1, customPasscodeLengthHandler);
        customDifficultyMinusButton2 = createPlusMinusButton(
                MINUS, DIFFICULTY_MINUS_BUTTON_2, customAttemptsNumberHandler);
        customDifficultyPlusButton2 = createPlusMinusButton(
                PLUS, DIFFICULTY_PLUS_BUTTON_2, customAttemptsNumberHandler);

        customPasscodeLengthLabel = createLabel(String.valueOf(customPasscodeLength), difficultyButtonsFont);
        customAttemptsNumberLabel = createLabel(String.valueOf(customAttemptsNumber), difficultyButtonsFont);

        customDifficultyPanel.add(customDifficultyTextLabel1);
        customDifficultyPanel.add(customDifficultyMinusButton1);
        customDifficultyPanel.add(customPasscodeLengthLabel);
        customDifficultyPanel.add(customDifficultyPlusButton1);

        customDifficultyPanel.add(customDifficultyTextLabel2);
        customDifficultyPanel.add(customDifficultyMinusButton2);
        customDifficultyPanel.add(customAttemptsNumberLabel);
        customDifficultyPanel.add(customDifficultyPlusButton2);
    }

    public static JButton createPlusMinusButton(String mpButtonText, String mpButtonName, ActionListener mpHandler) {
        final int[] CUSTOM_BUTTONS_SIZE = {10, 10}; // {width, height}

        JButton plusMinusButton = createButton(mpButtonText, difficultyButtonsFont, mpHandler);
        plusMinusButton.setMaximumSize(new Dimension(CUSTOM_BUTTONS_SIZE[0], CUSTOM_BUTTONS_SIZE[1]));
        plusMinusButton.setName(mpButtonName);

        return plusMinusButton;
    }

    public static void setCustomPasscodeLength(String mpClickedButton) {
        final int MAXIMUM_CODE_LENGTH = 6;
        final int MINIMUM_CODE_LENGTH = 1;

        switch (mpClickedButton) {
            case PLUS:
                if (customPasscodeLength < MAXIMUM_CODE_LENGTH) {
                    customPasscodeLength++;
                }
                break;
            case MINUS:
                if (customPasscodeLength > MINIMUM_CODE_LENGTH) {
                    customPasscodeLength--;
                }
                break;
            default:
                System.out.printf("Error setCustomPasscodeLength: mpClickedButton = %s", mpClickedButton);
        }
        customPasscodeLengthLabel.setText(String.valueOf(customPasscodeLength));
    }

    public static void setCustomAttemptsNumber(String mpClickedButton) {
        final int MAXIMUM_ATTEMPTS_NUMBER = 10;
        final int MINIMUM_ATTEMPTS_NUMBER = 1;

        switch (mpClickedButton) {
            case PLUS:
                if (customAttemptsNumber < MAXIMUM_ATTEMPTS_NUMBER) {
                    customAttemptsNumber++;
                }
                break;
            case MINUS:
                if (customAttemptsNumber > MINIMUM_ATTEMPTS_NUMBER) {
                    customAttemptsNumber--;
                }
                break;
            default:
                System.out.printf("Error setCustomAttemptsNumber: mpClickedButton = %s", mpClickedButton);
        }
        customAttemptsNumberLabel.setText(String.valueOf(customAttemptsNumber));
    }

    public static void createAboutSection() {
        final int[] ABOUT_PANEL_POS = {600, 440}; // {x, y}
        final int[] ABOUT_PANEL_SIZE = {180, 100}; // {width, height}

        aboutPanel = createPanel(ABOUT_PANEL_POS, ABOUT_PANEL_SIZE);
        aboutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(aboutPanel);

        aboutArea = createTextArea(ABOUT_PANEL_POS, ABOUT_PANEL_SIZE, gameRulesFont);
        aboutArea.setText("Thijs Harleman\nApril 2023");
        aboutArea.setForeground(darkTextColor);

        aboutPanel.add(aboutArea);
    }

    public static void createOptionsReturnButtonSection() {
        final int[] OPTIONS_RETURN_PANEL_POS = {600, 40}; // {x, y}
        final int[] OPTIONS_RETURN_PANEL_SIZE = {100, 70}; // {width, height}

        optionsReturnButtonPanel = createPanel(OPTIONS_RETURN_PANEL_POS, OPTIONS_RETURN_PANEL_SIZE);
        container.add(optionsReturnButtonPanel);

        optionsReturnButton = createButton("Terug", optionsReturnButtonFont, optionsReturnButtonHandler);
        optionsReturnButton.setForeground(darkTextColor);
        optionsReturnButtonPanel.add(optionsReturnButton);
    }

    public static void toggleHideOptionsScreenElements() {
        switchScreen(OPTIONS_SCREEN);

        optionsHeaderPanel.setVisible(!optionsHeaderLabel.isVisible());
        difficultyTextPanel.setVisible(!difficultyTextPanel.isVisible());
        difficultyButtonsPanel.setVisible(!difficultyButtonsPanel.isVisible());
        customDifficultyPanel.setVisible(!customDifficultyPanel.isVisible());
        aboutPanel.setVisible(!aboutPanel.isVisible());
        optionsReturnButtonPanel.setVisible((!optionsReturnButtonPanel.isVisible()));
    }

    public static void startGame() {
        gameRunning = true;
        passcode = generatePasscode();
        guessHistory = new String[maxNumberOfAttempts];
        displayRulesText();
        updateGameBoard();
    }

    public static String generatePasscode() {
        StringBuilder passcode = new StringBuilder();

        for (int i = 0; i < passcodeLength; i++) {
            passcode.append(generateNumber());
        }

        return passcode.toString();
    }

    public static int generateNumber() {
        return (int) (Math.random() * 10);
    }

    public static void updateGameBoard() {
        String[] gameBoard = new String[maxNumberOfAttempts + 1];
        String emptyRow = createEmptyRow();

        for (int index = 0; index < maxNumberOfAttempts; index++) {
            if (guessHistory[index] == null) {
                gameBoard[index] = emptyRow;
            } else {
                gameBoard[index] = guessHistory[index];
            }
        }

        if (gameRunning) {
            gameBoard[maxNumberOfAttempts] = "\n\n" + updateGuess();
        } else {
            gameBoard[maxNumberOfAttempts] = "";
        }

        gameMainArea.setText(buildGameBoardString(gameBoard));
    }

    public static String createEmptyRow() {
        String emptyRow = "_ ".repeat(passcodeLength);
        emptyRow += "- 0:0\n";

        return emptyRow;
    }

    public static String updateGuess() {
        StringBuilder guessString = new StringBuilder();
        for (int index = 0; index < passcodeLength; index++) {
            if (index + 1 > guess.length()) {
                guessString.append("_");
            } else {
                guessString.append(guess.charAt(index));
            }
            guessString.append(" ");
        }

        return guessString.toString();
    }

    public static String buildGameBoardString(String[] mpGameBoard){
        StringBuilder gameBoardString = new StringBuilder();

        for (String row : mpGameBoard) {
            gameBoardString.append(row);
        }

        return gameBoardString.toString();
    }

    public static void enterDigit(String mpKey) {
        if (guess.length() < passcodeLength) {
            guess += mpKey;
        }
    }

    public static void deleteDigit() {
        if (guess.length() > 0) {
            guess = guess.substring(0, guess.length() - 1);
        }
    }

    public static void enterGuess() {
        guessHistory[determineGuessHistoryLength()] = formatGuess((guess), determineResult(guess));
    }

    public static int determineGuessHistoryLength() {
        int length = 0;

        for (String row : guessHistory) {
            if (row != null) {
                length++;
            }
        }

        return length;
    }

    public static void determineGameOver() {
        final String gameWonText = "Je hebt de code gekraakt!\n\nDe correcte code was " + passcode +
                ". Je hebt de code in " + determineGuessHistoryLength() + " pogingen gekraakt.\n\n" +
                "Druk op [Enter] om een nieuw spel te starten!";
        final String gameLostText = "Game over\n\nHet is je niet gelukt om de code te kraken...\n\n" +
                "De correcte code was " + passcode + ".\n\nDruk op [Enter] om een nieuw spel te starten!";

        if (guess.equals(passcode)) {
            gameRulesArea.setText(gameWonText);
            gameRunning = false;
        } else if (guessHistory[maxNumberOfAttempts - 1] != null) {
            gameRulesArea.setText(gameLostText);
            gameRunning = false;
        }

        guess = "";
    }

    public static String formatGuess(String mpGuess, int[] mpResult) {
        StringBuilder formattedGuess = new StringBuilder();

        for (int index = 0; index < mpGuess.length(); index++) {
            formattedGuess.append(mpGuess.charAt(index));
            formattedGuess.append(" ");
        }

        formattedGuess.append("- ");
        formattedGuess.append(mpResult[0]);
        formattedGuess.append(":");
        formattedGuess.append(mpResult[1]);
        formattedGuess.append("\n");

        return formattedGuess.toString();
    }

    public static int[] determineResult(String mpGuess) {
        int[] result = {0, 0};

        // a) determine amount of correct number in correct position
        for (int index = 0; index < passcodeLength; index++) {
            if (mpGuess.charAt(index) == passcode.charAt(index)) {
                result[0]++;
            }
        }

        // b) determine amount of correct number in correct or incorrect position
        for (int number = 0; number < 10; number++) {
            final String numbers = "0123456789";

            int numberOccurrencesInGuess = 0;
            int numberOccurrencesInPasscode = 0;

            for (int index = 0; index < passcodeLength; index++) {
                if (mpGuess.charAt(index) == numbers.charAt(number)) {
                    numberOccurrencesInGuess++;
                }
            }

            for (int index = 0; index < passcodeLength; index++) {
                if (passcode.charAt(index) == numbers.charAt(number)) {
                    numberOccurrencesInPasscode++;
                }
            }

            result[1] += Math.min(numberOccurrencesInGuess, numberOccurrencesInPasscode);
        }

        // subtract a from b to get amount of correct number in incorrect position
        result[1] = result[1] - result[0];

        return result;
    }
}

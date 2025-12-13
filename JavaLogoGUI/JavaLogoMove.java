import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Java Logo Animation Project with Background
 * - Background image
 * - Bouncing Java logo
 * - Start / Stop buttons
 * - "Hello Java World" text in white
 */

public class JavaLogoMove extends JPanel implements Runnable {

    // Images
    BufferedImage logoImage;
    BufferedImage backgroundImage;

    // Logo position
    int x = 60;
    int y = 140;

    // Movement step
    int stepX = 4;
    int stepY = 4;

    // Logo size
    int logoWidth = 120;
    int logoHeight = 120;

    // Text message
    String message = "Hello Java World";

    // Animation flag
    boolean running = true;

    // Buttons
    JButton startBtn, stopBtn;

    public JavaLogoMove() {

        setLayout(null); // manual layout for buttons

        // ==== Load images from "images" folder ====
        try {
            logoImage = ImageIO.read(new File("images/java.png"));
            backgroundImage = ImageIO.read(new File("images/background.png"));
            System.out.println("Images loaded successfully.");
        } catch (Exception ex) {
            System.out.println("Error loading images: " + ex.getMessage());
        }

        // ---- Start button ----
        startBtn = new JButton("Start");
        startBtn.setBounds(20, 20, 80, 30);
        add(startBtn);

        // ---- Stop button ----
        stopBtn = new JButton("Stop");
        stopBtn.setBounds(110, 20, 80, 30);
        add(stopBtn);

        // Actions
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = true;
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = false;
            }
        });

        // Start animation thread
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // ===== Draw background image (stretched to window) =====
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            // Fallback if image not found
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        // ===== Draw "Hello Java World" text in white =====
        g2.setColor(Color.WHITE);       
        g2.setFont(new Font("Arial", Font.BOLD, 24));

        int textWidth = g2.getFontMetrics().stringWidth(message);
        int textX = (getWidth() - textWidth) / 2;
        int textY = 80;    

        g2.drawString(message, textX, textY);

        // ===== Draw Java logo =====
        if (logoImage != null) {

            logoWidth = getWidth() / 5;
            logoHeight = getHeight() / 5;

            if (logoWidth < 40) logoWidth = 40;
            if (logoHeight < 40) logoHeight = 40;

            g2.drawImage(logoImage, x, y, logoWidth, logoHeight, null);
        }
    }

    @Override
    public void run() {

        while (true) {

            if (running) {

                x += stepX;
                y += stepY;

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                if (panelWidth > 0 && panelHeight > 0) {

                    logoWidth = panelWidth / 5;
                    logoHeight = panelHeight / 5;

                    if (logoWidth < 40) logoWidth = 40;
                    if (logoHeight < 40) logoHeight = 40;

                    // Bounce horizontally
                    if (x < 0) {
                        x = 0;
                        stepX = -stepX;
                    } else if (x + logoWidth > panelWidth) {
                        x = panelWidth - logoWidth;
                        stepX = -stepX;
                    }

                    // Bounce vertically (avoid the text area at top)
                    if (y < 100) {
                        y = 100;
                        stepY = -stepY;
                    } else if (y + logoHeight > panelHeight) {
                        y = panelHeight - logoHeight;
                        stepY = -stepY;
                    }
                }

                repaint();
            }

            try {
                Thread.sleep(20);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Java Animation Project - Background Version");
        JavaLogoMove panel = new JavaLogoMove();

        frame.add(panel);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.FontUIResource;

public class UI {

	    private static final int FRAME_WIDTH = 1500;
	    private static final int FRAME_HEIGHT = 728;
	    private final kNN knN = new kNN();

	    private DrawArea drawArea;
	    private JFrame mainFrame;
	    private JPanel mainPanel;
	    private JPanel drawAndDigitPredictionPanel;
	    private SpinnerNumberModel modelTrainSize;
	    private JSpinner trainField;
	    private int TRAIN_SIZE = 30000;
	    private final Font sansSerifBold = new Font("SansSerif", Font.BOLD, 18);
	    private int TEST_SIZE = 10000;
	    private SpinnerNumberModel modelTestSize;
	    private JSpinner testField;
	    private JPanel resultPanel;

	    public UI() throws Exception {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));
	        UIManager.put("ProgressBar.font", new FontUIResource(new Font("Dialog", Font.BOLD, 18)));
	    }

	    public void initUI() {
	        // create main frame
	        mainFrame = createMainFrame();

	        mainPanel = new JPanel();
	        mainPanel.setLayout(new BorderLayout());

	        addTopPanel();

	        drawAndDigitPredictionPanel = new JPanel(new GridLayout());
	        addActionPanel();
	        addDrawAreaAndPredictionArea();
	        mainPanel.add(drawAndDigitPredictionPanel, BorderLayout.CENTER);

	        mainFrame.add(mainPanel, BorderLayout.CENTER);
	        mainFrame.setVisible(true);

	    }

	    private void addActionPanel() {
	        JButton recognize = new JButton("Recognize Digit With kNN");
	        recognize.addActionListener(e -> {
	            Image drawImage = drawArea.getImage();
	            BufferedImage sbi = toBufferedImage(drawImage);
	            Image scaled = scale(sbi);
	            BufferedImage scaledBuffered = toBufferedImage(scaled);
	            double[] scaledPixels = ToOneDimensionalVector(scaledBuffered);
	            BufferedImage labeledImage = new LabeledImage(0, scaledPixels);
	            BufferedImage predict = kNN.predict(labeledImage);
	            JLabel predictNumber = new JLabel("" + (int) predict.getLabel());
	            predictNumber.setForeground(Color.RED);
	            predictNumber.setFont(new Font("SansSerif", Font.BOLD, 128));
	            resultPanel.removeAll();
	            resultPanel.add(predictNumber);
	            resultPanel.updateUI();

	        });

	        JButton clear = new JButton("Clear");
	        clear.addActionListener(e -> {
	            drawArea.setImage(null);
	            drawArea.repaint();
	            drawAndDigitPredictionPanel.updateUI();
	        });
	        JPanel actionPanel = new JPanel(new GridLayout(8, 1));
	        actionPanel.add(recognize);
	        actionPanel.add(clear);
	        drawAndDigitPredictionPanel.add(actionPanel);
	    }

	    private Image scale(BufferedImage sbi) {
			// TODO Auto-generated method stub
			return null;
		}

		private void addDrawAreaAndPredictionArea() {

	        drawArea = new DrawArea();

	        drawAndDigitPredictionPanel.add(drawArea);
	        resultPanel = new JPanel();
	        resultPanel.setLayout(new GridBagLayout());
	        drawAndDigitPredictionPanel.add(resultPanel);
	    }

	    private void addTopPanel() {
	        JPanel topPanel = new JPanel(new FlowLayout());
	        JButton trainkNN = new JButton("Train kNN");
	        trainkNN.addActionListener(e -> {

	            int i = JOptionPane.showConfirmDialog(mainFrame, "Are you sure this may take some time to train?");

	            if (i == JOptionPane.OK_OPTION) {
	                ProgressBar progressBar = new ProgressBar(mainFrame);
	                SwingUtilities.invokeLater(() -> progressBar.showProgressBar("Training may take one or two minutes..."));
	            }
	        });

	        topPanel.add(trainkNN);
	        JLabel tL = new JLabel("Training Data");
	        tL.setFont(sansSerifBold);
	        topPanel.add(tL);
	        modelTrainSize = new SpinnerNumberModel(TRAIN_SIZE, 10000, 60000, 1000);
	        trainField = new JSpinner(modelTrainSize);
	        trainField.setFont(sansSerifBold);
	        topPanel.add(trainField);

	        JLabel ttL = new JLabel("Test Data");
	        ttL.setFont(sansSerifBold);
	        topPanel.add(ttL);
	        modelTestSize = new SpinnerNumberModel(TEST_SIZE, 1000, 10000, 500);
	        testField = new JSpinner(modelTestSize);
	        testField.setFont(sansSerifBold);
	        topPanel.add(testField);

	        mainPanel.add(topPanel, BorderLayout.NORTH);
	    }

	    private static BufferedImage toBufferedImage(Image img) {
	        // Create a buffered image with transparency
	        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	        // Draw the image on to the buffered image
	        Graphics2D bGr = bimage.createGraphics();
	        bGr.drawImage(img, 0, 0, null);
	        bGr.dispose();

	        // Return the buffered image
	        return bimage;
	    }


	    private static double[] ToOneDimensionalVector(BufferedImage img) {

	        double[] imageGray = new double[28 * 28];
	        int w = img.getWidth();
	        int h = img.getHeight();
	        int index = 0;
	        for (int i = 0; i < w; i++) {
	            for (int j = 0; j < h; j++) {
	                Color color = new Color(img.getRGB(j, i), true);
	                int red = (color.getRed());
	                int green = (color.getGreen());
	                int blue = (color.getBlue());
	                double v = 255 - (red + green + blue) / 3d;
	                imageGray[index] = v;
	                index++;
	            }
	        }
	        return imageGray;
	    }


	    private JFrame createMainFrame() {
	        JFrame mainFrame = new JFrame();
	        mainFrame.setTitle("Handwritten Digit Recognizer");
	        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	        mainFrame.setLocationRelativeTo(null);
	        mainFrame.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	                System.exit(0);
	            }
	        });
	        ImageIcon imageIcon = new ImageIcon("icon.png");
	        mainFrame.setIconImage(imageIcon.getImage());

	        return mainFrame;
	    }


	}

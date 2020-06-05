package com.set;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import java.awt.geom.CubicCurve2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.GeneralPath;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Line2D;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import java.util.ArrayList;

public class ShapeLibrary extends JFrame {

    Graphics2D g2d;
    private static final long serialVersionUID = 1L;
    private int width = 1100;
    private int height = 600;
    private int shapeWidth = 15;
    private int shapeHeight = 20;//these are half of the full widths and heights for shapes
    private int cardWidth = 170;
    private int cardHeight = 100;//these are half of the full widths and heights for cards
    private BufferedImage graphicsContext;
    private JPanel contentPanel = new JPanel();
    private JLabel contextRender;
    private Stroke solidStroke = new BasicStroke(3.0f);
    private RenderingHints antialiasing;
    private boolean fill;
    private Color color = Color.RED;
    public JLayeredPane layeredPane;
    public static ArrayList<JButton> buttons = new ArrayList<JButton>();
    public JLabel scoreLabel;
    public JButton threeButton;

    public static void main(String[] args) {
        Runnable swingStarter = new Runnable()
        {
            public void run(){
                new ShapeLibrary();
            }
        };

        SwingUtilities.invokeLater(swingStarter);
    }

    public ShapeLibrary() {
        antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphicsContext = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        contextRender = new JLabel(new ImageIcon(graphicsContext));

        contentPanel.add(contextRender);
        contentPanel.setSize(width, height);

        this.g2d = graphicsContext.createGraphics();
        this.g2d.setRenderingHints(antialiasing);

        //clear the background
        this.g2d.setColor(Color.WHITE);
        this.g2d.fillRect(0, 0, graphicsContext.getWidth(), graphicsContext.getHeight());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        this.layeredPane = new JLayeredPane();
        this.layeredPane.setPreferredSize(new Dimension(width, height));
        this.layeredPane.add(contentPanel, 2);

        threeButton = new JButton("Add Three");
        threeButton.setPreferredSize(new Dimension(30,30)); //use layout manager to place it where you want
        threeButton.setBounds(10, 10, 100, 30);
        threeButton.setContentAreaFilled(true);//make btn transparent
        threeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));//change button cursor
        threeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Card.selected.add(-1);
            }
        });

        scoreLabel = new JLabel("Score: 0");
        this.scoreLabel.setBounds(200, 10, 100, 30);
        this.scoreLabel.setBorder(new EmptyBorder(0,10,0,0));
        this.scoreLabel.setOpaque(true);
        this.scoreLabel.setVisible(true);
        this.layeredPane.add(scoreLabel, 1);

        makeButtonsAndFinish();

        this.setContentPane(this.layeredPane);
        //take advantage of auto-sizing the window based on the size of its contents
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void makeButtonsAndFinish() {
        this.layeredPane.add(threeButton, 1);
        this.layeredPane.add(scoreLabel, 1);
        setVisible(true);
    }

    public void changeScore(int score) {
        this.scoreLabel.setText("Score: " + score);
        this.layeredPane.add(scoreLabel, 1);
        repaint();
    }

    public void clearBoard() {
        this.g2d.setColor(Color.WHITE);
        this.g2d.fillRect(0, 0, graphicsContext.getWidth(), graphicsContext.getHeight());
    }

    public void finishWindow() {
        makeButtonsAndFinish(); 
    }

    public void drawCard(int x, int y, final int tablePosition, boolean highlight, Card card) {
        g2d.setColor(Color.WHITE);
        this.g2d.fill(new RoundRectangle2D.Float(x - cardWidth/2 - 20, y-cardHeight/2 -20, cardWidth +40, cardHeight+40, 20,20));//white

        if(highlight) {
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(3.0f));
        } else {
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3.0f));
        }

        this.g2d.draw(new RoundRectangle2D.Float(x - cardWidth/2, y-cardHeight/2, cardWidth, cardHeight, 20,20));//real
        
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(30,30)); //use layout manager to place it where you want
        btn.setBounds(x - cardWidth/2, y-cardHeight/2+5, cardWidth, cardHeight);
        btn.setActionCommand(Integer.toString(tablePosition));
        btn.setContentAreaFilled(false);//make btn transparent
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));//change button cursor
        btn.setBorder(null);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!Card.selected.contains(tablePosition)) {
                    Card.selected.add(tablePosition);
                    Deck.populateCard();
                } else {
                    Card.selected.remove(Card.selected.indexOf(tablePosition));
                    Deck.populateCard();
                }
            }
        });
        layeredPane.add(btn, 1);
    }

    public void paintOval(int x, int y, int style, int color) {
        if(color == 1) {
            this.color = Color.RED;
        } else if(color == 2) {
            this.color = new Color(27, 180, 89);
        } else {
            this.color = new Color(78, 45, 157);
        }
        g2d.setClip(null);
        g2d.setStroke(solidStroke);
        g2d.setColor(this.color);
        if(style == 1) {
            this.fill = true;
            g2d.setStroke(new BasicStroke(1.0f));
        } else if(style==2) {
            this.fill = false;
            g2d.setStroke(new BasicStroke(3.0f));
        } else {
            this.fill = true;
            g2d.setStroke(new BasicStroke(3.0f));
        }
        GeneralPath closedCurve = new GeneralPath();
        closedCurve.append(new CubicCurve2D.Float(x - shapeWidth, y-shapeHeight, x - shapeWidth + 4, y-(shapeHeight*2), x + shapeWidth - 4, y-(shapeHeight*2), x + shapeWidth, y-shapeHeight), this.fill);
        closedCurve.append(new CubicCurve2D.Float(x - shapeWidth, y+shapeHeight, x - shapeWidth + 4, y+(shapeHeight*2), x + shapeWidth - 4, y+(shapeHeight*2), x + shapeWidth, y+shapeHeight), this.fill);
        closedCurve.append(new Line2D.Double(x+shapeWidth,y+shapeHeight,x+shapeWidth,y-shapeHeight), this.fill);
        closedCurve.append(new Line2D.Double(x-shapeWidth,y+shapeHeight,x-shapeWidth,y-shapeHeight), this.fill);

        g2d.draw(closedCurve);
        if(this.fill) {
            g2d.fill(closedCurve);
        }
        if(style == 3) {
            g2d.setStroke(new BasicStroke(0.5f));
            g2d.draw(closedCurve);

            g2d.fill(closedCurve);
            BufferedImage bi = new BufferedImage(5, 3, BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.setColor(Color.WHITE);
            big.fillRect(0, 0, 5, 3);
            big.setColor(this.color);
            big.drawLine(0, 0, 5, 0);
            Rectangle r = new Rectangle(0, 0, 5, 3);
            TexturePaint tp = new TexturePaint(bi, r);

            g2d.setPaint(tp);
            g2d.fill(closedCurve);
        }
    }

    public void paintDiamond(int x, int y, int style, int color) {
        if(color == 1) {
            this.color = Color.RED;
        } else if(color == 2) {
            this.color = new Color(27, 180, 89);
        } else {
            this.color = new Color(78, 45, 157);
        }
        g2d.setClip(null);
        g2d.setStroke(solidStroke);
        g2d.setColor(this.color);
        g2d.setStroke(new BasicStroke(3.0f));
        if(style != 2) {
            this.fill = true;
        } else {
            this.fill = false;
        }

        GeneralPath closedCurve = new GeneralPath();
        closedCurve.moveTo(x-shapeWidth, y);
        closedCurve.lineTo(x, y-(shapeHeight*2)+5);
        closedCurve.lineTo(x+shapeWidth, y);
        closedCurve.lineTo(x, y+(shapeHeight*2)-5);
        closedCurve.lineTo(x-shapeWidth, y);
        closedCurve.closePath();
        g2d.draw(closedCurve);
        if(this.fill) {
            g2d.fill(closedCurve);
        }
        if(style == 3) {
            g2d.setStroke(new BasicStroke(0.5f));
            g2d.draw(closedCurve);

            g2d.fill(closedCurve);
            BufferedImage bi = new BufferedImage(5, 3, BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.setColor(Color.WHITE);
            big.fillRect(0, 0, 5, 3);
            big.setColor(this.color);
            big.drawLine(0, 0, 5, 0);
            Rectangle r = new Rectangle(0, 0, 5, 3);
            TexturePaint tp = new TexturePaint(bi, r);

            g2d.setPaint(tp);
            g2d.fill(closedCurve);
        }
    }

    public void paintSquiggle(int x, int y, int style, int color) {
        //style: 1 is fill, 2 is outline, 3 is lines
        //color: 1 is red, 2 is green, 3 is purple
        g2d.setClip(null);
        g2d.setStroke(solidStroke);
        if(color == 1) {
            this.color = Color.RED;
        } else if(color == 2) {
            this.color = new Color(27, 180, 89);
        } else {
            this.color = new Color(78, 45, 157);
        }
        g2d.setColor(this.color);
        if(style != 2) {
            this.fill = true;
            g2d.setStroke(new BasicStroke(5.0f));
        } else {
            this.fill = false;
            g2d.setStroke(new BasicStroke(3.0f));
        }

        GeneralPath closedCurve = new GeneralPath();
        //top left right bottom
        CubicCurve2D q1 = new CubicCurve2D.Float(x - shapeWidth+5, y-shapeHeight+2, x - shapeWidth-10, y-(shapeHeight*2), x + shapeWidth, y-(shapeHeight*2)+2, x + shapeWidth-2, y-shapeHeight+8);
        CubicCurve2D q2 = new CubicCurve2D.Float(x - shapeWidth+5, y-shapeHeight+2, x-3, y-5, x - shapeWidth-3, y+8, x - shapeWidth, y+shapeHeight+3);
        CubicCurve2D q3 = new CubicCurve2D.Float(x + shapeWidth-2, y-shapeHeight+8, x + shapeWidth-2, y, x+3, y+10, x + shapeWidth-5, y+shapeHeight+3);
        CubicCurve2D q4 = new CubicCurve2D.Float(x - shapeWidth, y+shapeHeight+3, x-shapeWidth+2, y+shapeHeight+16, x + shapeWidth+5, y+(shapeHeight*2)+3, x + shapeWidth-5, y+shapeHeight+3);
        closedCurve.append(q1, this.fill);
        closedCurve.append(q3, this.fill);
        closedCurve.append(q2, this.fill);
        closedCurve.append(q4, this.fill);

        g2d.draw(closedCurve);
        if(this.fill) {
            g2d.fill(closedCurve);
        }
        if(style == 3) {
            g2d.setStroke(new BasicStroke(0.5f));
            g2d.draw(closedCurve);

            g2d.fill(closedCurve);
            BufferedImage bi = new BufferedImage(5, 3, BufferedImage.TYPE_INT_RGB);
            Graphics2D big = bi.createGraphics();
            big.setColor(Color.WHITE);
            big.fillRect(0, 0, 5, 3);
            big.setColor(this.color);
            big.drawLine(0, 0, 5, 0);
            Rectangle r = new Rectangle(0, 0, 5, 3);
            TexturePaint tp = new TexturePaint(bi, r);

            g2d.setPaint(tp);
            g2d.fill(closedCurve);
        }
    }
}
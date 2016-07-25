package my.snippets;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import javax.sound.midi.*;

import java.util.ArrayList;
import java.io.*;
import java.net.URL;

public class MidiPianola {

    private JComponent ui = null;
    public static final int OTHER = -1;
    public static final int NOTE_ON = 1;
    public static final int NOTE_OFF = 2;
    private OctaveComponent[] octaves;
    Sequencer sequencer;
    int startOctave = 0;
    int numOctaves = 0;

    MidiPianola(int startOctave, int numOctaves)
            throws MidiUnavailableException {
        this.startOctave = startOctave;
        this.numOctaves = numOctaves;
        initUI();
    }

    public void openMidi(URL url)
            throws InvalidMidiDataException, IOException {
        openMidi(url.openStream());
    }

    public void openMidi(InputStream is)
            throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(is);
        Track[] tracks = sequence.getTracks();
        Track trk = sequence.createTrack();
        for (Track track : tracks) {
            addNotesToTrack(track, trk);
        }
        sequencer.setSequence(sequence);
        startMidi();
    }

    public void startMidi() {
        sequencer.start();
    }

    public void stopMidi() {
        sequencer.stop();
    }

    public void closeSequencer() {
        sequencer.close();
    }

    private void handleNote(final int command, int note) {
        OctaveComponent octave = getOctaveForNote(note);
        PianoKey key = octave.getKeyForNote(note);
        if (command == NOTE_ON) {
            key.setPressed(true);
        } else if (command == NOTE_OFF) {
            key.setPressed(false);
        }
        ui.repaint();
    }

    private OctaveComponent getOctaveForNote(int note) {
        return octaves[(note / 12) - startOctave];
    }

    public void initUI() throws MidiUnavailableException {
        if (ui != null) {
            return;
        }
        sequencer = MidiSystem.getSequencer();
        MetaEventListener mel = new MetaEventListener() {

            @Override
            public void meta(MetaMessage meta) {
                final int type = meta.getType();
                byte b = meta.getData()[1];
                int i = (int) (b & 0xFF);
                handleNote(type, i);
            }
        };
        sequencer.addMetaEventListener(mel);
        sequencer.open();

        ui = new JPanel(new BorderLayout(4, 4));
        ui.setBorder(new EmptyBorder(4, 4, 4, 4));

        JPanel keyBoard = new JPanel(new GridLayout(1, 0));
        ui.add(keyBoard, BorderLayout.CENTER);
        int end = startOctave + numOctaves;
        octaves = new OctaveComponent[end - startOctave];
        for (int i = startOctave; i < end; i++) {
            octaves[i - startOctave] = new OctaveComponent(i);
            keyBoard.add(octaves[i - startOctave]);
        }

        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        ui.add(tools, BorderLayout.PAGE_START);
        tools.setFloatable(false);
        Action open = new AbstractAction("Open") {

            JFileChooser fileChooser = new JFileChooser();

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(ui);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File f = fileChooser.getSelectedFile();
                    try {
                        openMidi(f.toURI().toURL());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        tools.add(open);

        Action rewind = new AbstractAction("Rewind") {

            @Override
            public void actionPerformed(ActionEvent e) {
                sequencer.setTickPosition(0);
            }
        };
        tools.add(rewind);

        Action play = new AbstractAction("Play") {

            @Override
            public void actionPerformed(ActionEvent e) {
                startMidi();
            }
        };
        tools.add(play);

        Action stop = new AbstractAction("Stop") {

            @Override
            public void actionPerformed(ActionEvent e) {
                stopMidi();
            }
        };
        tools.add(stop);
    }

    public JComponent getUI() {
        return ui;
    }

    /**
     * Iterates the MIDI events of the first track, and if they are a NOTE_ON or
     * NOTE_OFF message, adds them to the second track as a Meta event.
     */
    public static final void addNotesToTrack(
            Track track,
            Track trk) throws InvalidMidiDataException {
        for (int ii = 0; ii < track.size(); ii++) {
            MidiEvent me = track.get(ii);
            MidiMessage mm = me.getMessage();
            if (mm instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) mm;
                int command = sm.getCommand();
                int com = OTHER;
                if (command == ShortMessage.NOTE_ON) {
                    com = NOTE_ON;
                } else if (command == ShortMessage.NOTE_OFF) {
                    com = NOTE_OFF;
                }
                if (com > OTHER) {
                    byte[] b = sm.getMessage();
                    int l = (b == null ? 0 : b.length);
                    MetaMessage metaMessage = new MetaMessage(
                            com,
                            b,
                            l);
                    MidiEvent me2 = new MidiEvent(metaMessage, me.getTick());
                    trk.add(me2);
                }
            }
        }
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        UIManager.setLookAndFeel(
                                UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception useDefault) {
                    }
                    SpinnerNumberModel startModel = 
                            new SpinnerNumberModel(2,0,6,1);
                    JOptionPane.showMessageDialog(
                            null,
                            new JSpinner(startModel),
                            "Start Octave",
                            JOptionPane.QUESTION_MESSAGE);
                    SpinnerNumberModel octavesModel = 
                            new SpinnerNumberModel(5,5,11,1);
                    JOptionPane.showMessageDialog(
                            null,
                            new JSpinner(octavesModel),
                            "Number of Octaves",
                            JOptionPane.QUESTION_MESSAGE);
                    final MidiPianola o = new MidiPianola(
                            startModel.getNumber().intValue(),
                            octavesModel.getNumber().intValue());

                    WindowListener closeListener = new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent e) {
                            o.closeSequencer();
                        }
                    };

                    JFrame f = new JFrame("MIDI Pianola");
                    f.addWindowListener(closeListener);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setLocationByPlatform(true);

                    f.setContentPane(o.getUI());
                    f.setResizable(false);
                    f.pack();

                    f.setVisible(true);
                } catch (MidiUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
        };
        SwingUtilities.invokeLater(r);
    }
}

class OctaveComponent extends JPanel {

    int octave;
    ArrayList<PianoKey> keys;
    PianoKey selectedKey = null;

    public OctaveComponent(int octave) {
        this.octave = octave;
        init();
    }

    public PianoKey getKeyForNote(int note) {
        int number = note % 12;
        return keys.get(number);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (PianoKey key : keys) {
            key.draw(g2);
        }
    }

    public static final Shape
            removeArrayFromShape(Shape shape, Shape[] shapes) {
        Area a = new Area(shape);

        for (Shape sh : shapes) {
            a.subtract(new Area(sh));
        }

        return a;
    }

    public final Shape getEntireBounds() {
        Area a = new Area();
        for (PianoKey key : keys) {
            a.add(new Area(key.keyShape));
        }
        return a;
    }

    @Override
    public Dimension getPreferredSize() {
        Shape sh = getEntireBounds();
        Rectangle r = sh.getBounds();
        Dimension d = new Dimension(r.x + r.width, r.y + r.height + 1);
        return d;
    }

    public void init() {

        keys = new ArrayList<PianoKey>();
        int w = 30;
        int h = 200;
        int x = 0;
        int y = 0;
        int xs = w - (w / 3);
        Shape[] sharps = new Shape[5];
        int hs = h * 3 / 5;
        int ws = w * 2 / 3;
        sharps[0] = new Rectangle2D.Double(xs, y, ws, hs);
        xs += w;
        sharps[1] = new Rectangle2D.Double(xs, y, ws, hs);
        xs += 2 * w;
        sharps[2] = new Rectangle2D.Double(xs, y, ws, hs);
        xs += w;
        sharps[3] = new Rectangle2D.Double(xs, y, ws, hs);
        xs += w;
        sharps[4] = new Rectangle2D.Double(xs, y, ws, hs);

        Shape[] standards = new Shape[7];
        for (int ii = 0; ii < standards.length; ii++) {
            Shape shape = new Rectangle2D.Double(x, y, w, h);
            x += w;
            standards[ii] = removeArrayFromShape(shape, sharps);
        }

        int note = 0;
        int ist = 0;
        int ish = 0;
        keys.add(new PianoKey(standards[ist++], (octave * 12) + note++, "C", this));
        keys.add(new PianoKey(sharps[ish++], (octave * 12) + note++, "C#", this));
        keys.add(new PianoKey(standards[ist++], (octave * 12) + note++, "D", this));
        keys.add(new PianoKey(sharps[ish++], (octave * 12) + note++, "D#", this));
        keys.add(new PianoKey(standards[ist++], (octave * 12) + note++, "E", this));
        keys.add(new PianoKey(standards[ist++], (octave * 12) + note++, "F", this));
        keys.add(new PianoKey(sharps[ish++], (octave * 12) + note++, "F#", this));
        keys.add(new PianoKey(standards[ist++], (octave * 12) + note++, "G", this));
        keys.add(new PianoKey(sharps[ish++], (octave * 12) + note++, "G#", this));
        keys.add(new PianoKey(standards[ist++], (octave * 12) + note++, "A", this));
        keys.add(new PianoKey(sharps[ish++], (octave * 12) + note++, "A#", this));
        keys.add(new PianoKey(standards[ist++], (octave * 12) + note++, "B", this));
    }
}

class PianoKey {

    Shape keyShape;
    int number;
    String name;
    Component component;
    boolean pressed = false;

    PianoKey(Shape keyShape, int number, String name, Component component) {
        this.keyShape = keyShape;
        this.number = number;
        this.name = name;
        this.component = component;
    }

    public void draw(Graphics2D g) {
        if (name.endsWith("#")) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fill(keyShape);
        g.setColor(Color.GRAY);
        g.draw(keyShape);
        if (pressed) {
            Rectangle r = keyShape.getBounds();
            GradientPaint gp = new GradientPaint(
                    r.x,
                    r.y,
                    new Color(255, 225, 0, 40),
                    r.x,
                    r.y + (int) r.getHeight(),
                    new Color(255, 225, 0, 188));
            g.setPaint(gp);
            g.fill(keyShape);
            g.setColor(Color.GRAY);
            g.draw(keyShape);
        }
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }
}

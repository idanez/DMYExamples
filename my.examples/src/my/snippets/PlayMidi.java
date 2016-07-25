package my.snippets;

import java.io.File;
import javax.sound.midi.*;
import javax.swing.JOptionPane;

class PlayMidi {

    /** Iterates the MIDI events of the first track and if they are a 
     * NOTE_ON or NOTE_OFF message, adds them to the second track as a 
     * Meta event. */
    public static final void addNotesToTrack(
            Track track,
            Track trk) throws InvalidMidiDataException {
        for (int ii = 0; ii < track.size(); ii++) {
            MidiEvent me = track.get(ii);
            MidiMessage mm = me.getMessage();
            if (mm instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) mm;
                int command = sm.getCommand();
                int com = -1;
                if (command == ShortMessage.NOTE_ON) {
                    com = 1;
                } else if (command == ShortMessage.NOTE_OFF) {
                    com = 2;
                }
                if (com > 0) {
                    byte[] b = sm.getMessage();
                    int l = (b == null ? 0 : b.length);
                    MetaMessage metaMessage = new MetaMessage(com, b, l);
                    MidiEvent me2 = new MidiEvent(metaMessage, me.getTick());
                    trk.add(me2);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        /* This MIDI file can be found at..
         https://drive.google.com/open?id=0B5B9wDXIGw9lR2dGX005anJsT2M&authuser=0
         */
        File path = new File("I:\\projects\\EverLove.mid");

        Sequence sequence = MidiSystem.getSequence(path);
        Sequencer sequencer = MidiSystem.getSequencer();

        sequencer.open();

        MetaEventListener mel = new MetaEventListener() {

            @Override
            public void meta(MetaMessage meta) {
                final int type = meta.getType();
                System.out.println("MEL - type: " + type);
            }
        };
        sequencer.addMetaEventListener(mel);

        int[] types = new int[128];
        for (int ii = 0; ii < 128; ii++) {
            types[ii] = ii;
        }
        ControllerEventListener cel = new ControllerEventListener() {

            @Override
            public void controlChange(ShortMessage event) {
                int command = event.getCommand();
                if (command == ShortMessage.NOTE_ON) {
                    System.out.println("CEL - note on!");
                } else if (command == ShortMessage.NOTE_OFF) {
                    System.out.println("CEL - note off!");
                } else {
                    System.out.println("CEL - unknown: " + command);
                }
            }
        };
        int[] listeningTo = sequencer.addControllerEventListener(cel, types);
        StringBuilder sb = new StringBuilder();
        for (int ii = 0; ii < listeningTo.length; ii++) {
            sb.append(ii);
            sb.append(", ");
        }
        System.out.println("Listenning to: " + sb.toString());

        int mirror = JOptionPane.showConfirmDialog(
                null,
                "Add note on/off messages to another track as meta messages?",
                "Confirm Mirror",
                JOptionPane.OK_CANCEL_OPTION);
        if (mirror == JOptionPane.OK_OPTION) {
            Track[] tracks = sequence.getTracks();
            Track trk = sequence.createTrack();
            for (Track track : tracks) {
                addNotesToTrack(track, trk);
            }
        }

        sequencer.setSequence(sequence);
        sequencer.start();
        JOptionPane.showMessageDialog(null, "Exit this dialog to end");
        sequencer.stop();
        sequencer.close();
    }
}

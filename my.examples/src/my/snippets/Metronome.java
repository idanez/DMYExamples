package my.snippets;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

public class Metronome implements MetaEventListener {
	private Sequencer sequencer;
	private int bpm;
	private Synthesizer synthesizer;

	public static void main(final String[] args) throws Exception {
		int bpm = 120;
		if (args.length > 0) {
			try {
				bpm = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				bpm = 0;
			}
			if (bpm == 0) {
				bpm = 60;
			}
		}
		new Metronome().start(bpm);
	}

	public void start(final int bpm) {
		try {
			this.bpm = bpm;
			openSequencer();
			Sequence seq = createSequence();
			startSequence(seq);
		} catch (InvalidMidiDataException | MidiUnavailableException ex) {
			Logger.getLogger(Metronome.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void openSequencer() throws MidiUnavailableException {
		sequencer = MidiSystem.getSequencer();
		synthesizer = MidiSystem.getSynthesizer();
		sequencer.open();
		sequencer.addMetaEventListener(this);
		int[] controllersOfInterest = { 1, 4 };
		sequencer.addControllerEventListener(new ControllerEventListener() {

			@Override
			public void controlChange(final ShortMessage event) {
				System.out.print("Data1: " + event.getData1());
				System.out.print(" - Data2: " + event.getData2());
				System.out.print(" - Command : " + event.getCommand());
				System.out.print(" - Status : " + event.getStatus());
				System.out.println("");
				MidiChannel channel = synthesizer.getChannels()[9];
				if (event.getData2() == 2) {
					System.out.println("Entrei no data2");
					channel.setPitchBend(14000);
					channel.allNotesOff();
					ShortMessage msg;
				} else {
					channel.setPitchBend(8192);
				}
			}
		}, controllersOfInterest);
	}

	private Sequence createSequence() {
		try {
			Sequence seq = new Sequence(Sequence.PPQ, 1);
			Track track = seq.createTrack();
			ShortMessage msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0);
			MidiEvent evt = new MidiEvent(msg, 0);
			track.add(evt);

			int note = 37;
			int channel = 9; // 9 reserved for percursion
			int velocity = 127;

			addNoteEvent(track, 1, channel, note, velocity);
			addNoteEvent(track, 2, channel, note, velocity);
			// Teste de alteração de pitch
			msg = new ShortMessage(ShortMessage.PITCH_BEND, 9, 80, 100);
			evt = new MidiEvent(msg, 3);
			track.add(evt);
			addNoteEvent(track, 3, channel, note, velocity);
			addNoteEvent(track, 4, channel, 38, velocity);

			msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0);
			evt = new MidiEvent(msg, 4);
			track.add(evt);
			return seq;
		} catch (InvalidMidiDataException ex) {
			Logger.getLogger(Metronome.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	private void addControlChange(final Track track, final long tick) throws InvalidMidiDataException {
		ShortMessage msg = new ShortMessage(ShortMessage.CONTROL_CHANGE, 9, 1, (int) tick);
		MidiEvent evt = new MidiEvent(msg, tick);
		track.add(evt);
	}

	private void addNoteEvent(final Track track, final long tick, final int channel, final int note, final int velocity)
			throws InvalidMidiDataException {
		addControlChange(track, tick);
		ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity);
		MidiEvent event = new MidiEvent(message, tick);
		track.add(event);
	}

	private void startSequence(final Sequence seq) throws InvalidMidiDataException {
		sequencer.setSequence(seq);
		sequencer.setTempoInBPM(bpm);
		sequencer.start();
	}

	@Override
	public void meta(final MetaMessage message) {
		System.out.println("Type: " + message.getType());
		if (message.getType() != 47) { // 47 is end of track
			return;
		}
		doLoop();
	}

	private void doLoop() {
		if (sequencer == null || !sequencer.isOpen()) {
			return;
		}
		sequencer.setTickPosition(0);
		sequencer.start();
		sequencer.setTempoInBPM(bpm);
	}
}
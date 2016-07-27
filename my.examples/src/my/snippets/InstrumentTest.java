package my.snippets;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Synthesizer;

public class InstrumentTest {
	public static void main(final String[] args) {
		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			Instrument[] instruments = synth.getAvailableInstruments();
			if (synth.loadAllInstruments(synth.getDefaultSoundbank())) {
				System.out.println("There are " + instruments.length + " instruments. (Program::Bank");
			}
			for (int i = 0; i < instruments.length; i++) {
				Instrument instrument = instruments[i];
				Patch patch = instrument.getPatch();
				System.out.print(instrument.getName() + " >> ");
				System.out.println(patch.getProgram() + "::" + patch.getBank());
			}
		} catch (MidiUnavailableException mue) {
			// ignore
		}
	}
}
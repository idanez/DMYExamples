package tutogef.commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;

import tutogef.model.Employe;
import tutogef.model.Node;
import tutogef.model.Service;

public class CopyNodeCommand extends Command {

	private ArrayList<Node> list = new ArrayList<Node>();

	public boolean addElement(final Node node) {
		if (!list.contains(node)) {
			return list.add(node);
		}
		return false;
	}

	@Override
	public boolean canExecute() {
		if (list == null || list.isEmpty()) {
			return false;
		}
		Iterator<Node> it = list.iterator();
		while (it.hasNext()) {
			if (!isCopyableNode(it.next())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute() {
		if (canExecute()) {
			Clipboard.getDefault().setContents(list);
		}
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	public boolean isCopyableNode(final Node node) {
		if (node instanceof Service || node instanceof Employe) {
			return true;
		}
		return false;
	}

}

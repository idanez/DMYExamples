package tutogef.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;

import tutogef.model.Employe;
import tutogef.model.Node;
import tutogef.model.Service;

public class PasteNodeCommand extends Command {

	private HashMap<Node, Node> list = new HashMap<Node, Node>();

	@Override
	public boolean canExecute() {
		ArrayList<Node> bList = (ArrayList<Node>) Clipboard.getDefault().getContents();
		if (bList == null || bList.isEmpty()) {
			return false;
		}
		Iterator<Node> it = bList.iterator();
		while (it.hasNext()) {
			Node node = it.next();
			if (isPastableNode(node)) {
				list.put(node, null);
			}
		}
		return true;
	}

	@Override
	public void execute() {
		if (!canExecute()) {
			return;
		}
		Iterator<Node> it = list.keySet().iterator();
		while (it.hasNext()) {
			Node node = it.next();
			try {
				if (node instanceof Service) {
					Service srv = (Service) node;
					Service clone = (Service) srv.clone();
					list.put(node, clone);
				} else if (node instanceof Employe) {
					Employe emp = (Employe) node;
					Employe clone = (Employe) emp.clone();
					list.put(node, clone);
				}
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		redo();
	}

	@Override
	public void redo() {
		Iterator<Node> it = list.values().iterator();
		while (it.hasNext()) {
			Node node = it.next();
			if (isPastableNode(node)) {
				node.getParent().addChild(node);
			}
		}
	}

	@Override
	public boolean canUndo() {
		return !(list.isEmpty());
	}

	@Override
	public void undo() {
		Iterator<Node> it = list.values().iterator();
		while (it.hasNext()) {
			Node node = it.next();
			if (isPastableNode(node)) {
				node.getParent().removeChild(node);
			}
		}
	}

	public boolean isPastableNode(final Node node) {
		if (node instanceof Service || node instanceof Employe) {
			return true;
		}
		return false;
	}

}

package tutogef;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(final IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(true);
		// layout.addStandaloneView(IPageLayout.ID_OUTLINE, true, IPageLayout.LEFT, 0.3f, editorArea);
		IFolderLayout tabs = layout.createFolder("tab", IPageLayout.LEFT, 0.3f, editorArea);
		tabs.addView(IPageLayout.ID_OUTLINE);
		tabs.addPlaceholder(IPageLayout.ID_PROP_SHEET);

	}
}

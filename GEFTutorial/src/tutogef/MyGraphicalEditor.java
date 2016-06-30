package tutogef;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import tutogef.actions.CopyNodeAction;
import tutogef.actions.PasteNodeAction;
import tutogef.actions.RenameAction;
import tutogef.editpart.AppEditPartFactory;
import tutogef.model.Employe;
import tutogef.model.Entreprise;
import tutogef.model.Service;
import tutogef.part.tree.AppTreeEditPartFactory;

public class MyGraphicalEditor extends GraphicalEditorWithPalette {

	public static final String ID = "tutogef.mygraphicaleditor";

	private Entreprise model;
	private KeyHandler keyHandler;

	protected class OutlinePage extends ContentOutlinePage {
		private SashForm sash;
		private ScrollableThumbnail thumbnail;
		private DisposeListener disposeListener;

		public OutlinePage() {
			super(new TreeViewer());
		}

		@Override
		public void createControl(final Composite parent) {
			sash = new SashForm(parent, SWT.VERTICAL);
			getViewer().createControl(sash);
			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new AppTreeEditPartFactory());
			getViewer().setContents(model);
			getSelectionSynchronizer().addViewer(getViewer());
			// Creation de la miniature.
			Canvas canvas = new Canvas(sash, SWT.BORDER);
			LightweightSystem lws = new LightweightSystem(canvas);
			thumbnail = new ScrollableThumbnail(
					(Viewport) ((ScalableRootEditPart) getGraphicalViewer().getRootEditPart()).getFigure());
			thumbnail.setSource(((ScalableRootEditPart) getGraphicalViewer().getRootEditPart())
					.getLayer(LayerConstants.PRINTABLE_LAYERS));
			lws.setContents(thumbnail);
			disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(final DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			getGraphicalViewer().getControl().addDisposeListener(disposeListener);

		}

		@Override
		public void init(final IPageSite pageSite) {
			super.init(pageSite);
			// On hook les actions de l'editeur sur la toolbar
			IActionBars bars = getSite().getActionBars();
			bars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
					getActionRegistry().getAction(ActionFactory.UNDO.getId()));
			bars.setGlobalActionHandler(ActionFactory.REDO.getId(),
					getActionRegistry().getAction(ActionFactory.REDO.getId()));
			bars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
					getActionRegistry().getAction(ActionFactory.DELETE.getId()));
			bars.updateActionBars();
			// On associe les raccourcis clavier de l'editeur a l'outline
			getViewer().setKeyHandler(keyHandler);
			ContextMenuProvider provider = new AppContextMenuProvider(getViewer(), getActionRegistry());
			getViewer().setContextMenu(provider);
		}

		@Override
		public Control getControl() {
			return sash;
		}

		@Override
		public void dispose() {
			getSelectionSynchronizer().removeViewer(getViewer());
			Control control = getGraphicalViewer().getControl();
			if (control != null && !control.isDisposed()) {
				control.removeDisposeListener(disposeListener);
			}
			super.dispose();
		}
	}

	public MyGraphicalEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	protected void configureGraphicalViewer() {
		double[] zoomLevels;
		ArrayList<String> zoomContributions;
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new AppEditPartFactory());
		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart);
		ZoomManager manager = rootEditPart.getZoomManager();
		getActionRegistry().registerAction(new ZoomInAction(manager));
		getActionRegistry().registerAction(new ZoomOutAction(manager));
		// A lista de zooms possiveis. 1 = 100%
		zoomLevels = new double[] { 0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 10.0, 20.0 };
		manager.setZoomLevels(zoomLevels);
		// On ajoute certains zooms prédéfinis
		zoomContributions = new ArrayList<String>();
		zoomContributions.add(ZoomManager.FIT_ALL);
		zoomContributions.add(ZoomManager.FIT_HEIGHT);
		zoomContributions.add(ZoomManager.FIT_WIDTH);
		manager.setZoomLevelContributions(zoomContributions);

		keyHandler = new KeyHandler();
		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(ActionFactory.DELETE.getId()));

		keyHandler.put(KeyStroke.getPressed('+', SWT.KEYPAD_ADD, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

		keyHandler.put(KeyStroke.getPressed('-', SWT.KEYPAD_SUBTRACT, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_OUT));
		// On peut meme zoomer avec la molette de la souris.
		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.NONE), MouseWheelZoomHandler.SINGLETON);

		viewer.setKeyHandler(keyHandler);

		ContextMenuProvider provider = new AppContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(provider);

	}

	@Override
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		model = createEntreprise();
		viewer.setContents(model);
		viewer.addDropTargetListener(new MyTemplateTransferDropTargetListener(viewer));

	}

	@Override
	protected void initializePaletteViewer() {
		super.initializePaletteViewer();
		getPaletteViewer().addDragSourceListener(new TemplateTransferDragSourceListener(getPaletteViewer()));
	}

	public Entreprise createEntreprise() {
		Entreprise psyEntreprise = new Entreprise();
		psyEntreprise.setName("Entreprise");
		psyEntreprise.setAddress("Qualquer lugar");
		psyEntreprise.setCapital(100000);

		Service comptaService = new Service();
		comptaService.setName("Compta1");
		comptaService.setEtage(2);
		comptaService.setLayout(new Rectangle(30, 50, 250, 150));
		Employe employeCat = new Employe();
		employeCat.setName("Debora");
		employeCat.setPrenom("Cat1");
		employeCat.setLayout(new Rectangle(25, 40, 60, 40));
		comptaService.addChild(employeCat);
		Employe employeJyce = new Employe();
		employeJyce.setName("Pyco");
		employeJyce.setPrenom("Jyce1");
		employeJyce.setLayout(new Rectangle(100, 60, 60, 40));
		comptaService.addChild(employeJyce);
		Employe employeEva = new Employe();
		employeEva.setName("Longor");
		employeEva.setPrenom("Eva1");
		employeEva.setLayout(new Rectangle(180, 90, 60, 40));
		comptaService.addChild(employeEva);
		psyEntreprise.addChild(comptaService);

		Service rhService = new Service();
		rhService.setName("Ressources Humaine");
		rhService.setEtage(1);
		rhService.setLayout(new Rectangle(220, 230, 250, 150));
		Employe employePaul = new Employe();
		employePaul.setName("Dupond");
		employePaul.setPrenom("Paul");
		employePaul.setLayout(new Rectangle(40, 70, 60, 40));
		rhService.addChild(employePaul);
		Employe employeEric = new Employe();
		employeEric.setName("Durand");
		employeEric.setPrenom("Eric");
		employeEric.setLayout(new Rectangle(170, 100, 60, 40));
		rhService.addChild(employeEric);
		psyEntreprise.addChild(rhService);
		return psyEntreprise;

	}

	@Override
	public void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();
		IAction action = new RenameAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new CopyNodeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new PasteNodeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {

	}

	@Override
	public Object getAdapter(final Class type) {
		if (type == ZoomManager.class) {
			return ((ScalableRootEditPart) getGraphicalViewer().getRootEditPart()).getZoomManager();
		}
		if (type == IContentOutlinePage.class) {
			return new OutlinePage();
		}
		return super.getAdapter(type);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		// Racine de la palette
		PaletteRoot root = new PaletteRoot();

		// Creation d'un groupe (pour organiser un peu la palette)
		PaletteGroup manipGroup = new PaletteGroup("Main Pallete");
		root.add(manipGroup);

		// Ajout de l'outil de selection et de l'outil de selection groupe
		SelectionToolEntry selectionToolEntry = new SelectionToolEntry();
		manipGroup.add(selectionToolEntry);
		manipGroup.add(new MarqueeToolEntry());

		PaletteSeparator sep2 = new PaletteSeparator();
		root.add(sep2);

		PaletteGroup instGroup = new PaletteGroup("Elements creation");
		root.add(instGroup);

		instGroup.add(new CombinedTemplateCreationEntry("Service", "Creation dun service type",
				new NodeCreationFactory(Service.class),
				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/alt_window16.gif"),
				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/alt_window32.gif")));

		instGroup.add(new CombinedTemplateCreationEntry("Employe", "Creation dun employe model",
				new NodeCreationFactory(Employe.class),
				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/alt_window16.gif"),
				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/alt_window32.gif")));

		// Definition l'entree dans la palette qui sera utilise par defaut :
		// 1.lors de la premiere ouverture de la palette
		// 2.lorsqu'un element de la palette rend la main
		root.setDefaultEntry(selectionToolEntry);

		return root;
	}

}

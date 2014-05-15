package ca.usask.cs.srlab.contentsuggest.views;

import java.util.HashMap;
import java.util.List;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.custom.*;
import result.Result;
import utility.ContentLoader;

public class ContentSuggestView extends ViewPart {

	public static final String ID = "ca.usask.cs.srlab.contentsuggest.views.ContentSuggestView";
	public TableViewer viewer;
	public Browser browser;
	Result currentResult = null;
	int lastSelectedIndex = -1;
	HashMap<Button, Integer> suggestButtonMap;
	HashMap<Button, Integer> extractButtonMap;
	final int TEXT_MARGIN = 3;
	final Display currDisplay=Display.getCurrent();
	final TextLayout textLayout=new TextLayout(currDisplay);
	Font font1 = new Font(currDisplay, "Arial", 12, SWT.BOLD);
    Font font2 = new Font(currDisplay, "Arial", 10, SWT.NORMAL);
    Font font3 = new Font(currDisplay, "Arial", 10, SWT.NORMAL);
    Color blue = currDisplay.getSystemColor(SWT.COLOR_BLUE);
    Color green = currDisplay.getSystemColor(SWT.COLOR_DARK_GREEN);
    Color gray = currDisplay.getSystemColor(SWT.COLOR_DARK_GRAY);
    TextStyle style1 = new TextStyle(font1, blue,null);
    TextStyle style2 = new TextStyle(font2, green, null);
    TextStyle style3 = new TextStyle(font3, gray,null);
	
	
    protected void addTextLayout(final Table table)
    {
    	//now apply the text layout
    	Font font1 = new Font(currDisplay, "Arial", 12, SWT.BOLD);
        Font font2 = new Font(currDisplay, "Arial", 10, SWT.NORMAL);
        Font font3 = new Font(currDisplay, "Arial", 10, SWT.NORMAL);
        Color blue = currDisplay.getSystemColor(SWT.COLOR_BLUE);
        Color green = currDisplay.getSystemColor(SWT.COLOR_GREEN);
        Color gray = currDisplay.getSystemColor(SWT.COLOR_GRAY);
        TextStyle style1 = new TextStyle(font1, blue,null);
        TextStyle style2 = new TextStyle(font2, green, null);
        TextStyle style3 = new TextStyle(font3, gray,null);
        for(TableItem item:table.getItems()){
		String resultText=item.getText(0);
    	int firstNL=resultText.indexOf('\n');
    	int lastNL=resultText.lastIndexOf('\n');
    	textLayout.setText(resultText);
    	textLayout.setStyle(style1, 0, firstNL-1);
    	textLayout.setStyle(style2, firstNL+1, lastNL-1);
    	textLayout.setStyle(style3, lastNL, resultText.length());
        }
    	
    }
    
    
    
	protected void addTableItems(final Table table) {
		// adding items to the viewer table
		// adding suggestion and detail button
		final TableItem[] items = table.getItems();
		// System.out.println("Table items found:"+items.length);
		table.addListener(SWT.PaintItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				if (event.index == 1) {
					GC gc = event.gc;
					int index = event.index;
					TableItem item = (TableItem) event.item;
					int percent = Integer.parseInt(item.getText(1));
					Color foreground = gc.getForeground();
					Color background = gc.getBackground();
					//gc.setForeground(new Color(null, 11, 59, 23));
					gc.setForeground(new Color(null, 11, 97, 11));
					gc.setBackground(new Color(null, 255, 255, 255));
					int col2Width = 100;
					int width = (col2Width - 1) * percent / 100;
					int height = 25;
					//gc.fillRectangle(event.x, event.y + 10, width,
					//		height);
					gc.fillGradientRectangle(event.x, event.y + 15, width,
							height, false);
					Rectangle rect2 = new Rectangle(event.x, event.y + 15,
							width - 1, height - 1);
					gc.drawRectangle(rect2);
					gc.setForeground(new Color(null, 255, 255, 255));
					String text = percent + "%";
					Point size = event.gc.textExtent(text);
					int offset = Math.max(0, (height - size.y) / 2);
					gc.drawText(text, event.x + 2, event.y + 15 + offset, true);
					gc.setForeground(background);
					gc.setBackground(foreground);
				}
				if (event.index == 2) {
					// TableItem item=(TableItem)event.item;
					Image tmpImage = getSuggestionImage();
					int tmpWidth = 0;
					int tmpHeight = 0;
					int tmpX = 0;
					int tmpY = 0;

					tmpWidth = 30;// testTable.getColumn(event.index).getWidth();
					tmpHeight = ((TableItem) event.item).getBounds().height;

					tmpX = tmpImage.getBounds().width;
					tmpX = (tmpWidth / 2 - tmpX / 2);
					tmpY = tmpImage.getBounds().height;
					tmpY = (tmpHeight / 2 - tmpY / 2);
					if (tmpX <= 0)
						tmpX = event.x;
					else
						tmpX += event.x;
					if (tmpY <= 0)
						tmpY = event.y;
					else
						tmpY += event.y;
					event.gc.drawImage(tmpImage, tmpX, tmpY);

				}
				if (event.index == 3) {
					// TableItem item=(TableItem)event.item;
					Image tmpImage = getContentImage();
					int tmpWidth = 0;
					int tmpHeight = 0;
					int tmpX = 0;
					int tmpY = 0;

					tmpWidth = 30;// testTable.getColumn(event.index).getWidth();
					tmpHeight = ((TableItem) event.item).getBounds().height;

					tmpX = tmpImage.getBounds().width;
					tmpX = (tmpWidth / 2 - tmpX / 2);
					tmpY = tmpImage.getBounds().height;
					tmpY = (tmpHeight / 2 - tmpY / 2);
					if (tmpX <= 0)
						tmpX = event.x;
					else
						tmpX += event.x;
					if (tmpY <= 0)
						tmpY = event.y;
					else
						tmpY += event.y;
					event.gc.drawImage(tmpImage, tmpX, tmpY);

				}

				if (event.index == 0) {
					TableItem item = (TableItem) event.item;
					//item.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
					String text = item.getText(event.index);
					/* center column 1 vertically */
					int yOffset = 0;
					if (event.index == 1) {
						Point size = event.gc.textExtent(text);
						yOffset = Math.max(0, (event.height - size.y) / 2);
					}
//					event.gc.drawText(text, event.x + TEXT_MARGIN, event.y
//							+ yOffset, true);
					
					//redraw text layout
					String resultText=item.getText(0);
			    	int firstNL=resultText.indexOf('\n');
			    	int lastNL=resultText.lastIndexOf('\n');
			    	textLayout.setText(resultText);
			    	textLayout.setStyle(style1, 0, firstNL-1);
			    	textLayout.setStyle(style2, firstNL+1, lastNL-1);
			    	textLayout.setStyle(style3, lastNL, resultText.length());
					
					textLayout.draw(event.gc, event.x, event.y);
					
				}
			}
		});

		table.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				Point p = new Point(event.x, event.y);
				TableItem item = table.getItem(p);
				// System.out.println("selected index:"+table.getSelectionIndex());
				int currIndex = table.getSelectionIndex();
				if (item != null) {
					for (int col = 0; col < table.getColumnCount(); col++) {
						Rectangle rect = item.getBounds(col);
						if (rect.contains(p)) {
							if (col == 2) {
								if (lastSelectedIndex == currIndex) {
									// do nothing
								} else {
									currentResult = (Result) item.getData();
									currentResult.pageContent = ContentLoader
											.downloadPageContent(currentResult.resultURL);
								}
								BrowserManager manager = new BrowserManager(
										currentResult, browser);
								manager.showSuggestion();
								// table.setSelection(currIndex);
								// table.setRedraw(false);
								// lastSelectedIndex=event.index;
								// item.setBackground(new
								// Color(null,169,226,243));
							} else if (col == 3) {
								if (lastSelectedIndex == currIndex) {
									// do nothing
								} else {
									currentResult = (Result) item.getData();
									currentResult.pageContent = ContentLoader
											.downloadPageContent(currentResult.resultURL);
								}
								String viewID = "ca.usask.cs.srlab.contentsuggest.views.ContentBrowserView";
								try {
									PlatformUI.getWorkbench()
											.getActiveWorkbenchWindow()
											.getActivePage().showView(viewID);
									ContentBrowserView browserView = (ContentBrowserView) PlatformUI
											.getWorkbench()
											.getActiveWorkbenchWindow()
											.getActivePage().findView(viewID);
									BrowserManager manager = new BrowserManager(
											currentResult, browserView);
									manager.showMainContent();
									// table.setSelection(currIndex);
									// table.setRedraw(false);
									// item.setBackground(new
									// Color(null,169,226,243));
								} catch (Exception e2) {
									// handle the exception
								}

							}
						}
					}
				}
			}
		});

		// adding tool tip
		final ToolTip tip = new ToolTip(table.getShell(), SWT.ICON_INFORMATION);
		tip.setAutoHide(true);
		table.addListener(SWT.MouseHover, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Point p = new Point(event.x, event.y);
				TableItem item = table.getItem(p);
				for (int col = 2; col < table.getColumnCount(); col++) {
					Rectangle rect = item.getBounds(col);
					if (rect.contains(p)) {
						//System.out.println("From tool tip");
						try {
							if (col == 2) {
								tip.setText("Show relevant section only");
							} else if (col == 3) {
								tip.setText("Show all sections");
							}
							tip.getDisplay().timerExec(30, new Runnable() {
								public void run() {
									tip.setVisible(true);
								}
							});
						} catch (Exception exc) {
						}
					}
				}
			}
		});
		table.addListener(SWT.MouseExit, new Listener() {
			public void handleEvent(Event event) {
				tip.getDisplay().timerExec(50, new Runnable() {
					public void run() {
						tip.setVisible(false);
					}
				});
			}
		});
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

		// creating the index map
		suggestButtonMap = new HashMap<>();
		extractButtonMap = new HashMap<>();

		SashForm divider = new SashForm(parent, SWT.HORIZONTAL);
		Composite innerParent = new Composite(divider, SWT.NONE);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = 5;
		glayout.marginHeight = 5;
		innerParent.setLayout(glayout);

		GridData gdata = new GridData(SWT.FILL, SWT.FILL, true, true);
		parent.setLayoutData(gdata);

		// add search panel
		addSearchPanel(innerParent);

		// adding table viewer
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		viewer = new TableViewer(innerParent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		final Table table = viewer.getTable();
		table.setLayoutData(gridData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		String[] columns = { "Title", "Relevance", " ", " " };
		int[] colWidth = { 600, 100, 30, 30 };
		int[] colAlignment = { SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT };
		for (int i = 0; i < columns.length; i++) {
			TableColumn col = new TableColumn(table, colAlignment[i], i);
			col.setText(columns[i]);
			col.setWidth(colWidth[i]);
		}
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());

		// adding table items and listeners
		// adding suggestion and detailed button
		addTableItems(table);
		// resizing the table
		int min = 60;
		setItemHeight(table, min);
		
		//adding text layout
		//addTextLayout(table);

		// adding listener to the table
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				// TODO Auto-generated method stub
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.isEmpty())
					return;
				else {
					List<Object> list = selection.toList();
					Result result = (Result) list.get(0);
					System.out.println(result.resultURL);
					try {
						// now show the page URL
						String viewID = "ca.usask.cs.srlab.contentsuggest.views.ContentBrowserView";
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(viewID);
						ContentBrowserView browserView = (ContentBrowserView) PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().findView(viewID);
						Browser browser = browserView.browser;
						browser.setUrl(result.resultURL);
						browserView.setFocus();
					} catch (Exception exc) {
					}
				}
			}
		});

		// adding the browser
		browser = new Browser(divider, SWT.NONE);
		// browser.setLayoutData(gridData);

	}

	protected void addSearchPanel(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 10;
		gridLayout.verticalSpacing = 5;
		gridLayout.horizontalSpacing = 5;
		composite.setLayout(gridLayout);

		GridData gridData = new GridData(SWT.CENTER, SWT.FILL, true, false);
		composite.setLayoutData(gridData);

		// gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);
		GridData gdata2 = new GridData();
		gdata2.heightHint = 25;
		gdata2.widthHint = 500;
		gdata2.horizontalAlignment = SWT.BEGINNING;
		gdata2.verticalAlignment = SWT.CENTER;
		gdata2.grabExcessHorizontalSpace = false;

		// adding search box
		final Text input = new Text(composite, SWT.SINGLE | SWT.BORDER);
		input.setEditable(true);
		input.setToolTipText("Enter your search keywords");
		Font myfont = new Font(composite.getDisplay(), "Arial", 11, SWT.NORMAL);
		input.setFont(myfont);
		input.setLayoutData(gdata2);

		// adding search button
		GridData gdata3 = new GridData();
		gdata3.heightHint = 30;
		gdata3.widthHint = 90;
		gdata3.horizontalAlignment = SWT.BEGINNING;
		// gdata2.grabExcessHorizontalSpace=true;

		Button searchButton = new Button(composite, SWT.PUSH);
		searchButton.setText("Search");
		searchButton.setToolTipText("Search in Web");
		searchButton.setFont(new Font(composite.getDisplay(), "Arial", 10,
				SWT.BOLD));
		searchButton.setImage(getSearchImage());
		// System.out.println("Search Icon:"+Display.getDefault().getSystemImage(SWT.ICON_SEARCH));
		searchButton.setLayoutData(gdata3);
		searchButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				String query = input.getText().trim();
				if (query.isEmpty())
					return; // empty query
				else {
					// collecting results and displaying
					ViewContentProvider cprovider = new ViewContentProvider(
							query);
					viewer.setContentProvider(cprovider);
					Table table = viewer.getTable();
					addTableItems(table);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected Image getSearchImage() {
		// return search image
		return ImageDescriptor.createFromFile(ContentSuggestView.class,
				"search16.png").createImage();
	}

	protected Image getSuggestionImage() {
		// return suggestion image
		return ImageDescriptor.createFromFile(ContentSuggestView.class,
				"sugg16.png").createImage();
	}

	protected Image getContentImage() {
		// return content image
		return ImageDescriptor.createFromFile(ContentSuggestView.class,
				"doc316.png").createImage();
	}

	public void setItemHeight(Table table, final int min) {
		table.addListener(SWT.MeasureItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				TableItem item = (TableItem) event.item;
				String text = item.getText(event.index);
				Point size = event.gc.textExtent(text);
				event.width = size.x + 2 * TEXT_MARGIN;
				event.height = Math.max(min, size.y + TEXT_MARGIN);
			}
		});
		table.addListener(SWT.EraseItem, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				event.detail &= ~SWT.FOREGROUND;
			}
		});
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			Result result = (Result) obj;
			switch (index) {
			case 0:
				return result.title + "\n" + result.resultURL + "\n"
						+ result.description;
				// case 1:
				// return result.description;
			case 1:
				return String.format("%.0f", result.relevance);
			default:
				return "";
			}
		}

		public Image getColumnImage(Object obj, int index) {
			Image image = null;
			if (index == 0)
				image = getImage(obj);
			return image;
		}

		public Image getImage(Object obj) {
			return ImageDescriptor.createFromFile(ViewLabelProvider.class,
					"answer.png").createImage();
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
